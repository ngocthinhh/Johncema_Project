package com.example.johncinema.Models;

import android.graphics.drawable.Drawable;

public class TicketClass {

    private String title;
    private String idImage;
    private String theaterName;
    private String time;
    private String day;

    public TicketClass(String newTitle, String newIdImage, String newTheaterName, String newTime, String newDay)
    {
        title = newTitle;
        idImage = newIdImage;
        theaterName = newTheaterName;
        time = newTime;
        day = newDay;
    }

    public String getTitle() {
        return title;
    }

    public String getIdImage() {
        return idImage;
    }

    public String getTheaterName() {
        return theaterName;
    }

    public String getTime() {
        return time;
    }

    public String getDay() {
        return day;
    }
}
