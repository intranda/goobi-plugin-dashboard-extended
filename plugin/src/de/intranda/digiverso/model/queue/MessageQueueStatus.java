package de.intranda.digiverso.model.queue;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.XMLConfiguration;
import org.goobi.beans.JobType;
import org.goobi.managedbeans.ProcessBean;

import de.sub.goobi.helper.Helper;
import de.sub.goobi.helper.exceptions.DAOException;
import de.sub.goobi.persistence.managers.ProcessManager;
import de.sub.goobi.persistence.managers.StepManager;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class MessageQueueStatus {

    @Getter
    private boolean showMessageQueue = false;

    @Getter
    @Setter
    List<TicketType> jobTypeList = new ArrayList<>();

    @Getter
    @Setter
    private TicketType currentType;

    @Getter
    @Setter
    private boolean showAllTasks = false;

    public MessageQueueStatus(XMLConfiguration pluginConfig) {

        showMessageQueue = pluginConfig.getBoolean("queue-show", false);
        if (showMessageQueue) {

            try {
                List<JobType> jobTypes = StepManager.getExternalQueueJobTypes();

                for (JobType type : jobTypes) {
                    String sql = "select count(1) from schritte where Bearbeitungsstatus = 6 and titel in (";
                    List<String> steps = type.getStepNameList();
                    StringBuilder titles = new StringBuilder();
                    if (!steps.isEmpty()) {
                        for (String step : steps) {
                            if (titles.length() > 0) {
                                titles.append(", ");
                            }
                            titles.append("\"");
                            titles.append(step);
                            titles.append("\"");
                        }
                    }
                    @SuppressWarnings("unchecked")
                    List<Object[]> list = ProcessManager.runSQL(sql + titles.toString() + ");");
                    String numberOfTasks = "0";
                    if (!list.isEmpty()) {
                        Object[] o = list.get(0);
                        numberOfTasks = (String) o[0];
                    }

                    jobTypeList.add(new TicketType(type.getName(), numberOfTasks, steps));

                }
            } catch (DAOException e1) {
                log.error(e1);
            }
        }

    }

    public String loadProcesses() {
        StringBuilder searchFilter = new StringBuilder();

        for (String step : currentType.getStepNames()) {
            if (searchFilter.length() > 0) {
                searchFilter.append(" \"|");
            } else {
                searchFilter.append("\"");
            }
            searchFilter.append("stepinflight:");
            searchFilter.append(step);
            searchFilter.append("\"");
        }

        ProcessBean bean = (ProcessBean) Helper.getBeanByName("ProzessverwaltungForm", ProcessBean.class);
        bean.setModusAnzeige("");
        bean.setFilter(searchFilter.toString());

        return bean.FilterAlleStart();

    }

    public List<TicketType> getCurrentJobs() {

        if (showAllTasks) {
            return jobTypeList;
        }
        List<TicketType> thl = new ArrayList<>();
        for (TicketType t : jobTypeList) {
            if (!t.getNumberOfTickets().equals("0")) {
                thl.add(t);
            }

        }
        return thl;

    }

    public boolean isJobListEmpty() {
        return getCurrentJobs().isEmpty();
    }
}
