package de.intranda.goobi.plugins;

import org.goobi.production.enums.PluginType;
import org.goobi.production.plugin.interfaces.IDashboardPlugin;

import de.intranda.digiverso.model.helper.DashboardHelperRss;
import de.intranda.digiverso.model.helper.DashboardHelperTasks;
import de.sub.goobi.config.ConfigPlugins;
import net.xeoh.plugins.base.annotations.PluginImplementation;

@PluginImplementation
public class SimpleDashboard implements IDashboardPlugin {

	private DashboardHelperRss rssHelper;
	private DashboardHelperTasks tasksHelper;
	
	private static final String PLUGIN_NAME = "intranda_dashboard_simple";

	@Override
	public PluginType getType() {
		return PluginType.Dashboard;
	}

	@Override
	public String getTitle() {
		return PLUGIN_NAME;
	}

	public String getDescription() {
		return PLUGIN_NAME;
	}

	@Override
	public String getGuiPath() {
		return "/uii/plugin_dashboard_simple.xhtml";
	}
	
	public DashboardHelperTasks getTasksHelper() {
		if (tasksHelper==null){
			tasksHelper = new DashboardHelperTasks(ConfigPlugins.getPluginConfig(PLUGIN_NAME));
		}
		return tasksHelper;
	}
	
	public DashboardHelperRss getRssHelper() {
		if (rssHelper==null){
			rssHelper = new DashboardHelperRss(ConfigPlugins.getPluginConfig(PLUGIN_NAME));
		}
		return rssHelper;
	}
}
