
package de.intranda.digiverso.model.nagios;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Nagios {

    @SerializedName("cgi_json_version")
    @Expose
    private String cgiJsonVersion;
    @SerializedName("icinga_status")
    @Expose
    private IcingaStatus icingaStatus;
    @Expose
    private Status status;

    /**
     * 
     * @return
     *     The cgiJsonVersion
     */
    public String getCgiJsonVersion() {
        return cgiJsonVersion;
    }

    /**
     * 
     * @param cgiJsonVersion
     *     The cgi_json_version
     */
    public void setCgiJsonVersion(String cgiJsonVersion) {
        this.cgiJsonVersion = cgiJsonVersion;
    }

    /**
     * 
     * @return
     *     The icingaStatus
     */
    public IcingaStatus getIcingaStatus() {
        return icingaStatus;
    }

    /**
     * 
     * @param icingaStatus
     *     The icinga_status
     */
    public void setIcingaStatus(IcingaStatus icingaStatus) {
        this.icingaStatus = icingaStatus;
    }

    /**
     * 
     * @return
     *     The status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

}
