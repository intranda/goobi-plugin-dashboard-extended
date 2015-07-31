package de.intranda.digiverso.model.itm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.commons.configuration.XMLConfiguration;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class DashboardHelperItm {
	private Long lastReadItm = null;
	private List<DashQueuesObj> itmPluginList;
		
	public List<DashQueuesObj> getItmPlugins(XMLConfiguration xmlConfiguration) throws MalformedURLException {
		readItmInfos(xmlConfiguration);
		return itmPluginList;
	}
	
	private void readItmInfos(XMLConfiguration xmlConfiguration) throws MalformedURLException {
		Long now = System.currentTimeMillis();
		// never read or 3 min ago
		if (lastReadItm == null || now - lastReadItm > xmlConfiguration.getInt("itm-cache-time", 180000)) {
			String basisUrl = xmlConfiguration.getString("itm-url", "http://goobitest02.fritz.box/itm/") + "api?";
			Gson gson = new Gson();

			// read all plugins
			URL url = new URL(basisUrl + "action=getPlugins");
			String response = getStringFromUrl(url);
			itmPluginList = gson.fromJson(response, new TypeToken<List<DashQueuesObj>>() {
			}.getType());
			
			for (DashQueuesObj dqo : itmPluginList) {
				url = new URL(basisUrl + "action=getJobs&jobtype=" + dqo.getStrInt().getStr() + "&status=DONE");
				response = getStringFromUrl(url);
				List<IJob> jobsDone = gson.fromJson(response, new TypeToken<List<JobImpl>>() {
					}.getType());
				dqo.setListDone(jobsDone);
				
				url = new URL(basisUrl + "action=getJobs&jobtype=" + dqo.getStrInt().getStr() + "&status=ERROR");
				response = getStringFromUrl(url);
				List<IJob> jobsError = gson.fromJson(response, new TypeToken<List<JobImpl>>() {
					}.getType());
				dqo.setListError(jobsError);
				
				url = new URL(basisUrl + "action=getJobs&jobtype=" + dqo.getStrInt().getStr() + "&status=PROCESSING");
				response = getStringFromUrl(url);
				List<IJob> jobsProcessing = gson.fromJson(response, new TypeToken<List<JobImpl>>() {
					}.getType());
				dqo.setListProcessing(jobsProcessing);
			}
			
//			System.out.println(itmPluginList);
			lastReadItm = System.currentTimeMillis();
		}
	}
	
	private static String getStringFromUrl(URL url) {
		try (InputStream is = url.openStream(); BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
			String buffer;
			StringBuilder builder = new StringBuilder();
			while ((buffer = br.readLine()) != null) {
				builder.append(buffer);
			}
			return builder.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}