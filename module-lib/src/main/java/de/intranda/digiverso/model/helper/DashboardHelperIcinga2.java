package de.intranda.digiverso.model.helper;

/**
 * This file is part of a plugin for the Goobi Application - a Workflow tool for the support of mass digitization.
 *
 * Visit the websites for more information. - https://goobi.io - https://www.intranda.com - https://github.com/intranda/goobi
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59
 * Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 * Linking this library statically or dynamically with other modules is making a combined work based on this library. Thus, the terms and conditions
 * of the GNU General Public License cover the whole combination. As a special exception, the copyright holders of this library give you permission to
 * link this library with independent modules to produce an executable, regardless of the license terms of these independent modules, and to copy and
 * distribute the resulting executable under terms of your choice, provided that you also meet, for each linked independent module, the terms and
 * conditions of the license of that module. An independent module is a module which is not derived from or based on this library. If you modify this
 * library, you may extend this exception to your version of the library, but you are not obliged to do so. If you do not wish to do so, delete this
 * exception statement from your version.
 */

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.intranda.digiverso.model.icinga.Icinga2Host;
import de.intranda.digiverso.model.icinga.Icinga2HostStatus;
import de.intranda.digiverso.model.icinga.Icinga2Service;
import de.intranda.digiverso.model.icinga.Icinga2ServiceStatus;
import de.sub.goobi.helper.Helper;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.configuration.XMLConfiguration;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Getter
@Log4j2
public class DashboardHelperIcinga2 {
    private String monitoringHost;
    private String username;
    private String password;
    private boolean showIcinga;
    private List<Icinga2Host> hosts = Collections.emptyList();

    public DashboardHelperIcinga2(XMLConfiguration config) {
        showIcinga = config.getBoolean("icinga2-show", false);
        monitoringHost = config.getString("icinga2-host", "monitoring03.intranda.com");
        username = config.getString("icinga2-username", "user");
        password = config.getString("icinga2-password", "pass");
    }

    public List<Icinga2Host> getHosts() {
        try {
            reloadData();
        } catch (IOException | InterruptedException | NoSuchAlgorithmException | KeyManagementException e) {
            String message = "Failed to load Icinga2 monitoring data";
            log.error(message, e);
            Helper.setFehlerMeldung(message, e);
            hosts = Collections.emptyList();
        }
        return hosts;
    }

    private void reloadData() throws IOException, InterruptedException, NoSuchAlgorithmException, KeyManagementException {
        String auth = Base64.getEncoder()
                .encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8));

        try (HttpClient client = HttpClient.newBuilder()
                .build()) {

            HttpRequest hostsRequest = HttpRequest.newBuilder()
                    .uri(URI.create("https://" + monitoringHost + ":5665/v1/objects/hosts"))
                    .header("Authorization", "Basic " + auth)
                    .header("Accept", "application/json")
                    .GET()
                    .build();
            HttpRequest servicesRequest = HttpRequest.newBuilder()
                    .uri(URI.create("https://" + monitoringHost + ":5665/v1/objects/services"))
                    .header("Authorization", "Basic " + auth)
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> hostsResponse =
                    client.send(hostsRequest, HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> servicesResponse =
                    client.send(servicesRequest, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonHosts = mapper.readTree(hostsResponse.body());
            JsonNode jsonServices = mapper.readTree(servicesResponse.body());

            Map<String, Icinga2Host> hostMap = new HashMap<>();
            for (JsonNode node : jsonHosts.get("results")) {
                JsonNode attrs = node.get("attrs");
                Icinga2Host host = new Icinga2Host();
                String name = attrs.get("display_name").asText();
                int statusCode = attrs.get("state").asInt();
                host.setName(name);
                host.setStatus(Icinga2HostStatus.fromCode(statusCode));
                hostMap.put(name, host);
            }

            for (JsonNode node : jsonServices.get("results")) {
                JsonNode attrs = node.get("attrs");
                String hostName = attrs.get("host_name").asText();
                String name = attrs.get("display_name").asText();
                int statusCode = attrs.get("state").asInt();
                String output = Optional.ofNullable(attrs.get("last_check_result"))
                        .map(n -> n.get("output"))
                        .map(JsonNode::asText).orElse("");
                Icinga2Host host = Optional.ofNullable(hostMap.get(hostName)).orElseThrow();
                Icinga2Service service = new Icinga2Service();
                service.setName(name);
                service.setStatus(Icinga2ServiceStatus.fromCode(statusCode));
                service.setOutput(output);
                host.getServices().add(service);
            }

            hosts = hostMap.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .map(Map.Entry::getValue)
                    .toList();
        }
    }
}
