package de.intranda.digiverso.model;

public class DashQueuesObj {

    private StringIntObj strInt;
    private String running;
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
    
    
}
