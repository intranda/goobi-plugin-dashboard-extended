package de.intranda.digiverso.model.helper;

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
import java.util.Collections;
import java.util.List;

import org.apache.commons.configuration.XMLConfiguration;
import org.apache.log4j.Logger;
import org.goobi.beans.User;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

import de.sub.goobi.helper.Helper;
import de.sub.goobi.persistence.managers.ControllingManager;
import de.sub.goobi.persistence.managers.ProcessManager;

public class DashboardHelperProcesses {

    private static final Logger logger = Logger.getLogger(DashboardHelperProcesses.class);
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
                    if (strProjecIds.length()> 1) {
                        strProjecIds += ",";       
                    }
                    strProjecIds += projectId;
                }
            }
            
            strProjecIds += ")";                      
            
            LineChartModel model = new LineChartModel();
            model.setExtender("ext");
            ChartSeries series = new ChartSeries();
            series.setLabel("Months");

            List<?> list = ProcessManager.runSQL(
                    "Select year(erstellungsdatum) as year, month(erstellungsdatum) as month, count(*) FROM prozesse WHERE IstTemplate=false AND ProjekteID in " + 
                            strProjecIds +  " GROUP BY year, month  ORDER BY year desc, month desc LIMIT 24;");
            Collections.reverse(list);
            for (Object obj : list) {
                Object[] o = (Object[]) obj;
                String label = o[0] + "/" + o[1];
                Integer number = Integer.valueOf((String) o[2]);
                series.set(label, number);
            }

            model.addSeries(series);
            this.processesPerMonth = model;
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
