
package de.intranda.digiverso.model.nagios;

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
import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class ServiceStatu {

    @SerializedName("host_name")
    @Expose
    private String hostName;
    @SerializedName("host_display_name")
    @Expose
    private String hostDisplayName;
    @SerializedName("service_description")
    @Expose
    private String serviceDescription;
    @SerializedName("service_display_name")
    @Expose
    private String serviceDisplayName;
    @Expose
    private String status;
    @SerializedName("last_check")
    @Expose
    private String lastCheck;
    @Expose
    private String duration;
    @Expose
    private String attempts;
    @SerializedName("state_type")
    @Expose
    private String stateType;
    @SerializedName("is_flapping")
    @Expose
    private Boolean isFlapping;
    @SerializedName("in_scheduled_downtime")
    @Expose
    private Boolean inScheduledDowntime;
    @SerializedName("active_checks_enabled")
    @Expose
    private Boolean activeChecksEnabled;
    @SerializedName("passive_checks_enabled")
    @Expose
    private Boolean passiveChecksEnabled;
    @SerializedName("notifications_enabled")
    @Expose
    private Boolean notificationsEnabled;
    @SerializedName("has_been_acknowledged")
    @Expose
    private Boolean hasBeenAcknowledged;
    @SerializedName("action_url")
    @Expose
    private Object actionUrl;
    @SerializedName("notes_url")
    @Expose
    private Object notesUrl;
    @SerializedName("status_information")
    @Expose
    private String statusInformation;

    /**
     * 
     * @return The hostName
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * 
     * @param hostName The host_name
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    /**
     * 
     * @return The hostDisplayName
     */
    public String getHostDisplayName() {
        return hostDisplayName;
    }

    /**
     * 
     * @param hostDisplayName The host_display_name
     */
    public void setHostDisplayName(String hostDisplayName) {
        this.hostDisplayName = hostDisplayName;
    }

    /**
     * 
     * @return The serviceDescription
     */
    public String getServiceDescription() {
        return serviceDescription;
    }

    /**
     * 
     * @param serviceDescription The service_description
     */
    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    /**
     * 
     * @return The serviceDisplayName
     */
    public String getServiceDisplayName() {
        return serviceDisplayName;
    }

    /**
     * 
     * @param serviceDisplayName The service_display_name
     */
    public void setServiceDisplayName(String serviceDisplayName) {
        this.serviceDisplayName = serviceDisplayName;
    }

    /**
     * 
     * @return The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 
     * @return The lastCheck
     */
    public String getLastCheck() {
        return lastCheck;
    }

    /**
     * 
     * @param lastCheck The last_check
     */
    public void setLastCheck(String lastCheck) {
        this.lastCheck = lastCheck;
    }

    /**
     * 
     * @return The duration
     */
    public String getDuration() {
        return duration;
    }

    /**
     * 
     * @param duration The duration
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /**
     * 
     * @return The attempts
     */
    public String getAttempts() {
        return attempts;
    }

    /**
     * 
     * @param attempts The attempts
     */
    public void setAttempts(String attempts) {
        this.attempts = attempts;
    }

    /**
     * 
     * @return The stateType
     */
    public String getStateType() {
        return stateType;
    }

    /**
     * 
     * @param stateType The state_type
     */
    public void setStateType(String stateType) {
        this.stateType = stateType;
    }

    /**
     * 
     * @return The isFlapping
     */
    public Boolean getIsFlapping() {
        return isFlapping;
    }

    /**
     * 
     * @param isFlapping The is_flapping
     */
    public void setIsFlapping(Boolean isFlapping) {
        this.isFlapping = isFlapping;
    }

    /**
     * 
     * @return The inScheduledDowntime
     */
    public Boolean getInScheduledDowntime() {
        return inScheduledDowntime;
    }

    /**
     * 
     * @param inScheduledDowntime The in_scheduled_downtime
     */
    public void setInScheduledDowntime(Boolean inScheduledDowntime) {
        this.inScheduledDowntime = inScheduledDowntime;
    }

    /**
     * 
     * @return The activeChecksEnabled
     */
    public Boolean getActiveChecksEnabled() {
        return activeChecksEnabled;
    }

    /**
     * 
     * @param activeChecksEnabled The active_checks_enabled
     */
    public void setActiveChecksEnabled(Boolean activeChecksEnabled) {
        this.activeChecksEnabled = activeChecksEnabled;
    }

    /**
     * 
     * @return The passiveChecksEnabled
     */
    public Boolean getPassiveChecksEnabled() {
        return passiveChecksEnabled;
    }

    /**
     * 
     * @param passiveChecksEnabled The passive_checks_enabled
     */
    public void setPassiveChecksEnabled(Boolean passiveChecksEnabled) {
        this.passiveChecksEnabled = passiveChecksEnabled;
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
     * @return The hasBeenAcknowledged
     */
    public Boolean getHasBeenAcknowledged() {
        return hasBeenAcknowledged;
    }

    /**
     * 
     * @param hasBeenAcknowledged The has_been_acknowledged
     */
    public void setHasBeenAcknowledged(Boolean hasBeenAcknowledged) {
        this.hasBeenAcknowledged = hasBeenAcknowledged;
    }

    /**
     * 
     * @return The actionUrl
     */
    public Object getActionUrl() {
        return actionUrl;
    }

    /**
     * 
     * @param actionUrl The action_url
     */
    public void setActionUrl(Object actionUrl) {
        this.actionUrl = actionUrl;
    }

    /**
     * 
     * @return The notesUrl
     */
    public Object getNotesUrl() {
        return notesUrl;
    }

    /**
     * 
     * @param notesUrl The notes_url
     */
    public void setNotesUrl(Object notesUrl) {
        this.notesUrl = notesUrl;
    }

    /**
     * 
     * @return The statusInformation
     */
    public String getStatusInformation() {
        return statusInformation;
    }

    /**
     * 
     * @param statusInformation The status_information
     */
    public void setStatusInformation(String statusInformation) {
        this.statusInformation = statusInformation;
    }

}
