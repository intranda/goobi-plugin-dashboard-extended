package de.intranda.digiverso.model.helper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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
import java.util.List;

import org.apache.commons.configuration.XMLConfiguration;
import org.goobi.beans.Step;
import org.goobi.managedbeans.ProcessBean;
import org.goobi.production.flow.statistics.hibernate.FilterHelper;

import de.intranda.digiverso.model.tasks.TaskHistory;
import de.sub.goobi.helper.Helper;
import de.sub.goobi.persistence.managers.ControllingManager;
import de.sub.goobi.persistence.managers.StepManager;
import lombok.Getter;
import lombok.Setter;

public class DashboardHelperTasks {
    private XMLConfiguration config;
    private List<Step> assignedSteps = null;
    @Getter
    private boolean showHistory;
    @Getter
    private List<TaskHistory> history = new ArrayList<>();
    @Getter
    private int numberOfDays;
    private String dateString;

    @Getter
    @Setter
    private TaskHistory currentElement;

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public DashboardHelperTasks(XMLConfiguration pluginConfig) {
        config = pluginConfig;
        showHistory = config.getBoolean("tasks-history", true);
        if (showHistory) {

            numberOfDays = config.getInt("tasks-history-period", 7);
            LocalDate date = LocalDate.now().minusDays(numberOfDays);
            dateString = dtf.format(date);

            List<String> stepnames = Arrays.asList(config.getStringArray("tasks-history-title"));
            if (stepnames == null || stepnames.isEmpty()) {
                showHistory = false;
                return;
            }
            StringBuilder stepnameFilter = new StringBuilder();
            for (String title : stepnames) {
                TaskHistory row = new TaskHistory(title);
                history.add(row);

                if (stepnameFilter.length() > 0) {
                    stepnameFilter.append(", ");
                }
                stepnameFilter.append("\"");
                stepnameFilter.append(title);
                stepnameFilter.append("\"");
            }

            StringBuilder sb = new StringBuilder();
            sb.append("select titel, Bearbeitungsstatus, count(1) ");
            sb.append("from schritte where Bearbeitungsstatus in (3,4) and ");
            sb.append("titel in (");
            sb.append(stepnameFilter.toString());
            sb.append(") and BearbeitungsEnde > \"");
            sb.append(dateString);
            sb.append("\" ");
            sb.append("group by titel, Bearbeitungsstatus");

            List<Object[]> rawvalues = ControllingManager.getResultsAsObjectList(sb.toString());
            for (Object[] objArr : rawvalues) {
                String title = (String) objArr[0];
                String status = (String) objArr[1];
                int numberOfTasks = Integer.valueOf((String) objArr[2]);
                for (TaskHistory th : history) {
                    if (th.getStepName().equals(title)) {
                        switch (status) {
                            case "3":
                                th.setNumberOfFinishedTasks(numberOfTasks);
                                break;
                            case "4":
                                th.setNumberOfErrorTasks(numberOfTasks);
                                break;
                        }
                    }
                }
            }
        }
    }

    public boolean isShowTasks() {
        return config.getBoolean("tasks-show", true);
    }

    public List<Step> getAssignedSteps() {
        if (Helper.getCurrentUser() != null && this.assignedSteps == null) {
            String sql = FilterHelper.criteriaBuilder("", false, false, true, true, false, true);
            this.assignedSteps = StepManager.getSteps("BearbeitungsBeginn desc", sql, 0, 10);
        }
        return assignedSteps;
    }

    public String loadFinishedSteps() {
        StringBuilder searchFilter = new StringBuilder();
        searchFilter.append("\"stepdone:");
        searchFilter.append(currentElement.getStepName());
        searchFilter.append("\" \"stepfinishdate>");
        searchFilter.append(dateString);
        searchFilter.append("\"");
        ProcessBean bean = (ProcessBean) Helper.getBeanByName("ProzessverwaltungForm", ProcessBean.class);
        bean.setModusAnzeige("");
        bean.setFilter(searchFilter.toString());

        return bean.FilterAlleStart();
    }

    public String loadErrorSteps() {
        StringBuilder searchFilter = new StringBuilder();
        searchFilter.append("\"steperror:");
        searchFilter.append(currentElement.getStepName());
        searchFilter.append("\" \"stepfinishdate>");
        searchFilter.append(dateString);
        searchFilter.append("\"");
        ProcessBean bean = (ProcessBean) Helper.getBeanByName("ProzessverwaltungForm", ProcessBean.class);
        bean.setModusAnzeige("");
        bean.setFilter(searchFilter.toString());

        return bean.FilterAlleStart();
    }

}
