
package de.intranda.digiverso.model.nagios;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
import jakarta.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class IcingaStatus {

    @SerializedName("status_data_age")
    @Expose
    private Integer statusDataAge;
    @SerializedName("status_update_interval")
    @Expose
    private Integer statusUpdateInterval;
    @SerializedName("reading_status_data_ok")
    @Expose
    private Boolean readingStatusDataOk;
    @SerializedName("program_version")
    @Expose
    private String programVersion;
    @SerializedName("icinga_pid")
    @Expose
    private Integer icingaPid;
    @Expose
    private String timezone;
    @SerializedName("date_format")
    @Expose
    private String dateFormat;
    @SerializedName("program_start")
    @Expose
    private Integer programStart;
    @SerializedName("total_running_time")
    @Expose
    private String totalRunningTime;
    @SerializedName("last_external_command_check")
    @Expose
    private Integer lastExternalCommandCheck;
    @SerializedName("last_log_file_rotation")
    @Expose
    private Integer lastLogFileRotation;
    @SerializedName("notifications_enabled")
    @Expose
    private Boolean notificationsEnabled;
    @SerializedName("disable_notifications_expire_time")
    @Expose
    private Integer disableNotificationsExpireTime;
    @SerializedName("service_checks_being_executed")
    @Expose
    private Boolean serviceChecksBeingExecuted;
    @SerializedName("passive_service_checks_being_accepted")
    @Expose
    private Boolean passiveServiceChecksBeingAccepted;
    @SerializedName("host_checks_being_executed")
    @Expose
    private Boolean hostChecksBeingExecuted;
    @SerializedName("passive_host_checks_being_accepted")
    @Expose
    private Boolean passiveHostChecksBeingAccepted;
    @SerializedName("obsessing_over_services")
    @Expose
    private Boolean obsessingOverServices;
    @SerializedName("obsessing_over_hosts")
    @Expose
    private Boolean obsessingOverHosts;
    @SerializedName("check_service_freshness")
    @Expose
    private Boolean checkServiceFreshness;
    @SerializedName("check_host_freshness")
    @Expose
    private Boolean checkHostFreshness;
    @SerializedName("event_handlers_enabled")
    @Expose
    private Boolean eventHandlersEnabled;
    @SerializedName("flap_detection_enabled")
    @Expose
    private Boolean flapDetectionEnabled;
    @SerializedName("performance_data_being_processed")
    @Expose
    private Boolean performanceDataBeingProcessed;

    /**
     * 
     * @return The statusDataAge
     */
    public Integer getStatusDataAge() {
        return statusDataAge;
    }

    /**
     * 
     * @param statusDataAge The status_data_age
     */
    public void setStatusDataAge(Integer statusDataAge) {
        this.statusDataAge = statusDataAge;
    }

    /**
     * 
     * @return The statusUpdateInterval
     */
    public Integer getStatusUpdateInterval() {
        return statusUpdateInterval;
    }

    /**
     * 
     * @param statusUpdateInterval The status_update_interval
     */
    public void setStatusUpdateInterval(Integer statusUpdateInterval) {
        this.statusUpdateInterval = statusUpdateInterval;
    }

    /**
     * 
     * @return The readingStatusDataOk
     */
    public Boolean getReadingStatusDataOk() {
        return readingStatusDataOk;
    }

    /**
     * 
     * @param readingStatusDataOk The reading_status_data_ok
     */
    public void setReadingStatusDataOk(Boolean readingStatusDataOk) {
        this.readingStatusDataOk = readingStatusDataOk;
    }

    /**
     * 
     * @return The programVersion
     */
    public String getProgramVersion() {
        return programVersion;
    }

    /**
     * 
     * @param programVersion The program_version
     */
    public void setProgramVersion(String programVersion) {
        this.programVersion = programVersion;
    }

    /**
     * 
     * @return The icingaPid
     */
    public Integer getIcingaPid() {
        return icingaPid;
    }

    /**
     * 
     * @param icingaPid The icinga_pid
     */
    public void setIcingaPid(Integer icingaPid) {
        this.icingaPid = icingaPid;
    }

    /**
     * 
     * @return The timezone
     */
    public String getTimezone() {
        return timezone;
    }

    /**
     * 
     * @param timezone The timezone
     */
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    /**
     * 
     * @return The dateFormat
     */
    public String getDateFormat() {
        return dateFormat;
    }

    /**
     * 
     * @param dateFormat The date_format
     */
    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    /**
     * 
     * @return The programStart
     */
    public Integer getProgramStart() {
        return programStart;
    }

    /**
     * 
     * @param programStart The program_start
     */
    public void setProgramStart(Integer programStart) {
        this.programStart = programStart;
    }

    /**
     * 
     * @return The totalRunningTime
     */
    public String getTotalRunningTime() {
        return totalRunningTime;
    }

    /**
     * 
     * @param totalRunningTime The total_running_time
     */
    public void setTotalRunningTime(String totalRunningTime) {
        this.totalRunningTime = totalRunningTime;
    }

    /**
     * 
     * @return The lastExternalCommandCheck
     */
    public Integer getLastExternalCommandCheck() {
        return lastExternalCommandCheck;
    }

    /**
     * 
     * @param lastExternalCommandCheck The last_external_command_check
     */
    public void setLastExternalCommandCheck(Integer lastExternalCommandCheck) {
        this.lastExternalCommandCheck = lastExternalCommandCheck;
    }

    /**
     * 
     * @return The lastLogFileRotation
     */
    public Integer getLastLogFileRotation() {
        return lastLogFileRotation;
    }

    /**
     * 
     * @param lastLogFileRotation The last_log_file_rotation
     */
    public void setLastLogFileRotation(Integer lastLogFileRotation) {
        this.lastLogFileRotation = lastLogFileRotation;
    }

    /**
     * 
     * @return The notificationsEnabled
     */
    public Boolean getNotificationsEnabled() {
        return notificationsEnabled;
    }

    /**
     * 
     * @param notificationsEnabled The notifications_enabled
     */
    public void setNotificationsEnabled(Boolean notificationsEnabled) {
        this.notificationsEnabled = notificationsEnabled;
    }

    /**
     * 
     * @return The disableNotificationsExpireTime
     */
    public Integer getDisableNotificationsExpireTime() {
        return disableNotificationsExpireTime;
    }

    /**
     * 
     * @param disableNotificationsExpireTime The disable_notifications_expire_time
     */
    public void setDisableNotificationsExpireTime(Integer disableNotificationsExpireTime) {
        this.disableNotificationsExpireTime = disableNotificationsExpireTime;
    }

    /**
     * 
     * @return The serviceChecksBeingExecuted
     */
    public Boolean getServiceChecksBeingExecuted() {
        return serviceChecksBeingExecuted;
    }

    /**
     * 
     * @param serviceChecksBeingExecuted The service_checks_being_executed
     */
    public void setServiceChecksBeingExecuted(Boolean serviceChecksBeingExecuted) {
        this.serviceChecksBeingExecuted = serviceChecksBeingExecuted;
    }

    /**
     * 
     * @return The passiveServiceChecksBeingAccepted
     */
    public Boolean getPassiveServiceChecksBeingAccepted() {
        return passiveServiceChecksBeingAccepted;
    }

    /**
     * 
     * @param passiveServiceChecksBeingAccepted The passive_service_checks_being_accepted
     */
    public void setPassiveServiceChecksBeingAccepted(Boolean passiveServiceChecksBeingAccepted) {
        this.passiveServiceChecksBeingAccepted = passiveServiceChecksBeingAccepted;
    }

    /**
     * 
     * @return The hostChecksBeingExecuted
     */
    public Boolean getHostChecksBeingExecuted() {
        return hostChecksBeingExecuted;
    }

    /**
     * 
     * @param hostChecksBeingExecuted The host_checks_being_executed
     */
    public void setHostChecksBeingExecuted(Boolean hostChecksBeingExecuted) {
        this.hostChecksBeingExecuted = hostChecksBeingExecuted;
    }

    /**
     * 
     * @return The passiveHostChecksBeingAccepted
     */
    public Boolean getPassiveHostChecksBeingAccepted() {
        return passiveHostChecksBeingAccepted;
    }

    /**
     * 
     * @param passiveHostChecksBeingAccepted The passive_host_checks_being_accepted
     */
    public void setPassiveHostChecksBeingAccepted(Boolean passiveHostChecksBeingAccepted) {
        this.passiveHostChecksBeingAccepted = passiveHostChecksBeingAccepted;
    }

    /**
     * 
     * @return The obsessingOverServices
     */
    public Boolean getObsessingOverServices() {
        return obsessingOverServices;
    }

    /**
     * 
     * @param obsessingOverServices The obsessing_over_services
     */
    public void setObsessingOverServices(Boolean obsessingOverServices) {
        this.obsessingOverServices = obsessingOverServices;
    }

    /**
     * 
     * @return The obsessingOverHosts
     */
    public Boolean getObsessingOverHosts() {
        return obsessingOverHosts;
    }

    /**
     * 
     * @param obsessingOverHosts The obsessing_over_hosts
     */
    public void setObsessingOverHosts(Boolean obsessingOverHosts) {
        this.obsessingOverHosts = obsessingOverHosts;
    }

    /**
     * 
     * @return The checkServiceFreshness
     */
    public Boolean getCheckServiceFreshness() {
        return checkServiceFreshness;
    }

    /**
     * 
     * @param checkServiceFreshness The check_service_freshness
     */
    public void setCheckServiceFreshness(Boolean checkServiceFreshness) {
        this.checkServiceFreshness = checkServiceFreshness;
    }

    /**
     * 
     * @return The checkHostFreshness
     */
    public Boolean getCheckHostFreshness() {
        return checkHostFreshness;
    }

    /**
     * 
     * @param checkHostFreshness The check_host_freshness
     */
    public void setCheckHostFreshness(Boolean checkHostFreshness) {
        this.checkHostFreshness = checkHostFreshness;
    }

    /**
     * 
     * @return The eventHandlersEnabled
     */
    public Boolean getEventHandlersEnabled() {
        return eventHandlersEnabled;
    }

    /**
     * 
     * @param eventHandlersEnabled The event_handlers_enabled
     */
    public void setEventHandlersEnabled(Boolean eventHandlersEnabled) {
        this.eventHandlersEnabled = eventHandlersEnabled;
    }

    /**
     * 
     * @return The flapDetectionEnabled
     */
    public Boolean getFlapDetectionEnabled() {
        return flapDetectionEnabled;
    }

    /**
     * 
     * @param flapDetectionEnabled The flap_detection_enabled
     */
    public void setFlapDetectionEnabled(Boolean flapDetectionEnabled) {
        this.flapDetectionEnabled = flapDetectionEnabled;
    }

    /**
     * 
     * @return The performanceDataBeingProcessed
     */
    public Boolean getPerformanceDataBeingProcessed() {
        return performanceDataBeingProcessed;
    }

    /**
     * 
     * @param performanceDataBeingProcessed The performance_data_being_processed
     */
    public void setPerformanceDataBeingProcessed(Boolean performanceDataBeingProcessed) {
        this.performanceDataBeingProcessed = performanceDataBeingProcessed;
    }

}
