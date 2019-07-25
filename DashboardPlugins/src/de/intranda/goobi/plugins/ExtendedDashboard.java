package de.intranda.goobi.plugins;

import org.goobi.production.enums.PluginType;
import org.goobi.production.plugin.interfaces.IDashboardPlugin;

import de.intranda.digiverso.model.helper.DashboardHelperBatches;
import de.intranda.digiverso.model.helper.DashboardHelperItm;
import de.intranda.digiverso.model.helper.DashboardHelperNagios;
import de.intranda.digiverso.model.helper.DashboardHelperProcesses;
import de.intranda.digiverso.model.helper.DashboardHelperRss;
import de.intranda.digiverso.model.helper.DashboardHelperTasks;
import de.sub.goobi.config.ConfigPlugins;
import net.xeoh.plugins.base.annotations.PluginImplementation;

@PluginImplementation
public class ExtendedDashboard implements IDashboardPlugin {

	private DashboardHelperRss rssHelper; 
	private DashboardHelperItm itmHelper;
	private DashboardHelperNagios nagiosHelper;
	private DashboardHelperProcesses processHelper;
	private static final String PLUGIN_NAME = "intranda_dashboard_extended";
	private DashboardHelperBatches batchHelper;
	private DashboardHelperTasks tasksHelper;
	
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
		return "/uii/plugin_dashboard_extended.xhtml";
	}

	public DashboardHelperRss getRssHelper() {
		if (rssHelper==null){
			rssHelper = new DashboardHelperRss(ConfigPlugins.getPluginConfig(PLUGIN_NAME));
		}
		return rssHelper;
	}
	
	public DashboardHelperTasks getTasksHelper() {
		if (tasksHelper==null){
			tasksHelper = new DashboardHelperTasks(ConfigPlugins.getPluginConfig(PLUGIN_NAME));
		}
		return tasksHelper;
	}
	
	public DashboardHelperBatches getBatchHelper() {
		if (batchHelper==null){
			batchHelper = new DashboardHelperBatches(ConfigPlugins.getPluginConfig(PLUGIN_NAME));
		}
		return batchHelper;
	}
	
	public DashboardHelperItm getItmHelper() {
		if (itmHelper==null){
			itmHelper = new DashboardHelperItm(ConfigPlugins.getPluginConfig(PLUGIN_NAME));
		}
		return itmHelper;
	}
	
	public DashboardHelperNagios getNagiosHelper() {
		if (nagiosHelper==null){
			nagiosHelper = new DashboardHelperNagios(ConfigPlugins.getPluginConfig(PLUGIN_NAME));
		}
		return nagiosHelper;
	}
	
	public DashboardHelperProcesses getProcessHelper() {
		if (processHelper==null){
			processHelper = new DashboardHelperProcesses(ConfigPlugins.getPluginConfig(PLUGIN_NAME));
		}
		return processHelper;
	}
	
	public boolean isShowSearch() {
		return ConfigPlugins.getPluginConfig(PLUGIN_NAME).getBoolean("search-show", true);
	}
	
	public boolean isShowHtmlBox() {
		return ConfigPlugins.getPluginConfig(PLUGIN_NAME).getBoolean("html-box-show", false);
	}
	
	public String getHtmlBoxTitle() {
		return ConfigPlugins.getPluginConfig(PLUGIN_NAME).getString("html-box-title", "- no title defined -");
	}
	
	public String getHtmlBoxContent() {
		return ConfigPlugins.getPluginConfig(PLUGIN_NAME).getString("html-box-content", "- no content to show here -");
	}
	
	public boolean getShowProcessTemplateStatusColumn() {
		return ConfigPlugins.getPluginConfig(PLUGIN_NAME).getBoolean("processTemplates-show-statusColumn", true);
	}
	
	public boolean getShowProcessTemplateProjectColumn() {
		return ConfigPlugins.getPluginConfig(PLUGIN_NAME).getBoolean("processTemplates-show-projectColumn", true);
	}
	
	public boolean getShowProcessTemplateMassImportButton() {
		return ConfigPlugins.getPluginConfig(PLUGIN_NAME).getBoolean("processTemplates-show-massImportButton", true);
	}
	
	public void update(){
		//System.out.println("ich polle");
		// do nothing
	}
	
}
