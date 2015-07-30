package de.intranda.digiverso.model.itm;

import java.io.Serializable;

public class JobImpl extends AbstractJob implements IJob, Serializable{

    private static final long serialVersionUID = 5122860664528593325L;
    
    private String requestString;

    public JobImpl(int iD, long timestampStart, long timestampEnd, String status, String jobType, String goobiId, String errorMessage, String stepID,
            String title, int pagenumber, long size, String sourceDir, int priority, String targetDir, String template, String requestString) {
        super(iD, timestampStart, timestampEnd, status, jobType, goobiId, errorMessage, stepID, title, priority, pagenumber, size, sourceDir, targetDir, template);
        this.requestString = requestString;
    }

    public JobImpl(int id, String status, String errorMessage) {
        this.setStatus(status);
        this.setErrorMessage(errorMessage);
        //TODO: delete this in the end
        this.setSourceDir("/opt/digiverso/metadata/234/ocr/");
        this.setID(id);
    }

    public String getRequestString() {
        return requestString;
    }

    @Override
    public String getLanguage() {
        // TODO Auto-generated method stub
        return "ger";
    }    
    
    public boolean isFracture() {
        return false;
    }

}
