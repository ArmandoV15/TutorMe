package com.example.tutorme;

public class User {
    public String name;
    public String email;
    public String user_id;
    public String major;
    public String year;

    public User() {
        // DVC
    }

    // EVC


    public User(String name, String email, String user_id, String major, String year) {
        this.name = name;
        this.email = email;
        this.user_id = user_id;
        this.major = major;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }
}
