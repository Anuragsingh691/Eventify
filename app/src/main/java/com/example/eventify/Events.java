package com.example.eventify;

public class Events {
    public String name,date,desc,image,time,venue,id,society;

    public Events() {
    }

    public Events(String name, String date, String desc, String image, String time, String venue, String id, String society) {
        this.name = name;
        this.date = date;
        this.desc = desc;
        this.image = image;
        this.time = time;
        this.venue = venue;
        this.id = id;
        this.society = society;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSociety() {
        return society;
    }

    public void setSociety(String society) {
        this.society = society;
    }
}
