package com.example.johncinema.Models;

public class MovieClass {
    private String id;
    private String name;
    private String director;
    private String duration;
    private String debut;
    private String urlPoster;
    private String rating;

    public MovieClass(String id, String name, String director, String duration, String debut, String urlPoster, String rating)
    {
        this.id = id;
        this.name = name;
        this.director = director;
        this.duration = duration;
        this.debut = debut;
        this.urlPoster = urlPoster;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getUrlPoster() {
        return urlPoster;
    }

    public void setUrlPoster(String urlPoster) {
        this.urlPoster = urlPoster;
    }

    public String getDebut() {
        return debut;
    }

    public void setDebut(String debut) {
        this.debut = debut;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }
}
