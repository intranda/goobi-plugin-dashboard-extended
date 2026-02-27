package org.goobi.api.rest;

import de.intranda.digiverso.model.helper.DashboardHelperIcinga2;
import de.intranda.digiverso.model.helper.DashboardHelperItm;
import de.intranda.digiverso.model.helper.DashboardHelperNagios;
import de.intranda.digiverso.model.helper.DashboardHelperRss;
import de.intranda.digiverso.model.icinga.Icinga2Host;
import de.intranda.digiverso.model.itm.DashQueuesObj;
import de.intranda.digiverso.model.nagios.Host;
import de.intranda.digiverso.model.rss.RssEntry;
import de.sub.goobi.config.ConfigPlugins;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.configuration.XMLConfiguration;

import java.net.MalformedURLException;
import java.util.List;

@Log4j2
@Path("/plugins/exdashboard")
public class ExtendedDashboardApi {
    private static final String PLUGIN_NAME = "intranda_dashboard_extended";
    private static XMLConfiguration pluginConfig;
    private static DashboardHelperRss rssHelper;
    private static DashboardHelperItm itmHelper;
    private static DashboardHelperNagios nagiosHelper;
    private static DashboardHelperIcinga2 icinga2Helper;

    static {
        pluginConfig = ConfigPlugins.getPluginConfig(PLUGIN_NAME);
    }

    private static DashboardHelperRss getRssHelper() {
        if (rssHelper == null) {
            rssHelper = new DashboardHelperRss(pluginConfig);
        }
        return rssHelper;
    }

    private static DashboardHelperItm getItmHelper() {
        if (itmHelper == null) {
            itmHelper = new DashboardHelperItm(pluginConfig);
        }
        return itmHelper;
    }

    private static DashboardHelperNagios getNagiosHelper() {
        if (nagiosHelper == null) {
            nagiosHelper = new DashboardHelperNagios(pluginConfig);
        }
        return nagiosHelper;
    }

    private static DashboardHelperIcinga2 getIcinga2Helper() {
        if (icinga2Helper == null) {
            icinga2Helper = new DashboardHelperIcinga2(pluginConfig);
        }
        return icinga2Helper;
    }

    @GET
    @Path("/rssfeed")
    public List<RssEntry> rssFeed() {
        return getRssHelper().getFeed();
    }

    @GET
    @Path("/itm")
    public List<DashQueuesObj> itm() throws MalformedURLException {
        return getItmHelper().getItmPlugins();
    }

    @GET
    @Path("/nagios")
    public List<Host> nagios() {
        return getNagiosHelper().getHosts();
    }

    @GET
    @Path("/icinga2")
    public List<Icinga2Host> icinga2() {
        return getIcinga2Helper().getHosts();
    }
}
