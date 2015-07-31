package de.intranda.goobi.plugins;

import java.net.MalformedURLException;
import java.util.List;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import org.goobi.beans.Step;
import org.goobi.production.enums.PluginType;
import org.goobi.production.plugin.interfaces.IDashboardPlugin;

import de.intranda.digiverso.model.itm.DashQueuesObj;
import de.intranda.digiverso.model.itm.DashboardHelperItm;
import de.intranda.digiverso.model.rss.DashboardHelperRss;
import de.intranda.digiverso.model.rss.RssEntry;
import de.intranda.digiverso.model.tasks.DashboardHelperTasks;
import de.sub.goobi.config.ConfigPlugins;

@PluginImplementation
public class ExtendedDashboard implements IDashboardPlugin {

	private DashboardHelperRss rssHelper = new DashboardHelperRss();
	private DashboardHelperItm itmHelper = new DashboardHelperItm();
	private static final String PLUGIN_NAME = "intranda_dashboard_extended";
	private DashQueuesObj itmPlugin = null;
	
	public DashQueuesObj getItmPlugin() {
		return itmPlugin;
	}
	
	public void setItmPlugin(DashQueuesObj itmPlugin) {
		this.itmPlugin = itmPlugin;
	}
	
	@Override
	public PluginType getType() {
		return PluginType.Dashboard;
	}

	@Override
	public String getTitle() {
		return PLUGIN_NAME;
	}

	@Override
	public String getDescription() {
		return PLUGIN_NAME;
	}

	public List<DashQueuesObj> getItmPlugins() throws MalformedURLException {
		return itmHelper.getItmPlugins(ConfigPlugins.getPluginConfig(this));
	}
	
	// Bound to our ui:repeat component
	public List<RssEntry> getFeed() {
		return rssHelper.getFeed(getFeedUrl(), getFeedCachTime());
	}

	public Integer getFeedCachTime() {
		return ConfigPlugins.getPluginConfig(this).getInt("rss-cache-time", 900000);
	}

	public String getFeedUrl() {
		return ConfigPlugins.getPluginConfig(this).getString("rss-url", "http://www.intranda.com/feed/");
	}
	
	public String getFeedTitle() {
		return ConfigPlugins.getPluginConfig(this).getString("rss-title", "Letzte Importe");
	}

	public List<Step> getAssignedSteps() {
		return DashboardHelperTasks.getAssignedSteps();
	}

	@Override
	public String getGuiPath() {
		return "plugin_dashboard_extended.xhtml";
	}
	
	public boolean isShowRss(){
		return ConfigPlugins.getPluginConfig(this).getBoolean("rss-show", true);
	}
	
	public boolean isShowItm(){
		return ConfigPlugins.getPluginConfig(this).getBoolean("itm-show", true);
	}
	
}
