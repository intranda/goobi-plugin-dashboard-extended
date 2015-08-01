package de.intranda.goobi.plugins;

import java.util.List;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import org.goobi.beans.Step;
import org.goobi.production.enums.PluginType;
import org.goobi.production.plugin.interfaces.IDashboardPlugin;

import de.intranda.digiverso.model.rss.DashboardHelperRss;
import de.intranda.digiverso.model.tasks.DashboardHelperTasks;
import de.sub.goobi.config.ConfigPlugins;

@PluginImplementation
public class SimpleDashboard implements IDashboardPlugin {

	private DashboardHelperRss rssHelper = new DashboardHelperRss(ConfigPlugins.getPluginConfig(this));
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

	@Override
	public String getGuiPath() {
		return "plugin_dashboard_simple.xhtml";
	}

	public List<Step> getAssignedSteps() {
		return DashboardHelperTasks.getAssignedSteps();
	}
	
	public DashboardHelperRss getRssHelper() {
		return rssHelper;
	}
}
