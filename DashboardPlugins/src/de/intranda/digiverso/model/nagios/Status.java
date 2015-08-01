
package de.intranda.digiverso.model.nagios;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Status {

    @SerializedName("service_status")
    @Expose
    private List<ServiceStatu> serviceStatus = new ArrayList<ServiceStatu>();

    /**
     * 
     * @return
     *     The serviceStatus
     */
    public List<ServiceStatu> getServiceStatus() {
        return serviceStatus;
    }

    /**
     * 
     * @param serviceStatus
     *     The service_status
     */
    public void setServiceStatus(List<ServiceStatu> serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

}
