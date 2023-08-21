package com.example.johncinema.Models;

public class PosterClass {
    private String title;
    private String urlImage;
    private String id_movie;

    public PosterClass(String title, String urlImage, String id_movie)
    {
        this.title = title;
        this.urlImage = urlImage;
        this.setId_movie(id_movie);
    }

    public String getTitle() {
        return title;
    }
    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(int idImage) {
        this.urlImage = urlImage;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId_movie() {
        return id_movie;
    }

    public void setId_movie(String id_movie) {
        this.id_movie = id_movie;
    }
}
