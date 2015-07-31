package de.intranda.goobi.plugins;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import org.goobi.beans.Step;
import org.goobi.production.enums.PluginType;
import org.goobi.production.plugin.interfaces.IDashboardPlugin;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import de.intranda.digiverso.model.itm.DashQueuesObj;
import de.intranda.digiverso.model.itm.IJob;
import de.intranda.digiverso.model.itm.JobImpl;
import de.intranda.digiverso.model.rss.DashboardHelperRss;
import de.intranda.digiverso.model.rss.RssEntry;
import de.intranda.digiverso.model.tasks.DashboardHelperTasks;
import de.sub.goobi.config.ConfigPlugins;

@PluginImplementation
public class ExtendedDashboard implements IDashboardPlugin {

	private DashboardHelperRss helper = new DashboardHelperRss();
	private static final String PLUGIN_NAME = "intranda_dashboard_extended";

	private Long lastReadItm = null;
	private List<DashQueuesObj> itmPluginList;
	List<IJob> itmJobListOcrDone;
	List<IJob> itmJobListOcrError;
	List<IJob> itmJobListSdbDone;
	List<IJob> itmJobListSdbError;

	@Override
	public PluginType getType() {
		return PluginType.Dashboard;
	}

	@Override
	public String getTitle() {
		return PLUGIN_NAME;
	}

	@Override
	public String getDescription() {
		return PLUGIN_NAME;
	}

	// Bound to our ui:repeat component
	public List<RssEntry> getFeed() {
		return helper.getFeed(getFeedUrl(), getFeedCachTime());
	}

	public Integer getFeedCachTime() {
		return ConfigPlugins.getPluginConfig(this).getInt("feed-cache-time", 900000);
	}

	public String getFeedUrl() {
		return ConfigPlugins.getPluginConfig(this).getString("feed-url", "http://www.intranda.com/feed/");
	}
	
	public String getFeedTitle() {
		return ConfigPlugins.getPluginConfig(this).getString("feed-title", "Letzte Importe");
	}

	public List<Step> getAssignedSteps() {
		return DashboardHelperTasks.getAssignedSteps();
	}

	public List<DashQueuesObj> getItmPlugins() throws MalformedURLException {
		readItmInfos();
		return itmPluginList;
	}

	public List<IJob> getItmJobsOcrDone() throws MalformedURLException {
		readItmInfos();
		return itmJobListOcrDone;
	}

	public List<IJob> getItmJobsOcrError() throws MalformedURLException {
		readItmInfos();
		return itmJobListOcrError;
	}

	public List<IJob> getItmJobsSdbDone() throws MalformedURLException {
		readItmInfos();
		return itmJobListSdbDone;
	}

	public List<IJob> getItmJobsSdbError() throws MalformedURLException {
		readItmInfos();
		return itmJobListSdbError;
	}

	private void readItmInfos() throws MalformedURLException {
		Long now = System.currentTimeMillis();
		// never read or 3 min ago
		if (lastReadItm == null || now - lastReadItm > ConfigPlugins.getPluginConfig(this).getInt("itm-cache-time", 180000)) {
			String basisUrl = ConfigPlugins.getPluginConfig(this).getString("itm-url", "http://goobitest02.fritz.box/itm/") + "api?";
			Gson gson = new Gson();

			// read all plugins
			URL url = new URL(basisUrl + "action=getPlugins");
			String response = getStringFromUrl(url);
			itmPluginList = gson.fromJson(response, new TypeToken<List<DashQueuesObj>>() {
			}.getType());

			// read ocr jobs: Done
			url = new URL(basisUrl + "action=getJobs&jobtype=OCR&status=DONE");
			response = getStringFromUrl(url);
			itmJobListOcrDone = gson.fromJson(response, new TypeToken<List<JobImpl>>() {
			}.getType());

			// read ocr jobs: Error
			url = new URL(basisUrl + "action=getJobs&jobtype=OCR&status=ERROR");
			response = getStringFromUrl(url);
			itmJobListOcrError = gson.fromJson(response, new TypeToken<List<JobImpl>>() {
			}.getType());

			// read sdb jobs: Done
			url = new URL(basisUrl + "action=getJobs&jobtype=SDB&status=DONE");
			response = getStringFromUrl(url);
			itmJobListSdbDone = gson.fromJson(response, new TypeToken<List<JobImpl>>() {
			}.getType());

			// read sdb jobs: Error
			url = new URL(basisUrl + "action=getJobs&jobtype=SDB&status=ERROR");
			response = getStringFromUrl(url);
			itmJobListSdbError = gson.fromJson(response, new TypeToken<List<JobImpl>>() {
			}.getType());

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

	@Override
	public String getGuiPath() {
		return "plugin_dashboard_extended.xhtml";
	}
}
