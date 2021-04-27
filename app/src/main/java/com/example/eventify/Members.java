package com.example.eventify;

public class Members {
    private String Mname,Mpost,Mtemp,Mstatus,Mphone,Memail;

    public Members() {
    }

    public String getMname() {
        return Mname;
    }

    public void setMname(String mname) {
        Mname = mname;
    }

    public String getMpost() {
        return Mpost;
    }

    public void setMpost(String mpost) {
        Mpost = mpost;
    }

    public String getMtemp() {
        return Mtemp;
    }

    public void setMtemp(String mtemp) {
        Mtemp = mtemp;
    }

    public String getMstatus() {
        return Mstatus;
    }

    public void setMstatus(String mstatus) {
        Mstatus = mstatus;
    }

    public String getMphone() {
        return Mphone;
    }

    public void setMphone(String mphone) {
        Mphone = mphone;
    }

    public String getMemail() {
        return Memail;
    }

    public void setMemail(String memail) {
        Memail = memail;
    }

    public Members(String mname, String mpost, String mtemp, String mstatus, String mphone, String memail) {
        Mname = mname;
        Mpost = mpost;
        Mtemp = mtemp;
        Mstatus = mstatus;
        Mphone = mphone;
        Memail = memail;
    }
}
