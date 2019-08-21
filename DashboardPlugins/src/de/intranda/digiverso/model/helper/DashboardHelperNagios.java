package de.intranda.digiverso.model.helper;

/**
 * This file is part of a plugin for the Goobi Application - a Workflow tool for the support of mass digitization.
 * 
 * Visit the websites for more information. 
 *          - https://goobi.io
 *          - https://www.intranda.com
 *          - https://github.com/intranda/goobi
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
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.XMLConfiguration;

import com.google.gson.Gson;

import de.intranda.digiverso.model.nagios.Host;
import de.intranda.digiverso.model.nagios.Nagios;
import de.sub.goobi.helper.HttpClientHelper;

public class DashboardHelperNagios {

    private XMLConfiguration config;
    private Long lastRead = null;
    private List<Host> hosts = null;

    public DashboardHelperNagios(XMLConfiguration xmlConfiguration) {
        config = xmlConfiguration;
    }

    private void readMonitoring() {
        Thread nagiosThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String serverLogin = config.getString("nagios-login", "nagiosadmin");
                String serverPassword = config.getString("nagios-password", "password");

                // run through all configured hosts
                hosts = new ArrayList<Host>();
                int numberOfHosts = config.getMaxIndex("nagios-host");
                for (int i = 0; i <= numberOfHosts; i++) {
                    String url = config.getString("nagios-host(" + i + ")");
                    String serverURL = "http://monitoring.intranda.com/icinga/cgi-bin/status.cgi?host=" + url + "&jsonoutput";
                    String json = HttpClientHelper.getStringFromUrl(serverURL, serverLogin, serverPassword, "monitoring.intranda.com", "80");
                    Gson gson = new Gson();
                    Host host = new Host(url);
                    host.setNagios(gson.fromJson(json, Nagios.class));
                    hosts.add(host);
                }
            }
        });
        nagiosThread.start();
    }

    public boolean isShowNagios() {
        return config.getBoolean("nagios-show", true);
    }

    public Long getLastRead() {
        return lastRead;
    }

    public void setLastRead(Long inLastRead) {
        this.lastRead = inLastRead;
    }

    public List<Host> getHosts() {
        Long now = System.currentTimeMillis();
        if (isShowNagios() && (lastRead == null || now - lastRead > config.getInt("nagios-cache-time", 180000))) {
            readMonitoring();
            lastRead = System.currentTimeMillis();
        }
        return hosts;
    }
}
