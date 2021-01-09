package de.intranda.digiverso.model.itm;

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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.WordUtils;

public abstract class AbstractJob implements IJob {
    private static DecimalFormat formatter = null;

    private int id;
    private long timestampStart;
    private long timestampEnd;
    private int priority;
    private String status;
    private String jobType;
    private String goobiId;
    private String errorMessage;
    private String stepId;
    private String title;
    private int pagenumber;
    private int abbyyPages;
    private long size;
    private String sourceDir;
    private String targetDir;
    private String template;

    public AbstractJob() {
        super();
    }

    public AbstractJob(int iD, long timestampStart, long timestampEnd, String status, String jobType, String goobiId, String errorMessage,
            String step_id, String title, int priority, int pagenumber, long size, String sourceDir, String targetDir, String template) {
        super();
        id = iD;
        this.timestampStart = timestampStart;
        this.timestampEnd = timestampEnd;
        this.status = status;
        this.jobType = jobType;
        this.goobiId = goobiId;
        this.errorMessage = errorMessage;
        this.stepId = step_id;
        this.title = title;
        this.priority = priority;
        this.pagenumber = pagenumber;
        this.size = size;
        this.sourceDir = sourceDir;
        this.targetDir = targetDir;
        this.template = template;
    }

    public AbstractJob(AbstractJob job) {
        super();
        this.id = job.id;
        this.timestampStart = job.timestampStart;
        this.timestampEnd = job.timestampEnd;
        this.status = job.status;
        this.jobType = job.jobType;
        this.goobiId = job.goobiId;
        this.errorMessage = job.errorMessage;
        this.stepId = job.stepId;
        this.title = job.title;
        this.priority = job.priority;
        this.pagenumber = job.pagenumber;
        this.size = job.size;
        this.sourceDir = job.sourceDir;
        this.targetDir = job.targetDir;
        this.template = job.template;
    }

    @Override
    public String getTimestampEndAsDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.GERMANY);
        Date date = new Date(getTimestampEnd());
        return format.format(date);
    }

    @Override
    public String getTimestampStartAsDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.GERMANY);
        Date date = new Date(getTimestampStart());
        return format.format(date);
    }

    @Override
    public String getGoobiId() {
        if (this.goobiId == null || this.goobiId.isEmpty()) {
            if (this.getSourceDir() == null) {
                return null;
            }
            String id = "";
            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(this.getSourceDir());
            if (m.find()) {
                id = m.group();
            }
            return id;
        } else {
            return this.goobiId;
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((errorMessage == null) ? 0 : errorMessage.hashCode());
        result = prime * result + ((goobiId == null) ? 0 : goobiId.hashCode());
        result = prime * result + id;
        result = prime * result + ((jobType == null) ? 0 : jobType.hashCode());
        result = prime * result + priority;
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((stepId == null) ? 0 : stepId.hashCode());
        result = prime * result + (int) (timestampEnd ^ (timestampEnd >>> 32));
        result = prime * result + (int) (timestampStart ^ (timestampStart >>> 32));
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        AbstractJob other = (AbstractJob) obj;
        if (errorMessage == null) {
            if (other.errorMessage != null) {
                return false;
            }
        } else if (!errorMessage.equals(other.errorMessage)) {
            return false;
        }
        if (goobiId == null) {
            if (other.goobiId != null) {
                return false;
            }
        } else if (!goobiId.equals(other.goobiId)) {
            return false;
        }
        if (id != other.id) {
            return false;
        }
        if (jobType == null) {
            if (other.jobType != null) {
                return false;
            }
        } else if (!jobType.equals(other.jobType)) {
            return false;
        }
        if (priority != other.priority) {
            return false;
        }
        if (status == null) {
            if (other.status != null) {
                return false;
            }
        } else if (!status.equals(other.status)) {
            return false;
        }
        if (stepId == null) {
            if (other.stepId != null) {
                return false;
            }
        } else if (!stepId.equals(other.stepId)) {
            return false;
        }
        if (timestampEnd != other.timestampEnd) {
            return false;
        }
        if (timestampStart != other.timestampStart) {
            return false;
        }
        if (title == null) {
            if (other.title != null) {
                return false;
            }
        } else if (!title.equals(other.title)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "itm-" + this.getJobType() + "-Job " + this.getId() + ": ";
    }

    @Override
    public int getId() {
        return id;
    }

    public void setID(int iD) {
        this.id = iD;
    }

    public long getTimestampStart() {
        return timestampStart;
    }

    public void setTimestampStart(long timestampStart) {
        this.timestampStart = timestampStart;
    }

    @Override
    public long getTimestampEnd() {
        return timestampEnd;
    }

    public void setTimestampEnd(long timestampEnd) {
        this.timestampEnd = timestampEnd;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.timestampEnd = new Date().getTime();
        this.status = status;
    }

    @Override
    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    @Override
    public String getGoobiProcessTitle() {
        return title;
    }

    public void setGoobiProcessTitle(String goobiProcessTitle) {
        this.title = goobiProcessTitle;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String getErrorMessageShort() {
        return WordUtils.abbreviate(errorMessage, 15, 28, "...");
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public void addErrorMessage(String errorMessage) {
        this.errorMessage += errorMessage;
    }

    @Override
    public String getStepId() {
        return stepId;
    }

    public void setStepId(String stepId) {
        this.stepId = stepId;
    }

    @Override
    public int getPagenumber() {
        return pagenumber;
    }

    public void setPagenumber(int pagenumber) {
        this.pagenumber = pagenumber;
    }

    @Override
    public long getSize() {
        return size;
    }

    @Override
    public String getSizeInMB() {
        formatter = (DecimalFormat) NumberFormat.getInstance(Locale.GERMAN);
        return formatter.format(this.size / 1048576) + " MB";
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public String getSourceDir() {
        return sourceDir;
    }

    public void setSourceDir(String sourceDir) {
        this.sourceDir = sourceDir;
    }

    @Override
    public int getProgress() {
        //TODO: implement in all other jobs, not in abstractjob! Just for test issues!!!
        if (IJob.STARTED.equals(this.status)) {
            return 10;
        }
        if (IJob.PROCESSING.equals(this.status)) {
            return 50;
        }
        if (IJob.DONE.equals(this.status)) {
            return 100;
        }
        if ("UPLOAD".equals(this.status)) {
            return 30;
        }
        if ("DOWNLOAD".equals(this.status)) {
            return 70;
        }
        if (IJob.ERROR.equals(this.status)) {
            return 0;
        }
        return 10;
    }

    @Override
    public String getTemplate() {
        return template;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public void setPriority(int priority) {
        if (priority >= 0) {
            this.priority = priority;
        } else {
            this.priority = 0;
        }
    }

    @Override
    public String getTargetDir() {
        return targetDir;
    }

    public void printSomething() {
        System.out.println(this.errorMessage);
    }

    public static String toString(IJob job) {
        return "itm-" + job.getJobType() + "-Job " + job.getId() + " with template " + job.getTemplate() + ": ";
    }

    @Override
    public int getAbbyyPages() {
        return abbyyPages;
    }

    @Override
    public void setAbbyyPages(int abbyyPages) {
        this.abbyyPages = abbyyPages;
    }

}
