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
import java.net.MalformedURLException;
import java.util.List;

import org.apache.commons.configuration.XMLConfiguration;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import de.intranda.digiverso.model.itm.DashQueuesObj;
import de.intranda.digiverso.model.itm.IJob;
import de.intranda.digiverso.model.itm.JobImpl;
import de.sub.goobi.helper.HttpClientHelper;

public class DashboardHelperItm {

    private XMLConfiguration config;

    public DashboardHelperItm(XMLConfiguration xmlConfiguration) {
        config = xmlConfiguration;
    }

    public List<DashQueuesObj> getItmPlugins() throws MalformedURLException {
        String basisUrl = config.getString("itm-url", "http://goobitest02.fritz.box/itm/") + "api?";
        Gson gson = new Gson();

        // read all plugin types of the itm
        String response = HttpClientHelper.getStringFromUrl(basisUrl + "action=getPlugins");
        List<DashQueuesObj> itmPluginList = gson.fromJson(response, new TypeToken<List<DashQueuesObj>>() {
        }.getType());

        if (itmPluginList != null) {
            // read all job queues for this plugin type
            for (DashQueuesObj dqo : itmPluginList) {
                response = HttpClientHelper.getStringFromUrl(basisUrl + "action=getJobs&jobtype=" + dqo.getStrInt().getStr() + "&status=DONE");
                List<IJob> jobsDone = gson.fromJson(response, new TypeToken<List<JobImpl>>() {
                }.getType());
                dqo.setListDone(jobsDone);

                response = HttpClientHelper.getStringFromUrl(basisUrl + "action=getJobs&jobtype=" + dqo.getStrInt().getStr() + "&status=ERROR");
                List<IJob> jobsError = gson.fromJson(response, new TypeToken<List<JobImpl>>() {
                }.getType());
                dqo.setListError(jobsError);

                response = HttpClientHelper.getStringFromUrl(basisUrl + "action=getJobs&jobtype=" + dqo.getStrInt().getStr() + "&status=PROCESSING");
                List<IJob> jobsProcessing = gson.fromJson(response, new TypeToken<List<JobImpl>>() {
                }.getType());
                dqo.setListProcessing(jobsProcessing);
            }
        }
        return itmPluginList;
    }

    public boolean isShowItm() {
        return config.getBoolean("itm-show", true);
    }

}
