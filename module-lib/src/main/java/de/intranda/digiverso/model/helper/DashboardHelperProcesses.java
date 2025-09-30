package de.intranda.digiverso.model.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.configuration.XMLConfiguration;
import org.goobi.beans.User;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.title.Title;

import de.sub.goobi.helper.Helper;
import de.sub.goobi.persistence.managers.ControllingManager;
import de.sub.goobi.persistence.managers.ProcessManager;

public class DashboardHelperProcesses {
    private XMLConfiguration config;
    private LineChartModel processesPerMonth = null;

    public DashboardHelperProcesses(XMLConfiguration xmlConfiguration) {
        config = xmlConfiguration;
    }

    public LineChartModel getProcessesPerMonth() {
        if (this.processesPerMonth == null) {

            String strProjecIds = "(";
            User user = Helper.getCurrentUser();
            if (user != null) {
                String sql = "select ProjekteID from projektbenutzer where BenutzerID = "
                        + user.getId();
                List<Object[]> rawvalues = ControllingManager.getResultsAsObjectList(sql);
                for (Object[] objArr : rawvalues) {
                    String projectId = (String) objArr[0];
                    if (strProjecIds.length() > 1) {
                        strProjecIds += ",";
                    }
                    strProjecIds += projectId;
                }
            }

            strProjecIds += ")";

            List<Object> values = new ArrayList<>();
            List<String> labels = new ArrayList<>();
            int maxValue = 0;

            List<?> list = ProcessManager.runSQL(
                    "Select year(erstellungsdatum) as year, month(erstellungsdatum) as month, count(*) FROM prozesse WHERE IstTemplate=false AND ProjekteID in "
                            +
                            strProjecIds + " GROUP BY year, month  ORDER BY year desc, month desc LIMIT 24;");
            Collections.reverse(list);
            for (Object obj : list) {
                Object[] o = (Object[]) obj;
                String label = o[0] + "/" + o[1];
                Integer number = Integer.valueOf((String) o[2]);
                values.add(number);
                labels.add(label);
                if (number > maxValue) {
                    maxValue = number;
                }
            }
            maxValue = 10000;
            processesPerMonth = new LineChartModel();
            ChartData data = new ChartData();

            LineChartDataSet dataSet = new LineChartDataSet();

            dataSet.setData(values);
            dataSet.setFill(false);
            dataSet.setLabel("");
            dataSet.setBorderColor("rgb(54, 142, 224)");
            dataSet.setTension(0.1);
            data.addChartDataSet(dataSet);

            data.setLabels(labels);

            //Options
            LineChartOptions options = new LineChartOptions();
            options.setMaintainAspectRatio(false);
            Title title = new Title();
            title.setDisplay(false);
            title.setText("Line Chart");
            options.setTitle(title);

            Title subtitle = new Title();
            subtitle.setDisplay(false);
            subtitle.setText("Line Chart Subtitle");
            options.setSubtitle(subtitle);

            Legend legend = new Legend();
            legend.setDisplay(false);
            options.setLegend(legend);
            processesPerMonth.setOptions(options);
            processesPerMonth.setData(data);

            // Use log scale if max value > 1000
            if (maxValue > 1000) {
                processesPerMonth.setExtender("chartExtenderLog");
            } else {
                processesPerMonth.setExtender("chartExtender");
            }
        }
        return this.processesPerMonth;
    }

    public boolean isShowStatistics() {
        return config.getBoolean("statistics-show", true);
    }

    public boolean isShowProcessTemplates() {
        if (config.getBoolean("processTemplates-show", true)) {
            return true;
        }
        return false;
    }

    public boolean isProcessPerMonthEmpty() {

        List<?> list = ProcessManager.runSQL(
                "Select year(erstellungsdatum) as year, month(erstellungsdatum) as month, count(*) FROM prozesse WHERE IstTemplate=false  GROUP BY year, month  ORDER BY year desc, month desc LIMIT 24;");

        return list.isEmpty();
    }

    //	public LineChartModel getProcessesPerMonth() {
    //		LineChartModel model = new LineChartModel();
    //
    ////		ChartSeries series0 = new ChartSeries();
    ////        series0.setLabel("Series 0");
    ////
    ////        series0.set(1, 0);
    ////        series0.set(2, 0);
    ////        series0.set(3, 0);
    ////        series0.set(4, 0);
    ////        series0.set(5, 0);
    ////        series0.set(6, 0);
    ////        model.addSeries(series0);
    ////
    ////
    ////        ChartSeries series1 = new ChartSeries();
    ////        series1.setLabel("Series 1");
    ////
    ////        series1.set(1, 2);
    ////        series1.set(2, 1);
    ////        series1.set(4, 3);
    ////        series1.set(13, 0);
    ////        series1.set(5, 6);
    ////        series1.set(6, 8);
    ////
    ////        ChartSeries series2 = new ChartSeries();
    ////        series2.setLabel("Series 2");
    ////
    ////        series2.set(1, 6);
    ////        series2.set(2, 3);
    ////        series2.set(4, 7);
    ////        series2.set(5, 9);
    ////        series2.set(3, 2);
    ////
    ////        model.addSeries(series1);
    ////        model.addSeries(series2);
    ////
    ////
    ////        series0.set(3, 1);
    //
    //
    //
    //
    //		model.setExtender("ext");
    //
    //		String allProjects = "select Distinct(ProjekteId) from prozesse;";
    //		List projects = ProcessManager.runSQL(allProjects);
    //		for (Object hit : projects) {
    //
    //			Object[] record = (Object[]) hit;
    //			String projectId = record[0] + "";
    //			String hitsOfProject = "Select year(erstellungsdatum), month(erstellungsdatum), count(*) FROM prozesse WHERE IstTemplate=false AND ProjekteID="+ projectId +" GROUP BY year(erstellungsdatum), month(erstellungsdatum) ORDER by erstellungsdatum desc LIMIT 24;";
    //			System.out.println(hitsOfProject);
    //
    //			ChartSeries series = new ChartSeries();
    //			series.setLabel(projectId);
    //
    //			List list = ProcessManager.runSQL(hitsOfProject);
    //			Collections.reverse(list);
    //			for (Object obj : list) {
    //				Object[] o = (Object[]) obj;
    //				String label = o[0] + "/" + o[1];
    //				Integer number = Integer.valueOf((String) o[2]);
    //				series.set(label, number);
    //			}
    //
    //			model.addSeries(series);
    //		}
    //
    //
    //
    //
    //
    //
    //
    //
    //
    ////		ChartSeries series2 = new ChartSeries();
    ////		series2.setLabel("Months");
    ////
    ////		List list2 = ProcessManager.runSQL(
    ////				"Select year(erstellungsdatum), month(erstellungsdatum), count(*) FROM prozesse WHERE IstTemplate=true GROUP BY year(erstellungsdatum), month(erstellungsdatum) ORDER by erstellungsdatum desc LIMIT 24;");
    ////		Collections.reverse(list2);
    ////		for (Object obj : list2) {
    ////			Object[] o = (Object[]) obj;
    ////			String label = o[0] + "/" + o[1];
    ////			Integer number = Integer.valueOf((String) o[2]);
    ////			series2.set(label, number);
    ////		}
    ////
    ////		model.addSeries(series2);
    //
    //		return model;
    //	}

    //	public List getProcessesPerMonth() {
    //		List list = ProcessManager.runSQL(
    //				"Select year(erstellungsdatum), month(erstellungsdatum), count(*) FROM prozesse GROUP BY year(erstellungsdatum), month(erstellungsdatum);");
    //		return list;
    //	}
}
