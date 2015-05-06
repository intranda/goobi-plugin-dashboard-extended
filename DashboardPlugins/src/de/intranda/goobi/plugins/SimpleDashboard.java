package de.intranda.goobi.plugins;

import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import org.goobi.beans.Step;
import org.goobi.production.enums.PluginType;
import org.goobi.production.flow.statistics.hibernate.FilterHelper;
import org.goobi.production.plugin.interfaces.IDashboardPlugin;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import de.sub.goobi.helper.Helper;
import de.sub.goobi.persistence.managers.StepManager;

@PluginImplementation
public class SimpleDashboard implements IDashboardPlugin {

	private static final String PLUGIN_NAME = "intranda_dashboard_simple";

    List<RssEntry> feeds;

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
        getrss();
        return feeds;
    }

    private void getrss() {

        String rssFeedUrl = "http://gei-digital.gei.de/viewer/rss/";
        int count = 5; // desired number of feeds to retrieve

        SimpleDateFormat df = new SimpleDateFormat("EEEE MMMM dd, yyyy HH:mm:ss");

        try {
            // Connect
            URLConnection feedUrl = new URL(rssFeedUrl).openConnection();
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

            feeds = new ArrayList<>();

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

    }

    // List item class
    public class RssEntry {

        private String title;
        private String author;
        private String publishedDate;
        private String description;

        public RssEntry() {
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String s) {
            title = s;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String s) {
            author = s;
        }

        public String getPublishedDate() {
            return publishedDate;
        }

        public void setPublishedDate(String s) {
            publishedDate = s;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String s) {
            description = s;
        }
    }

    public List<Step> getAssignedSteps() {
        if (Helper.getCurrentUser() != null) {
            String sql = FilterHelper.criteriaBuilder("", false, false, true, true, false, true);

            return StepManager.getSteps("BearbeitungsBeginn desc", sql, 0, 5);
        }
        return null;
    }

    @Override
    public String getGuiPath() {
        return "SimpleDashboardPlugin.xhtml";
    }
}
