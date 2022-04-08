package de.intranda.digiverso.model.helper;

/**
 * This file is part of a plugin for the Goobi Application - a Workflow tool for the support of mass digitization.
 * 
 * Visit the websites for more information.
 *          - https://goobi.io
 *          - https://www.intranda.com
 *          - https://github.com/intranda/goobi
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59
 * Temple Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * Linking this library statically or dynamically with other modules is making a combined work based on this library. Thus, the terms and conditions
 * of the GNU General Public License cover the whole combination. As a special exception, the copyright holders of this library give you permission to
 * link this library with independent modules to produce an executable, regardless of the license terms of these independent modules, and to copy and
 * distribute the resulting executable under terms of your choice, provided that you also meet, for each linked independent module, the terms and
 * conditions of the license of that module. An independent module is a module which is not derived from or based on this library. If you modify this
 * library, you may extend this exception to your version of the library, but you are not obliged to do so. If you do not wish to do so, delete this
 * exception statement from your version.
 */
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.configuration.XMLConfiguration;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;

import de.intranda.digiverso.model.rss.RssEntry;
import de.sub.goobi.helper.HttpClientHelper;

public class DashboardHelperRss {

    private XMLConfiguration config;

    public DashboardHelperRss(XMLConfiguration pluginConfig) {
        config = pluginConfig;
    }

    public List<RssEntry> getFeed() {
        List<RssEntry> internalFeeds = new ArrayList<>();
        int count = 5; // desired number of feeds to retrieve
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
                SyndEntry entry = feedList.get(i);
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
            internalFeeds = new ArrayList<>();
            RssEntry rss = new RssEntry();
            rss.setTitle("Error");
            rss.setAuthor("");
            rss.setDescription(e.getMessage());
            internalFeeds.add(rss);
        }
        return internalFeeds;
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
