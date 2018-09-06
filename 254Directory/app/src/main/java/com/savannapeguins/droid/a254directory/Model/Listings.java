package com.savannapeguins.droid.a254directory.Model;

public class Listings {
    private String varEmail,varName,varPhone,varContactPerson,varLocation,varUID;

    public Listings() {
    }

    public Listings(String varEmail, String varName, String varPhone, String varContactPerson, String varLocation, String varUID) {
        this.varEmail = varEmail;
        this.varName = varName;
        this.varPhone = varPhone;
        this.varContactPerson = varContactPerson;
        this.varLocation = varLocation;
        this.varUID = varUID;
    }

    public String getVarEmail() {
        return varEmail;
    }

    public void setVarEmail(String varEmail) {
        this.varEmail = varEmail;
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    public String getVarPhone() {
        return varPhone;
    }

    public void setVarPhone(String varPhone) {
        this.varPhone = varPhone;
    }

    public String getVarContactPerson() {
        return varContactPerson;
    }

    public void setVarContactPerson(String varContactPerson) {
        this.varContactPerson = varContactPerson;
    }

    public String getVarLocation() {
        return varLocation;
    }

    public void setVarLocation(String varLocation) {
        this.varLocation = varLocation;
    }

    public String getVarUID() {
        return varUID;
    }

    public void setVarUID(String varUID) {
        this.varUID = varUID;
    }
}
