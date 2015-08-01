package de.intranda.goobi.plugins;

import java.util.List;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import org.goobi.beans.Step;
import org.goobi.production.enums.PluginType;
import org.goobi.production.plugin.interfaces.IDashboardPlugin;

import de.intranda.digiverso.model.tasks.DashboardHelperTasks;

@PluginImplementation
public class FullDashboard implements IDashboardPlugin {
	
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

	public List<Step> getAssignedSteps() {
		return DashboardHelperTasks.getAssignedSteps();
	}

	@Override
	public String getGuiPath() {
		return "plugin_dashboard_full.xhtml";
	}
}
