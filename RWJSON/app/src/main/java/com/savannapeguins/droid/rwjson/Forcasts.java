package com.savannapeguins.droid.rwjson;

public class Forcasts {
    String varDate,varDay;
    int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Forcasts() {
    }

    public Forcasts(String varDate, String varDay,int count) {
        this.varDate = varDate;
        this.varDay = varDay;
        this.count=count;
    }

    public String getVarDate() {
        return varDate;
    }

    public void setVarDate(String varDate) {
        this.varDate = varDate;
    }

    public String getVarDay() {
        return varDay;
    }

    public void setVarDay(String varDay) {
        this.varDay = varDay;
    }
}
