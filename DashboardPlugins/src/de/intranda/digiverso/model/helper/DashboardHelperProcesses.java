package de.intranda.digiverso.model.helper;

import java.util.Collections;
import java.util.List;

import org.apache.commons.configuration.XMLConfiguration;
import org.apache.log4j.Logger;
import org.goobi.managedbeans.DatabasePaginator;
import org.goobi.managedbeans.LoginBean;
import org.goobi.managedbeans.ProcessBean;
import org.goobi.production.flow.statistics.hibernate.FilterHelper;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

import com.sun.syndication.io.SyndFeedOutput;

import de.sub.goobi.helper.Helper;
import de.sub.goobi.persistence.managers.ProcessManager;

public class DashboardHelperProcesses {

	private static final Logger logger = Logger.getLogger(DashboardHelperProcesses.class);
	private XMLConfiguration config;

	public DashboardHelperProcesses(XMLConfiguration xmlConfiguration) {
		config = xmlConfiguration;
	}
	
	public void loadProcessTemplates() {
		ProcessBean pb = (ProcessBean) Helper.getManagedBeanValue("#{ProzessverwaltungForm}");
		if (pb.getFilter()==null) {
			pb.setFilter("");
		}
		if (pb.getFilter().length()>0 || !pb.getModusAnzeige().equals("vorlagen")){
			pb.setFilter("");
			pb.setModusAnzeige("vorlagen");
			pb.FilterVorlagen();
		}
	}
		
	public void onload() { 
//		ProcessBean pb = (ProcessBean) Helper.getManagedBeanValue("#{ProzessverwaltungForm}");
//		pb.FilterVorlagen();
	}

	public LineChartModel getProcessesPerMonth() {
		LineChartModel model = new LineChartModel();
		model.setExtender("ext");
		ChartSeries series = new ChartSeries();
		series.setLabel("Months");

		List list = ProcessManager.runSQL(
				"Select year(erstellungsdatum) as year, month(erstellungsdatum) as month, count(*) FROM prozesse WHERE IstTemplate=false  GROUP BY year, month  ORDER BY year desc, month desc LIMIT 24;");
		Collections.reverse(list);
		for (Object obj : list) {
			Object[] o = (Object[]) obj;
			String label = o[0] + "/" + o[1];
			Integer number = Integer.valueOf((String) o[2]);
			series.set(label, number);
		}

		model.addSeries(series);
		return model;
	}
	
	public boolean isShowStatistics() {
		return config.getBoolean("statistics-show", true);
	}
	
	public boolean isShowProcessTemplates() {
		if (config.getBoolean("processTemplates-show", true)) {
			onload();
			return true;
		}
		return false;
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
