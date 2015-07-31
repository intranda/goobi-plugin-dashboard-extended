package de.intranda.digiverso.model.tasks;

import java.util.List;

import org.goobi.beans.Step;
import org.goobi.production.flow.statistics.hibernate.FilterHelper;

import de.sub.goobi.helper.Helper;
import de.sub.goobi.persistence.managers.StepManager;

public class DashboardHelperTasks {
	
	public static List<Step> getAssignedSteps() {
		if (Helper.getCurrentUser() != null) {
			String sql = FilterHelper.criteriaBuilder("", false, false, true, true, false, true);
			return StepManager.getSteps("BearbeitungsBeginn desc", sql, 0, 10);
		}
		return null;
	}
}
