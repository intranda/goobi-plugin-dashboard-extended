package de.intranda.digiverso.model.itm;

import java.io.Serializable;

public class StringIntObj implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 2768677410039288884L;
    private String str;
    private int number;

    public StringIntObj(String str, int number) {
        super();
        this.str = str;
        this.number = number;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "StringIntObj [str=" + str + ", number=" + number + "]";
    }
    
    
}
