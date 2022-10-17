package de.intranda.goobi.plugins;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.lang.StringUtils;
import org.goobi.beans.User;
import org.goobi.managedbeans.DatabasePaginator;
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
import org.goobi.production.flow.statistics.hibernate.FilterHelper;
import org.goobi.production.plugin.interfaces.IDashboardPlugin;
import org.goobi.production.plugin.interfaces.IRestPlugin;

import com.google.gson.Gson;

import de.intranda.digiverso.model.helper.DashboardHelperBatches;
import de.intranda.digiverso.model.helper.DashboardHelperItm;
import de.intranda.digiverso.model.helper.DashboardHelperNagios;
import de.intranda.digiverso.model.helper.DashboardHelperProcesses;
import de.intranda.digiverso.model.helper.DashboardHelperRss;
import de.intranda.digiverso.model.helper.DashboardHelperTasks;
import de.intranda.digiverso.model.queue.MessageQueueStatus;
import de.sub.goobi.config.ConfigPlugins;
import de.sub.goobi.forms.NavigationForm;
import de.sub.goobi.helper.FacesContextHelper;
import de.sub.goobi.helper.Helper;
import de.sub.goobi.persistence.managers.ProcessManager;
import lombok.Getter;
import lombok.Setter;
import net.xeoh.plugins.base.annotations.PluginImplementation;
import spark.Service;

@PluginImplementation
public class ExtendedDashboard implements IDashboardPlugin, IRestPlugin {

    private static final long serialVersionUID = -3380191539964504517L;

    private transient DashboardHelperRss rssHelper;
    private transient DashboardHelperItm itmHelper;
    private transient DashboardHelperNagios nagiosHelper;
    private transient DashboardHelperProcesses processHelper;
    private static final String PLUGIN_NAME = "intranda_dashboard_extended";
    private transient DashboardHelperBatches batchHelper;
    private transient DashboardHelperTasks tasksHelper;

    private DatabasePaginator paginator = null;

    private transient MessageQueueStatus mq;

    @Getter
    @Setter
    private String filter = null;

    private XMLConfiguration pluginConfig;

    @Getter
    @Setter
    private List<String> column1 = new ArrayList<>();
    @Getter
    @Setter
    private List<String> column2 = new ArrayList<>();
    @Getter
    @Setter
    private List<String> column3 = new ArrayList<>();

    private String[] widgetNames = { "assignedSteps", "tasksLastChanges", "taskHistory", "processSearch", "htmlBox", "statisticsProcesses",
            "processTemplates", "itm", "queue", "rss", "nagios" };

    public ExtendedDashboard() {
        pluginConfig = ConfigPlugins.getPluginConfig(PLUGIN_NAME);

        String value =
                "1 assignedSteps,1 tasksLastChanges,1 taskHistory,1 processSearch,1 htmlBox,2 statisticsProcesses,2 processTemplates,2 itm,2 queue,3 rss,3 nagios";

        User user = Helper.getCurrentUser();

        if (user != null && !StringUtils.isBlank(user.getDashboardConfiguration())) {
            value = user.getDashboardConfiguration();
        }
        // replace new line with comma, then split
        value = value.replace("\n", ",");
        String[] lines = value.split(",");
        for (String line : lines) {
            line = line.trim();
            if (line.startsWith("1")) {
                String widget = line.replace("1", "").trim();
                if (widgetExists(widget)) {
                    column1.add(widget);
                }
            } else if (line.startsWith("2")) {
                String widget = line.replace("2", "").trim();
                if (widgetExists(widget)) {
                    column2.add(widget);
                }
            } else if (line.startsWith("3")) {
                String widget = line.replace("3", "").trim();
                if (widgetExists(widget)) {
                    column3.add(widget);
                }
            }
        }
    }

