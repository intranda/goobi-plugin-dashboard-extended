package de.intranda.digiverso.model.itm;

public interface IJob {

    public static final String STARTED = "STARTED";
    public static final String PROCESSING = "PROCESSING";
    public static final String DONE = "DONE";
    public static final String ERROR = "ERROR";
    public static final String PAUSED = "PAUSED";
    public static final String CANCELLED = "CANCELLED";

    public String getStatus();

    public String getErrorMessage();
    
    public String getErrorMessageShort();

    public String getGoobiProcessTitle();
    
    public String getGoobiId();

    public int getPriority();

    public long getSize();
    
    public String getSizeInMB();
    
    public int getProgress();

    public String getTimestampEndAsDate();
    
    public String getTimestampStartAsDate();
    
    public String getStepId();
    
    public int getId();
    
    public long getTimestampEnd();
    
    public void addErrorMessage(String message);
    
    public void setPriority(int priority);
    
    public int getPagenumber();
    
    public String getLanguage();
    
    public String getSourceDir();
    
    public String getTargetDir();
    
    public String getJobType();
    
    public void setStatus(String status);
    
    public String getTemplate();
    
    public int getAbbyyPages();
    
    public void setAbbyyPages(int abbyyPages);
    
}
