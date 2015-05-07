package de.intranda.digiverso.steffenshowcase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import de.intranda.digiverso.model.DashQueuesObj;
import de.intranda.digiverso.model.IJob;
import de.intranda.digiverso.model.JobImpl;

public class HierGucken {
    public static void main(String[] args) {
        Gson gson = new Gson();
        try {
            /*
             * Hier plugin-liste
             */
            System.out.println("plugin-liste:");
            URL url = new URL("http://goobitest02.fritz.box/itm/api?action=getPlugins");
            System.out.println(url);
            String response = getStringFromUrl(url);
            List<DashQueuesObj> pluginList = gson.fromJson(response, new TypeToken<List<DashQueuesObj>>(){}.getType());
            System.out.println(pluginList);
            /*
             * Ende plugin-liste
             */
            
            /*
             * Hier job-Liste ohne Jobtyp mit status ERROR. Weitere Möglichkeiten: DONE, PROCESSING
             */
            System.out.println("\njob-Liste ohne Jobtyp mit status ERROR. Weitere Möglichkeiten: DONE, PROCESSING");
            url = new URL("http://goobitest02.fritz.box/itm/api?action=getJobs&status=ERROR");
            System.out.println(url);
            response = getStringFromUrl(url);
            List<IJob> jobList = gson.fromJson(response, new TypeToken<List<JobImpl>>(){}.getType());
            System.out.println(jobList);
            /*
             * Ende job-Liste ohne Jobtyp
             */
            
            /*
             * Hier job-Liste mit Jobtyp
             */
            System.out.println("\njob-Liste mit Jobtyp");
            url = new URL("http://goobitest02.fritz.box/itm/api?action=getJobs&jobtype=STORAGEBALANCER");
            System.out.println(url);
            response = getStringFromUrl(url);
            jobList = gson.fromJson(response, new TypeToken<List<JobImpl>>(){}.getType());
            System.out.println(jobList);
            /*
             * Ende job-Liste mit Jobtyp
             */
            
            /*
             * Hier nur Statistik, ohne gson Umwandlung mit Englisch locale 
             */
            System.out.println("\nnur Statistik, ohne gson Umwandlung mit Englisch locale");
            url = new URL("http://goobitest02.fritz.box/itm/api?action=getThroughput&locale=" + Locale.GERMAN.toLanguageTag());
            System.out.println(url);
            System.out.println(getStringFromUrl(url));
            /*
             * Ende Statistik, ohne gson Umwandlung
             */
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    private static String getStringFromUrl(URL url) {
        try(InputStream is = url.openStream(); BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
            String buffer;
            StringBuilder builder = new StringBuilder();
            while((buffer = br.readLine()) != null) {
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
