package de.intranda.goobi.plugins;

import org.goobi.production.enums.PluginType;
import org.goobi.production.plugin.interfaces.IDashboardPlugin;

import lombok.Data;
import lombok.extern.log4j.Log4j;
import net.xeoh.plugins.base.annotations.PluginImplementation;

@PluginImplementation
@Data
@Log4j
public class BatchDashboardPlugin implements IDashboardPlugin {
	private static final String PLUGIN_NAME = "intranda_dashboard_batches";

	

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
		return "/uii/plugin_dashboard_batch.xhtml";
	}


}
