package de.intranda.digiverso.model.tasks;

import java.text.DateFormat;
import java.util.Locale;

import org.goobi.beans.Process;
import org.goobi.beans.Step;

import de.sub.goobi.helper.FacesContextHelper;
import de.sub.goobi.helper.enums.StepStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class TaskChangeType {

    @Getter
    private Step closedStep;
    @Getter
    private Step followingStep;

    @Getter
    private Process process;

    /**
     * Check if the closed step can be opened again. This is possible, when it is either the last step of the process or when the following step still
     * has the status open.
     * 
     * @return
     */

    public boolean isAssignable() {
        return followingStep == null || followingStep.getBearbeitungsstatusEnum() == StepStatus.OPEN
                || followingStep.getBearbeitungsstatusEnum() == StepStatus.LOCKED;
    }

    public String getClosedDate() {
        DateFormat dateFormat = getDateFormat(DateFormat.DEFAULT);
        return dateFormat.format(closedStep.getBearbeitungsende());
    }

    public String getClosedTime() {
        DateFormat dateFormat = getTimeFormat(DateFormat.MEDIUM);
        return dateFormat.format(closedStep.getBearbeitungsende());
    }

    private DateFormat getTimeFormat(int formatType) {
        DateFormat dateFormat = DateFormat.getTimeInstance(formatType);
        Locale userLang = FacesContextHelper.getCurrentFacesContext().getViewRoot().getLocale();
        if (userLang != null) {
            dateFormat = DateFormat.getTimeInstance(formatType, userLang);
        }
        return dateFormat;
    }

    private DateFormat getDateFormat(int formatType) {
        DateFormat dateFormat = DateFormat.getDateInstance(formatType);
        Locale userLang = FacesContextHelper.getCurrentFacesContext().getViewRoot().getLocale();
        if (userLang != null) {
            dateFormat = DateFormat.getDateInstance(formatType, userLang);
        }
        return dateFormat;
    }
}
