package com.example.project4200.dto;

import java.time.LocalDateTime;

public class Event {
    private Integer idEvent;
    private String title;
    private String description;
    private String place;
    //private LocalDateTime time;
    private String time;
    private String namePicture;

    public Event(Integer idEvent, String title, String description, String place, String time, String namePicture){
        this.idEvent = idEvent;
        this.title = title;
        this.description = description;
        this.place = place;
        this.time = time;
        this.namePicture = namePicture;
    }
}
