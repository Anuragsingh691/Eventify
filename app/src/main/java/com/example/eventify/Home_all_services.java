package com.example.eventify;

public class Home_all_services {
    private String Title;
    private int Thumbnail;

    public void  Home_all_services()
    {

    }

    public Home_all_services(String title, int thumbnail) {
        Title = title;
        Thumbnail = thumbnail;
    }

    public String getTitle() {
        return Title;
    }

    public int getThumbnail() {
        return Thumbnail;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setThumbnail(int thumbnail) {
        Thumbnail = thumbnail;
    }
}
