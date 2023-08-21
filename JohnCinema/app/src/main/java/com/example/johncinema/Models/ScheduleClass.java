package com.example.johncinema.Models;

import android.content.Context;

public class ScheduleClass {
    private String id;
    private String id_movie;
    private String start_hour;
    private String theater;
    private String date;

    public ScheduleClass(String id, String id_movie, String start_hour, String theater, String date)
    {
        this.id = id;
        this.id_movie = id_movie;
        this.start_hour = start_hour;
        this.theater = theater;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_movie() {
        return id_movie;
    }

    public void setId_movie(String id_movie) {
        this.id_movie = id_movie;
    }

    public String getStart_hour() {
        return start_hour;
    }

    public void setStart_hour(String start_hour) {
        this.start_hour = start_hour;
    }

    public String getTheater() {
        return theater;
    }

    public void setTheater(String theater) {
        this.theater = theater;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
