package de.intranda.digiverso.model.icinga;

import lombok.Data;

@Data
public class Icinga2Service {
    private String name;
    private Icinga2ServiceStatus status;
    private String output;
}
