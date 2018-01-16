package de.intranda.goobi.plugins;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.goobi.production.cli.helper.StringPair;
import org.goobi.production.enums.PluginType;
import org.goobi.production.plugin.interfaces.IDashboardPlugin;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import de.intranda.digiverso.model.helper.DashboardHelperBatch;
import de.sub.goobi.config.ConfigurationHelper;
import de.sub.goobi.persistence.managers.MySQLHelper;
import lombok.Data;
import lombok.extern.log4j.Log4j;
import net.xeoh.plugins.base.annotations.PluginImplementation;

@PluginImplementation
@Data
@Log4j
public class BatchDashboardPlugin implements IDashboardPlugin {
    private static final DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");

    private static final String PLUGIN_NAME = "intranda_dashboard_batches";

    // get start- and end date from date picker?
    private DateTime selectedStartDate;
    private DateTime selectedEndDate;
    private DateTime today;

    // contains the list of all batches in the current interval
    private List<DashboardHelperBatch> batchesInInterval;

    public BatchDashboardPlugin() {
        // set default search interval - replace it with configured value? 
        today = new DateTime();
        selectedStartDate = today.minusMonths(1);
        selectedEndDate = today.plusMonths(1);

        loadBatchesInInterval();

    }

    public void loadBatchesInInterval() {
        // the first query collects all batches within given interval, the second query loads additional data to each batch

        /*
       SELECT max(id) as id, max(startDate) as startDate, max(endDate) as endDate, max(batchName) as batchName, count(prozesseId) as totalProcesses FROM batches join prozesse
        on prozesse.batchID = batches.id WHERE
        (startDate BETWEEN DATE("2017-01-01") AND DATE("2018-01-15")) OR
        (endDate BETWEEN DATE("2017-01-01") AND DATE("2018-01-15")) OR
        (startDate < DATE("2017-01-01") AND endDate > DATE("2018-01-15"))
        group by batches.id;
        */

        /*
        select bearbeitungsstatus, count(schritteid) from schritte left join prozesse on schritte.prozesseid = prozesse.prozesseid where prozesse.batchId = 3 group by bearbeitungsstatus;
         */

        StringBuilder batches = new StringBuilder();
       

        if (ConfigurationHelper.getInstance().isUseH2DB()) {
	        batches.append("SELECT max(id) as id, ");
	        batches.append("max(batchName) as batchName, ");
	        batches.append("max(startDate) as startDate, ");
	        batches.append("max(endDate) as endDate, ");
	        batches.append("count(prozesseId) as totalProcesses ");
	        batches.append("FROM batches join prozesse ");
	        batches.append("on prozesse.batchID = batches.id WHERE ");
	        batches.append("(startDate BETWEEN '");
	        batches.append(formatter.print(selectedStartDate));
	        batches.append("' AND '");
	        batches.append(formatter.print(selectedEndDate));
	        batches.append("') OR ");
	        batches.append("(endDate BETWEEN '");
	        batches.append(formatter.print(selectedStartDate));
	        batches.append("' AND '");
	        batches.append(formatter.print(selectedEndDate));
	        batches.append("') OR ");
	        batches.append("(startDate between '0000-01-01' AND '");
	        batches.append(formatter.print(selectedStartDate));
	        batches.append("' AND endDate between '");
	        batches.append(formatter.print(selectedEndDate));
	        batches.append("' AND '9999-12-31') ");
	        batches.append("group by batches.id");
        } else {
        	 	batches.append("SELECT max(id) as id, ");
             batches.append("max(batchName) as batchName, ");
             batches.append("max(startDate) as startDate, ");
             batches.append("max(endDate) as endDate, ");
             batches.append("count(prozesseId) as totalProcesses ");
             batches.append("FROM batches join prozesse ");
             batches.append("on prozesse.batchID = batches.id WHERE ");
             batches.append("(startDate BETWEEN DATE(\"");
             batches.append(formatter.print(selectedStartDate));
             batches.append("\") AND DATE(\"");
             batches.append(formatter.print(selectedEndDate));
             batches.append("\")) OR ");
             batches.append("(endDate BETWEEN DATE(\"");
             batches.append(formatter.print(selectedStartDate));
             batches.append("\") AND DATE(\"");
             batches.append(formatter.print(selectedEndDate));
             batches.append("\")) OR ");
             batches.append("(startDate < DATE(\"");
             batches.append(formatter.print(selectedStartDate));
             batches.append("\") AND endDate > DATE(\"");
             batches.append(formatter.print(selectedEndDate));
             batches.append("\")) ");
             batches.append("group by batches.id");
        }
        
        String batchDetails =
                "select bearbeitungsstatus as one, count(schritteid) as two from schritte left join prozesse on schritte.prozesseid = prozesse.prozesseid where prozesse.batchId = ? group by bearbeitungsstatus";

        Connection connection = null;
        try {
            connection = MySQLHelper.getInstance().getConnection();
            QueryRunner run = new QueryRunner();
            batchesInInterval = run.query(connection, batches.toString(), new BeanListHandler<DashboardHelperBatch>(DashboardHelperBatch.class));
            for (DashboardHelperBatch batch : batchesInInterval) {

                List<StringPair> spl = run.query(connection, batchDetails, rsh, batch.getId());
                for (StringPair row : spl) {
                    int numberOfTasks = Integer.parseInt(row.getTwo());
                    switch (row.getOne()) {
                        case "0":
                            batch.setNumberOfLockedTasks(numberOfTasks);
                            break;
                        case "1":
                            batch.setNumberOfOpenTasks(numberOfTasks);
                            break;
                        case "2":
                            batch.setNumberOfTasksInWork(numberOfTasks);
                            break;
                        case "3":
                            batch.setNumberOfFinishedTasks(numberOfTasks);
                            break;
                        case "4":
                            batch.setNumberOfErrorTasks(numberOfTasks);
                            break;
                        case "5":
                            batch.setNumberOfDeactivatedTasks(numberOfTasks);
                            break;
                    }
                }
            }
        } catch (SQLException e) {
            log.error(e);
        } finally {
            if (connection != null) {
                try {
                    MySQLHelper.closeConnection(connection);
                } catch (SQLException e) {
                    log.error(e);
                }
            }
        }
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
        return PLUGIN_NAME;
    }

    @Override
    public String getGuiPath() {
        return "/uii/plugin_dashboard_batch.xhtml";
    }

    private static ResultSetHandler<List<StringPair>> rsh = new ResultSetHandler<List<StringPair>>() {
        public List<StringPair> handle(ResultSet rs) {
            List<StringPair> list = new ArrayList<>();
            try {
                while (rs.next()) {
                    StringPair sp = new StringPair(rs.getString("one"), rs.getString("two"));
                    list.add(sp);
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return list;
        }
    };

}
