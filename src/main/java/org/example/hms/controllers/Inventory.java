package org.example.hms.controllers;

import org.example.hms.classes.Doctor;
import org.example.hms.classes.User;

public class Inventory {
    private User user;
    private Doctor doctor;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
