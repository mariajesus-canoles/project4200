package com.example.project4200;

import java.time.LocalDateTime;

public class Appointment {
    int id;
    String title;
    String place;
    LocalDateTime time;
    int picture_id;

    public Appointment(int id, String title, String place, LocalDateTime time) {
        this.id = id;
        this.title = title;
        this.place = place;
        this.time = time;
        this.picture_id = picture_id;
    }

    public int getId() {
        return id;
    }

    public String getPlace() {
        return place;
    }

    public int getPicture_id() {
        return picture_id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getTime() {
        return time;
    }
}
