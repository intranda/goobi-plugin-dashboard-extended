package de.intranda.digiverso.model.nagios;

public class Host {
	private String name;
	private String summary;
	private Nagios nagios;

	public Host(String inName) {
		name = inName;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Nagios getNagios() {
		return nagios;
	}
	
	public void setNagios(Nagios nagios) {
		this.nagios = nagios;
		calculateSummary();
	}
	
	public String getSummary() {
		return summary;
	}
	
	private void calculateSummary(){
		summary = "OK";
		if (nagios==null){
			summary = "CRITICAL";
			return;
		}
		for (ServiceStatu ss : nagios.getStatus().getServiceStatus()) {
			if (!ss.getStatus().equals("OK")){
				if (ss.getStatus().equals("CRITICAL")){
					summary ="CRITICAL";
					break;
				}else{
					summary ="WARNING";
				}
			}
		}
	}
}
