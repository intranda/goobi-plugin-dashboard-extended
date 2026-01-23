
package de.intranda.digiverso.model.icinga;

import com.google.gson.annotations.SerializedName;
import jakarta.annotation.Generated;
import lombok.Data;

import java.util.List;

@Generated("org.jsonschema2pojo")
@Data
public class IcingaHosts {
    @SerializedName("results")
    private List<Host> hosts;
}
