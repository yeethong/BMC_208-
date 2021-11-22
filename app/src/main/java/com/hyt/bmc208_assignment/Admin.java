package com.hyt.bmc208_assignment;

public class Admin {

    static String COLLECTION_NAME = "Admins";

    private String admin_ID;
    private String admin_username;
    private String admin_password;
    private String admin_email;
    private String admin_fullName;
    private String admin_staffID;

    public String getAdmin_ID() {
        return admin_ID;
    }

    public void setAdmin_ID(String admin_ID) {
        this.admin_ID = admin_ID;
    }

    public String getAdmin_username() {
        return admin_username;
    }

    public void setAdmin_username(String admin_username) {
        this.admin_username = admin_username;
    }

    public String getAdmin_password() {
        return admin_password;
    }

    public void setAdmin_password(String admin_password) {
        this.admin_password = admin_password;
    }

    public String getAdmin_email() {
        return admin_email;
    }

    public void setAdmin_email(String admin_email) {
        this.admin_email = admin_email;
    }

    public String getAdmin_fullName() {
        return admin_fullName;
    }

    public void setAdmin_fullName(String admin_fullName) {
        this.admin_fullName = admin_fullName;
    }

    public String getAdmin_staffID() {
        return admin_staffID;
    }

    public void setAdmin_staffID(String admin_staffID) {
        this.admin_staffID = admin_staffID;
    }
}