    private boolean widgetExists(String widgetName) {
        for (String knownWidgetName : widgetNames) {
            if (knownWidgetName.equals(widgetName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public PluginType getType() {
        return PluginType.Dashboard;
    }

    @Override
    public String getTitle() {
        return PLUGIN_NAME;
    }

    public String getDescription() {
        return getTitle();
    }

    @Override
    public String getGuiPath() {
        return "/uii/plugin_dashboard_extended.xhtml";
    }

    public DashboardHelperRss getRssHelper() {
        if (rssHelper == null) {
            rssHelper = new DashboardHelperRss(pluginConfig);
        }
        return rssHelper;
    }

    public DashboardHelperTasks getTasksHelper() {
        if (tasksHelper == null) {
            tasksHelper = new DashboardHelperTasks(pluginConfig);
        }
        return tasksHelper;
    }

    public DashboardHelperBatches getBatchHelper() {
        if (batchHelper == null) {
            batchHelper = new DashboardHelperBatches(pluginConfig);
        }
        return batchHelper;
    }

    public DashboardHelperItm getItmHelper() {
        if (itmHelper == null) {
            itmHelper = new DashboardHelperItm(pluginConfig);
        }
        return itmHelper;
    }

    public DashboardHelperNagios getNagiosHelper() {
        if (nagiosHelper == null) {
            nagiosHelper = new DashboardHelperNagios(pluginConfig);
        }
        return nagiosHelper;
    }

    public DashboardHelperProcesses getProcessHelper() {
        if (processHelper == null) {
            processHelper = new DashboardHelperProcesses(pluginConfig);
        }
        return processHelper;
    }

    public MessageQueueStatus getMessageQueueStatus() {
        if (mq == null) {
            mq = new MessageQueueStatus(pluginConfig);
        }
        return mq;
    }

    public boolean isShowSearch() {
        return pluginConfig.getBoolean("search-show", true);
    }

    public boolean isShowHtmlBox() {
        return pluginConfig.getBoolean("html-box-show", false);
    }

    public String getHtmlBoxTitle() {
        return pluginConfig.getString("html-box-title", "- no title defined -");
    }

    public String getHtmlBoxContent() {
        return pluginConfig.getString("html-box-content", "- no content to show here -");
    }

    public boolean getShowProcessTemplateStatusColumn() {
        return pluginConfig.getBoolean("processTemplates-show-statusColumn", true);
    }

    public boolean getShowProcessTemplateProjectColumn() {
        return pluginConfig.getBoolean("processTemplates-show-projectColumn", true);
    }

    public boolean getShowProcessTemplateMassImportButton() {
        return pluginConfig.getBoolean("processTemplates-show-massImportButton", true);
    }

    public void update() {
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
            http.get("/itm", (req, res) -> {
                return new Gson().toJson(this.getItmHelper().getItmPlugins());
            });
            http.get("/nagios", (req, res) -> {
                return new Gson().toJson(this.getNagiosHelper().getHosts());
            });
        });

    }

    public void filterTemplates() {
        String sql = FilterHelper.criteriaBuilder(filter, null, null, null, null, true, false);
        if (!sql.isEmpty()) {
            sql = sql + " AND ";
        }
        sql = sql + " prozesse.istTemplate = true ";

        if (!sql.isEmpty()) {
            sql = sql + " AND ";
        }
        sql = sql + " prozesse.ProjekteID not in (select ProjekteID from projekte where projectIsArchived = true) ";

        ProcessManager m = new ProcessManager();
        paginator = new DatabasePaginator(sortList(), sql, m, "process_all");

    }

    private String sortList() {
        NavigationForm form = (NavigationForm) Helper.getBeanByName("NavigationForm", NavigationForm.class);
        String sortOrder = form.getUiStatus().get("sorting");
        if (sortOrder == null) {
            sortOrder = "titelAsc";
            form.getUiStatus().put("sorting", sortOrder);
        }
        String answer = "prozesse.titel";
        if (sortOrder.equals("titelAsc")) {
            answer = "prozesse.titel";
        } else if (sortOrder.equals("titelDesc")) {
            answer = "prozesse.titel desc";
        } else if (sortOrder.equals("batchAsc")) {
            answer = "batchID";
        } else if (sortOrder.equals("batchDesc")) {
            answer = "batchID desc";
        } else if (sortOrder.equals("projektAsc")) {
            answer = "projekte.Titel";
        } else if (sortOrder.equals("projektDesc")) {
            answer = "projekte.Titel desc";
        } else if (sortOrder.equals("vorgangsdatumAsc")) {
            answer = "erstellungsdatum";
        } else if (sortOrder.equals("vorgangsdatumDesc")) {
            answer = "erstellungsdatum desc";
        } else if (sortOrder.equals("fortschrittAsc")) {
            answer = "sortHelperStatus";
        } else if (sortOrder.equals("fortschrittDesc")) {
            answer = "sortHelperStatus desc";
        } else if (sortOrder.equals("idAsc")) {
            answer = "prozesse.ProzesseID";
        } else if (sortOrder.equals("idDesc")) {
            answer = "prozesse.ProzesseID desc";
        } else if (sortOrder.equals("institutionAsc")) {
            answer = "institution.shortName";
        } else if (sortOrder.equals("institutionDesc")) {
            answer = "institution.shortName desc";
        }

        return answer;
    }

    public DatabasePaginator getPaginator() {
        if (paginator == null) {
            filterTemplates();
        }
        return paginator;
    }

    public String getFormattedDate(Date date) {
        if (date == null) {
            return "-";
        }
        DateFormat dateFormat = getDateFormat(DateFormat.DEFAULT);
        return dateFormat.format(date);
    }

    public String getFormattedTime(Date date) {
        if (date == null) {
            return "-";
        }
        DateFormat dateFormat = getTimeFormat(DateFormat.MEDIUM);
        return dateFormat.format(date);
    }

    private DateFormat getTimeFormat(int formatType) {
        DateFormat dateFormat = DateFormat.getTimeInstance(formatType);
        Locale userLang = FacesContextHelper.getCurrentFacesContext().getViewRoot().getLocale();
        if (userLang != null) {
            dateFormat = DateFormat.getTimeInstance(formatType, userLang);
        }
        return dateFormat;
    }

    private DateFormat getDateFormat(int formatType) {
        DateFormat dateFormat = DateFormat.getDateInstance(formatType);
        Locale userLang = FacesContextHelper.getCurrentFacesContext().getViewRoot().getLocale();
        if (userLang != null) {
            dateFormat = DateFormat.getDateInstance(formatType, userLang);
        }
        return dateFormat;
    }
}
