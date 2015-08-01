package de.intranda.digiverso.model.rss;

import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.XMLConfiguration;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class DashboardHelperRss {

	private XMLConfiguration config;
	List<RssEntry> feeds;
	private Long lastRead = null;

	public DashboardHelperRss(XMLConfiguration pluginConfig) {
		config = pluginConfig;
	}

	private List<RssEntry> getRss() {
		List<RssEntry> feeds = new ArrayList<>();
		int count = 10; // desired number of feeds to retrieve
		SimpleDateFormat df = new SimpleDateFormat("EEEE MMMM dd, yyyy HH:mm:ss");

		try {
			// Connect
			URLConnection feedUrl = new URL(getFeedUrl()).openConnection();
			SyndFeedInput input = new SyndFeedInput();
			// Build the feed list
			SyndFeed feed = input.build(new XmlReader(feedUrl));

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
				rss.setPublishedDate(df.format(entry.getPublishedDate()));
				String description = entry.getDescription().getValue();
				rss.setDescription(description);
				feeds.add(rss);
			}
		} catch (Exception e) {
			feeds = new ArrayList<RssEntry>();
			RssEntry rss = new RssEntry();
			rss.setTitle("Error");
			rss.setAuthor("");
			rss.setDescription(e.getMessage());
			feeds.add(rss);
		}
		return feeds;
	}

	public List<RssEntry> getFeed() {
		Long now = System.currentTimeMillis();
		// never read or 15 min ago
		if (isShowRss() && (lastRead == null || now - lastRead > config.getInt("rss-cache-time", 900000))) {
			feeds = getRss();
			lastRead = System.currentTimeMillis();
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
