package de.intranda.digiverso.model.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.XMLConfiguration;

import com.google.gson.Gson;

import de.intranda.digiverso.model.nagios.Host;
import de.intranda.digiverso.model.nagios.Nagios;
import de.sub.goobi.helper.HttpClientHelper;

public class DashboardHelperNagios {

	private XMLConfiguration config;
	private Long lastRead = null;
	private List<Host> hosts = null;
	
	public DashboardHelperNagios(XMLConfiguration xmlConfiguration) {
		config = xmlConfiguration;
	}
	
	private void readMonitoring() {
		Long now = System.currentTimeMillis();
		if (isShowNagios() && (lastRead == null || now - lastRead > config.getInt("nagios-cache-time", 180000))) {
			String serverLogin = config.getString("nagios-login","nagiosadmin");
			String serverPassword = config.getString("nagios-password","password"); 
		
			// run through all configured hosts
			hosts = new ArrayList<Host>();
	        int numberOfHosts = config.getMaxIndex("nagios-host");
	        for (int i = 0; i <= numberOfHosts; i++) {
	            String url = config.getString("nagios-host(" + i + ")");
	            String serverURL = "http://monitoring.intranda.com/icinga/cgi-bin/status.cgi?host=" + url + "&jsonoutput";
	    		String json = HttpClientHelper.getStringFromUrl(serverURL, serverLogin, serverPassword, "monitoring.intranda.com", "80");
			    Gson gson = new Gson();
			    Host host = new Host(url);
		        host.setNagios(gson.fromJson(json, Nagios.class));
			    hosts.add(host);
		    }
			lastRead = System.currentTimeMillis();
		}
	}
	
	public boolean isShowNagios(){
		return config.getBoolean("nagios-show", true);
	}

	public Long getLastRead() {
		return lastRead;
	}
	
	public void setLastRead(Long inLastRead) {
		this.lastRead = inLastRead;
	}
	
	public List<Host> getHosts() {
		readMonitoring();
		return hosts;
	}

}
