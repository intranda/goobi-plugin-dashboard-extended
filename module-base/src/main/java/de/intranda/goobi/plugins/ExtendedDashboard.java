package de.intranda.goobi.plugins;

import de.intranda.digiverso.model.helper.*;
import de.intranda.digiverso.model.queue.MessageQueueStatus;
import de.sub.goobi.config.ConfigPlugins;
import de.sub.goobi.forms.NavigationForm;
import de.sub.goobi.helper.FacesContextHelper;
import de.sub.goobi.helper.Helper;
import de.sub.goobi.persistence.managers.ProcessManager;
import lombok.Getter;
import lombok.Setter;
import net.xeoh.plugins.base.annotations.PluginImplementation;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.lang.StringUtils;
import org.goobi.beans.User;
import org.goobi.managedbeans.DatabasePaginator;
import org.goobi.production.enums.PluginType;
import org.goobi.production.flow.statistics.hibernate.FilterHelper;
import org.goobi.production.plugin.interfaces.IDashboardPlugin;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@PluginImplementation
public class ExtendedDashboard implements IDashboardPlugin {

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
        if ("titelAsc".equals(sortOrder)) {
            answer = "prozesse.titel";
        } else if ("titelDesc".equals(sortOrder)) {
            answer = "prozesse.titel desc";
        } else if ("batchAsc".equals(sortOrder)) {
            answer = "batchID";
        } else if ("batchDesc".equals(sortOrder)) {
            answer = "batchID desc";
        } else if ("projektAsc".equals(sortOrder)) {
            answer = "projekte.Titel";
        } else if ("projektDesc".equals(sortOrder)) {
            answer = "projekte.Titel desc";
        } else if ("vorgangsdatumAsc".equals(sortOrder)) {
            answer = "erstellungsdatum";
        } else if ("vorgangsdatumDesc".equals(sortOrder)) {
            answer = "erstellungsdatum desc";
        } else if ("fortschrittAsc".equals(sortOrder)) {
            answer = "sortHelperStatus";
        } else if ("fortschrittDesc".equals(sortOrder)) {
            answer = "sortHelperStatus desc";
        } else if ("idAsc".equals(sortOrder)) {
            answer = "prozesse.ProzesseID";
        } else if ("idDesc".equals(sortOrder)) {
            answer = "prozesse.ProzesseID desc";
        } else if ("institutionAsc".equals(sortOrder)) {
            answer = "institution.shortName";
        } else if ("institutionDesc".equals(sortOrder)) {
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
