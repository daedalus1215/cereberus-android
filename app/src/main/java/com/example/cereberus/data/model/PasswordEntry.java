package com.example.cereberus.data.model;

public class PasswordEntry {
    private String serviceName;
    private String username;
    private String password;
    private String notes;

    public PasswordEntry(String serviceName, String username, String password, String notes) {
        this.serviceName = serviceName;
        this.username = username;
        this.password = password;
        this.notes = notes;
    }


    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
