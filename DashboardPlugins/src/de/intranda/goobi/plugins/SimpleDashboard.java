package de.intranda.goobi.plugins;

import java.util.List;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import org.goobi.beans.Step;
import org.goobi.production.enums.PluginType;
import org.goobi.production.plugin.interfaces.IDashboardPlugin;

import de.intranda.digiverso.model.itm.rss.DashboardHelperRss;
import de.intranda.digiverso.model.itm.rss.RssEntry;
import de.intranda.digiverso.model.tasks.DashboardHelperTasks;
import de.sub.goobi.config.ConfigPlugins;

@PluginImplementation
public class SimpleDashboard implements IDashboardPlugin {

	private DashboardHelperRss helper = new DashboardHelperRss();
	private static final String PLUGIN_NAME = "intranda_dashboard_simple";

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

	// Bound to our ui:repeat component
	public List<RssEntry> getFeed() {
		return helper.getFeed(getFeedUrl(), getFeedCachTime());
	}

	public Integer getFeedCachTime() {
		return ConfigPlugins.getPluginConfig(this).getInt("feed-cache-time", 900000);
	}
	
	public String getFeedTitle() {
		return ConfigPlugins.getPluginConfig(this).getString("feed-title", "Letzte Importe");
	}
	
	public String getFeedUrl() {
		return ConfigPlugins.getPluginConfig(this).getString("feed-url", "http://www.intranda.com/feed/");
	}

	public List<Step> getAssignedSteps() {
		return DashboardHelperTasks.getAssignedSteps();
	}

	@Override
	public String getGuiPath() {
		return "plugin_dashboard_simple.xhtml";
	}
}
