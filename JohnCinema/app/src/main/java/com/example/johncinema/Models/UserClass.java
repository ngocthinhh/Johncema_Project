package com.example.johncinema.Models;

public class UserClass {
    private Integer id;
    private String name;
    private String phone;
    private String password;
    private String star;
    private String role;
    private String url_avatar;

    public UserClass (Integer id, String name, String phone, String password, String star, String role, String url_avatar)
    {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.star = star;
        this.role = role;
        this.url_avatar = url_avatar;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUrl_avatar() {
        return url_avatar;
    }

    public void setUrl_avatar(String url_avatar) {
        this.url_avatar = url_avatar;
    }
}
