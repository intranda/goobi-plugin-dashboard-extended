package de.intranda.digiverso.model.rss;

import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class DashboardHelperRss {
	
	List<RssEntry> feeds;
	private Long lastRead = null;
	
	// Bound to our ui:repeat component
	public List<RssEntry> getFeed(String inUrl, int inTime) {
		Long now = System.currentTimeMillis();
		// never read or 15 min ago
		if (lastRead == null || now - lastRead > inTime) {
			feeds = getRss(inUrl);
			lastRead = System.currentTimeMillis();
		}
		return feeds;
	}
	
	private List<RssEntry> getRss(String inFeedUrl) {
    	List<RssEntry> feeds = new ArrayList<>();
        int count = 10; // desired number of feeds to retrieve
        SimpleDateFormat df = new SimpleDateFormat("EEEE MMMM dd, yyyy HH:mm:ss");

        try {
            // Connect
            URLConnection feedUrl = new URL(inFeedUrl).openConnection();
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

                // Please see Javadoc for more of SyndEntry members
                SyndEntry entry = (SyndEntry) feedList.get(i);

                RssEntry rss = new RssEntry();

                // Format based on your requirements
                String title = entry.getTitle();
                rss.setTitle(title);

                rss.setAuthor(entry.getAuthor());
                rss.setPublishedDate(df.format(entry.getPublishedDate()));

                // Do some formatting you may require;
                String description = entry.getDescription().getValue();
                
                rss.setDescription(description);
                //  Update
                feeds.add(rss);
            }
        } catch (Exception e) {

            // Or whatever behaviour your application requires
            feeds = new ArrayList<RssEntry>();
            RssEntry rss = new RssEntry();
            rss.setTitle("Error");
            rss.setAuthor("");
            rss.setDescription(e.getMessage());
            feeds.add(rss);

        }
        return feeds;
    }
}
