package de.intranda.digiverso.model.itm;

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
import java.util.List;

public class DashQueuesObj {

    private StringIntObj strInt;
    private String running;

    private List<IJob> listDone;
    private List<IJob> listError;
    private List<IJob> listProcessing;

    public DashQueuesObj(StringIntObj strInt, String running) {
        super();
        this.strInt = strInt;
        this.running = running;
    }

    public StringIntObj getStrInt() {
        return strInt;
    }

    public void setStrInt(StringIntObj strInt) {
        this.strInt = strInt;
    }

    public String getRunning() {
        return running;
    }

    public void setRunning(String running) {
        this.running = running;
    }

    @Override
    public String toString() {
        return "DashQueuesObj [strInt=" + strInt + ", running=" + running + "]";
    }

    public void setListDone(List<IJob> listDone) {
        this.listDone = listDone;
    }

    public void setListError(List<IJob> listError) {
        this.listError = listError;
    }

    public void setListProcessing(List<IJob> listProcessing) {
        this.listProcessing = listProcessing;
    }

    public List<IJob> getListDone() {
        return listDone;
    }

    public List<IJob> getListError() {
        return listError;
    }

    public List<IJob> getListProcessing() {
        return listProcessing;
    }

    public String getName() {
        return strInt.getStr();
    }

    public int getNumber() {
        return strInt.getNumber();
    }

}
