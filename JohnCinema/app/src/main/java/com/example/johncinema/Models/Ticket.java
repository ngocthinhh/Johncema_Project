package com.example.johncinema.Models;

public class Ticket {
    private String id_ticket;
    private String id_user_ticket;
    private String id_chair_ticket;
    private String time_buy;
    private String title;
    private String idImage;
    private String theaterName;
    private String time;
    private String day;
    private String money;
    private String rating;

    public Ticket(String id_ticket, String id_user_ticket, String id_chair_ticket, String time_buy,
                  String title, String idImage, String theaterName, String time, String day, String money, String rating)
    {
        this.id_ticket = id_ticket;
        this.id_user_ticket = id_user_ticket;
        this.id_chair_ticket = id_chair_ticket;
        this.time_buy = time_buy;
        this.title = title;
        this.idImage = idImage;
        this.theaterName = theaterName;
        this.time = time;
        this.day = day;
        this.money = money;
        this.setRating(rating);
    }

    public String getId_ticket() {
        return id_ticket;
    }

    public void setId_ticket(String id_ticket) {
        this.id_ticket = id_ticket;
    }

    public String getId_user_ticket() {
        return id_user_ticket;
    }

    public void setId_user_ticket(String id_user_ticket) {
        this.id_user_ticket = id_user_ticket;
    }

    public String getId_chair_ticket() {
        return id_chair_ticket;
    }

    public void setId_chair_ticket(String id_chair_ticket) {
        this.id_chair_ticket = id_chair_ticket;
    }

    public String getTime_buy() {
        return time_buy;
    }

    public void setTime_buy(String time_buy) {
        this.time_buy = time_buy;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIdImage() {
        return idImage;
    }

    public void setIdImage(String idImage) {
        this.idImage = idImage;
    }

    public String getTheaterName() {
        return theaterName;
    }

    public void setTheaterName(String theaterName) {
        this.theaterName = theaterName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
