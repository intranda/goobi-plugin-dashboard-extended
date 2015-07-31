package de.intranda.goobi.plugins;

import java.util.List;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import org.goobi.beans.Step;
import org.goobi.production.enums.PluginType;
import org.goobi.production.plugin.interfaces.IDashboardPlugin;

import de.intranda.digiverso.model.rss.DashboardHelperRss;
import de.intranda.digiverso.model.rss.RssEntry;
import de.intranda.digiverso.model.tasks.DashboardHelperTasks;
import de.sub.goobi.config.ConfigPlugins;

@PluginImplementation
public class FullDashboard implements IDashboardPlugin {
	
	private DashboardHelperRss helper = new DashboardHelperRss();
	private static final String PLUGIN_NAME = "intranda_dashboard_full";

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
		return "plugin_dashboard_full.xhtml";
	}
}
