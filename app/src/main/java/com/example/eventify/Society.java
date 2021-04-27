package com.example.eventify;

public class Society {
    private String Title,Sdesc,Sdomain,Sphone,image;

    public Society() {
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getSdesc() {
        return Sdesc;
    }

    public void setSdesc(String sdesc) {
        Sdesc = sdesc;
    }

    public String getSdomain() {
        return Sdomain;
    }

    public void setSdomain(String sdomain) {
        Sdomain = sdomain;
    }

    public String getSphone() {
        return Sphone;
    }

    public void setSphone(String sphone) {
        Sphone = sphone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Society(String title, String sdesc, String sdomain, String sphone, String image) {
        Title = title;
        Sdesc = sdesc;
        Sdomain = sdomain;
        Sphone = sphone;
        this.image = image;
    }
}
