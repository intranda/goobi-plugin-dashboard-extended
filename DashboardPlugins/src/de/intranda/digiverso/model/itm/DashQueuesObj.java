package de.intranda.digiverso.model.itm;

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
    
    public String getName(){
    	return strInt.getStr();
    }
    
    public int getNumber(){
    	return strInt.getNumber();
    }
    
}
