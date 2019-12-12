package com.example.tutorme;

public class User {
    public String name;
    public String email;
    public String user_id;
    public String major;
    public String year;
    boolean isAvailable;

    public User() {
        // DVC
    }

    // EVC

    public User(String name, String email, String user_id, String major, String year, boolean isAvailable) {
        this.name = name;
        this.email = email;
        this.user_id = user_id;
        this.major = major;
        this.year = year;
        this.isAvailable = isAvailable;
    }

    /**
     Getter used to get the current value in name
     * @return current value of name
     */
    public String getName() {
        return name;
    }

    /**
     Setter used to set the value of name
     * @param name Desired value of name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     Getter used to get the current value in email
     * @return current value of email
     */
    public String getEmail() {
        return email;
    }

    /**
     Setter used to set the value of email
     * @param email  Desired value of email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     Getter used to get the current value in user_id
     * @return current value of user_id
     */
    public String getUser_id() {
        return user_id;
    }

    /**
     Setter used to set the value of user_id
     * @param user_id  Desired value of user_id
     */
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    /**
     Getter used to get the current value in year
     * @return current value of year
     */
    public String getYear() {
        return year;
    }

    /**
     Setter used to set the value of year
     * @param year  Desired value of year
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     Getter used to get the current value in major
     * @return current value of major
     */
    public String getMajor() {
        return major;
    }

    /**
     Setter used to set the value of major
     * @param major  Desired value of major
     */
    public void setMajor(String major) {
        this.major = major;
    }

    /**
     Getter used to get the current value in isAvailable
     * @return current value of isAvailable
     */
    public boolean isAvailable() {
        return isAvailable;
    }

    /**
     Setter used to set the value of available
     * @param available  Desired value of available
     */
    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
