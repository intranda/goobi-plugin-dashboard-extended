package de.intranda.goobi.plugins;

import java.util.List;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import org.goobi.beans.Step;
import org.goobi.production.enums.PluginType;
import org.goobi.production.flow.statistics.hibernate.FilterHelper;
import org.goobi.production.plugin.interfaces.IDashboardPlugin;

import de.sub.goobi.helper.Helper;
import de.sub.goobi.persistence.managers.StepManager;

@PluginImplementation
public class TestPlugin implements IDashboardPlugin {

    @Override
    public PluginType getType() {
        return PluginType.Dashboard;
    }

    @Override
    public String getTitle() {
        return "TestPlugin";
    }

    @Override
    public String getDescription() {
        return "TestPlugin";
    }

    public List<Step> getAssignedSteps() {
        if (Helper.getCurrentUser() != null) {
            String sql = FilterHelper.criteriaBuilder("", false, false, true, true, false, true);

            return StepManager.getSteps("BearbeitungsBeginn desc", sql, 0, 5);
        }
        return null;
    }

    @Override
    public String getGuiPath() {
        return "TestPlugin.xhtml";
    }

}
