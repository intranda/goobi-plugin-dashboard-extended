package de.intranda.digiverso.model.helper;

import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.configuration.XMLConfiguration;
import org.primefaces.context.RequestContext;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import de.intranda.digiverso.model.rss.RssEntry;
import de.sub.goobi.helper.HttpClientHelper;

public class DashboardHelperRss {

	private XMLConfiguration config;
	List<RssEntry> feeds;
	private Long lastRead = null;

	public DashboardHelperRss(XMLConfiguration pluginConfig) {
		config = pluginConfig;
	}

	private void readRss() {
		Thread rssThread = new Thread(new Runnable() {
		     public void run() {
		    	 List<RssEntry> internalFeeds = new ArrayList<>();
		 		int count = 10; // desired number of feeds to retrieve
		 		SimpleDateFormat df = new SimpleDateFormat("EEEE MMMM dd, yyyy HH:mm:ss");

		 		try {
		 			SyndFeedInput input = new SyndFeedInput();
		 			SyndFeed feed = input.build(new StringReader(HttpClientHelper.getStringFromUrl(getFeedUrl())));

		 			@SuppressWarnings("unchecked")
		 			List<SyndEntry> feedList = feed.getEntries();
		 			int feedSize = feedList.size();

		 			// Save only count requested
		 			if (feedSize > count) {
		 				feedSize = count;
		 			}

		 			for (int i = 0; i < feedSize; i++) {
		 				SyndEntry entry = (SyndEntry) feedList.get(i);
		 				RssEntry rss = new RssEntry();
		 				String title = entry.getTitle();
		 				rss.setTitle(title);
		 				rss.setAuthor(entry.getAuthor());
		 				try {
		 					Date d = entry.getPublishedDate();
		 					rss.setPublishedDate(df.format(d));
		 				} catch (Exception e) {
		 				}
		 				rss.setLink(entry.getLink());
		 				String description = entry.getDescription().getValue();
		 				rss.setDescription(description);
		 				internalFeeds.add(rss);
		 			}
		 		} catch (Exception e) {
		 			internalFeeds = new ArrayList<RssEntry>();
		 			RssEntry rss = new RssEntry();
		 			rss.setTitle("Error");
		 			rss.setAuthor("");
		 			rss.setDescription(e.getMessage());
		 			internalFeeds.add(rss);
		 		}
		 		feeds = internalFeeds;
		     }
		});  
		rssThread.start();
	}

	public List<RssEntry> getFeed() {
		Long now = System.currentTimeMillis();
		// never read or 15 min ago
		if (isShowRss() && (lastRead == null || now - lastRead > config.getInt("rss-cache-time", 900000))) {
			readRss();
			lastRead = System.currentTimeMillis();
		}
		if (feeds!= null) {
			RequestContext reqCtx = RequestContext.getCurrentInstance();
			reqCtx.execute("poll.stop();");
		}
		return feeds;
	}

	public String getFeedUrl() {
		return config.getString("rss-url", "http://www.intranda.com/feed/");
	}

	public String getFeedTitle() {
		return config.getString("rss-title", "Letzte Importe");
	}

	public boolean isShowRss() {
		return config.getBoolean("rss-show", true);
	}
}
