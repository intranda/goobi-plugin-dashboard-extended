package de.intranda.digiverso.model.helper;

import java.util.List;

import org.apache.commons.configuration.XMLConfiguration;
import org.apache.log4j.Logger;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

import de.sub.goobi.persistence.managers.ProcessManager;

public class DashboardHelperProcesses {

	private static final Logger logger = Logger.getLogger(DashboardHelperProcesses.class);
	private XMLConfiguration config;

	public DashboardHelperProcesses(XMLConfiguration xmlConfiguration) {
		config = xmlConfiguration;
	}

	public LineChartModel getProcessesPerMonth() {
		LineChartModel model = new LineChartModel();
		model.setExtender("ext");
		ChartSeries series = new ChartSeries();
		series.setLabel("Months");

		List list = ProcessManager.runSQL(
				"Select year(erstellungsdatum), month(erstellungsdatum), count(*) FROM prozesse GROUP BY year(erstellungsdatum), month(erstellungsdatum);");
		
		for (Object obj : list) {
			Object[] o = (Object[]) obj;
			String label = o[0] + "/" + o[1];
			Integer number = Integer.valueOf((String) o[2]);
			series.set(label, number);
		}

		model.addSeries(series);
		
		return model;
	}

//	public List getProcessesPerMonth() {
//		List list = ProcessManager.runSQL(
//				"Select year(erstellungsdatum), month(erstellungsdatum), count(*) FROM prozesse GROUP BY year(erstellungsdatum), month(erstellungsdatum);");
//		return list;
//	}
}