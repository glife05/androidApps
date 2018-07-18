package com.savannapeguins.droid.placefinder.Model;

public class Listings {
     private String biz_name,new_spinner,biz_street,biz_phone,biz_contact,biz_build;

    public Listings() {
        //no args
    }

    public Listings(String biz_name, String new_spinner,String biz_street, String biz_phone, String biz_contact, String biz_build) {
        this.biz_name = biz_name;
        this.biz_street = biz_street;
        this.biz_phone = biz_phone;
        this.biz_contact = biz_contact;
        this.biz_build = biz_build;
        this.new_spinner=new_spinner;
    }

    public String getBiz_name() {
        return biz_name;
    }

    public void setBiz_name(String biz_name) {
        this.biz_name = biz_name;
    }

    public String getBiz_street() {
        return biz_street;
    }

    public void setBiz_street(String biz_street) {
        this.biz_street = biz_street;
    }

    public String getBiz_phone() {
        return biz_phone;
    }

    public void setBiz_phone(String biz_phone) {
        this.biz_phone = biz_phone;
    }

    public String getBiz_contact() {
        return biz_contact;
    }

    public void setBiz_contact(String biz_contact) {
        this.biz_contact = biz_contact;
    }

    public String getBiz_build() {
        return biz_build;
    }

    public void setBiz_build(String biz_build) {
        this.biz_build = biz_build;
    }

    public String getNew_spinner() {
        return new_spinner;
    }

    public void setNew_spinner(String new_spinner) {
        this.new_spinner = new_spinner;
    }
}
