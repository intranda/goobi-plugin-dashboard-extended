package de.intranda.digiverso.model.helper;

import java.util.List;

import org.apache.commons.configuration.XMLConfiguration;
import org.goobi.beans.Step;
import org.goobi.production.flow.statistics.hibernate.FilterHelper;

import de.sub.goobi.helper.Helper;
import de.sub.goobi.persistence.managers.StepManager;

public class DashboardHelperTasks {
	private XMLConfiguration config;
	
	public DashboardHelperTasks(XMLConfiguration pluginConfig) {
		config = pluginConfig;
	}
	
	public boolean isShowTasks() {
		return config.getBoolean("tasks-show", true);
	}
	
	public List<Step> getAssignedSteps() {
		if (Helper.getCurrentUser() != null) {
			String sql = FilterHelper.criteriaBuilder("", false, false, true, true, false, true);
			return StepManager.getSteps("BearbeitungsBeginn desc", sql, 0, 10);
		}
		return null;
	}
}
