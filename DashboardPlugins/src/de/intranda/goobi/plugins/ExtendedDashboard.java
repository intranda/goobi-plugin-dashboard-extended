package de.intranda.goobi.plugins;

import java.nio.file.Path;
import java.util.HashMap;

import org.goobi.beans.Step;
import org.goobi.production.enums.PluginGuiType;
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
import org.goobi.production.enums.PluginType;
import org.goobi.production.enums.StepReturnValue;
import org.goobi.production.plugin.interfaces.IDashboardPlugin;
import org.goobi.production.plugin.interfaces.IRestGuiPlugin;

import com.google.gson.Gson;

import de.intranda.digiverso.model.helper.DashboardHelperBatches;
import de.intranda.digiverso.model.helper.DashboardHelperItm;
import de.intranda.digiverso.model.helper.DashboardHelperNagios;
import de.intranda.digiverso.model.helper.DashboardHelperProcesses;
import de.intranda.digiverso.model.helper.DashboardHelperRss;
import de.intranda.digiverso.model.helper.DashboardHelperTasks;
import de.sub.goobi.config.ConfigPlugins;
import net.xeoh.plugins.base.annotations.PluginImplementation;
import spark.Service;

@PluginImplementation
public class ExtendedDashboard implements IDashboardPlugin, IRestGuiPlugin {

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
        if (rssHelper == null) {
            rssHelper = new DashboardHelperRss(ConfigPlugins.getPluginConfig(PLUGIN_NAME));
        }
        return rssHelper;
    }

    public DashboardHelperTasks getTasksHelper() {
        if (tasksHelper == null) {
            tasksHelper = new DashboardHelperTasks(ConfigPlugins.getPluginConfig(PLUGIN_NAME));
        }
        return tasksHelper;
    }

    public DashboardHelperBatches getBatchHelper() {
        if (batchHelper == null) {
            batchHelper = new DashboardHelperBatches(ConfigPlugins.getPluginConfig(PLUGIN_NAME));
        }
        return batchHelper;
    }

    public DashboardHelperItm getItmHelper() {
        if (itmHelper == null) {
            itmHelper = new DashboardHelperItm(ConfigPlugins.getPluginConfig(PLUGIN_NAME));
        }
        return itmHelper;
    }

    public DashboardHelperNagios getNagiosHelper() {
        if (nagiosHelper == null) {
            nagiosHelper = new DashboardHelperNagios(ConfigPlugins.getPluginConfig(PLUGIN_NAME));
        }
        return nagiosHelper;
    }

    public DashboardHelperProcesses getProcessHelper() {
        if (processHelper == null) {
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

    public void update() {
        // System.out.println("ich polle");
        // do nothing
    }

    @Override
    public String cancel() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean execute() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String finish() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getPagePath() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PluginGuiType getPluginGuiType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Step getStep() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void initialize(Step arg0, String arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public HashMap<String, StepReturnValue> validate() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void extractAssets(Path arg0) {
        // do nothing
    }

    @Override
    public String[] getJsPaths() {
        return new String[0];
    }

    @Override
    public void initRoutes(Service http) {
        http.path("/exdashboard", () -> {
            http.get("/rssfeed", (req, res) -> {
                return new Gson().toJson(this.getRssHelper().getFeed());
            });
        });

    }

}
