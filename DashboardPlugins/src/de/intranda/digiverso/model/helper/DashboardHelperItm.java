package de.intranda.digiverso.model.helper;

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

import de.intranda.digiverso.model.itm.DashQueuesObj;
import de.intranda.digiverso.model.itm.IJob;
import de.intranda.digiverso.model.itm.JobImpl;

public class DashboardHelperItm {

	private XMLConfiguration config;
	private Long lastRead = null;
	private List<DashQueuesObj> itmPluginList;
	private DashQueuesObj currentItmPlugin = null;

	public DashboardHelperItm(XMLConfiguration xmlConfiguration) {
		config = xmlConfiguration;
	}

	public List<DashQueuesObj> getItmPlugins() throws MalformedURLException {
		readItmInfos();
		return itmPluginList;
	}

	private void readItmInfos() throws MalformedURLException {
		Long now = System.currentTimeMillis();

		// never read or 3 min ago
		if (isShowItm() && (lastRead == null || now - lastRead > config.getInt("itm-cache-time", 180000))) {
			String basisUrl = config.getString("itm-url", "http://goobitest02.fritz.box/itm/") + "api?";
			Gson gson = new Gson();

			// read all plugin types of the itm
			URL url = new URL(basisUrl + "action=getPlugins");
			String response = getStringFromUrl(url);
			itmPluginList = gson.fromJson(response, new TypeToken<List<DashQueuesObj>>() {
			}.getType());

			if (itmPluginList != null) {
				// read all job queues for this plugin type
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
			}

			// System.out.println(itmPluginList);
			lastRead = System.currentTimeMillis();
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

	public DashQueuesObj getCurrentItmPlugin() {
		return currentItmPlugin;
	}

	public void setCurrentItmPlugin(DashQueuesObj currentItmPlugin) {
		this.currentItmPlugin = currentItmPlugin;
	}

	public boolean isShowItm() {
		return config.getBoolean("itm-show", true);
	}

	public Long getLastRead() {
		return lastRead;
	}

	public void setLastRead(Long lastReadItm) {
		this.lastRead = lastReadItm;
	}
}
