package de.intranda.digiverso.model.icinga;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class Icinga2Host {
    private String name;
    private Icinga2HostStatus status;
    private List<Icinga2Service> services = new LinkedList<>();
}
