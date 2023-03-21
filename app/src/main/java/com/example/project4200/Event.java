package com.example.project4200;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_event",
        foreignKeys = {@ForeignKey(entity = Picture.class,
                parentColumns = "id",
                childColumns = "picture_id",
                onDelete = ForeignKey.CASCADE)})
public class Event {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "place")
    private String place;
    @ColumnInfo(name = "time")
    private String time;
    @ColumnInfo(name = "picture_id")
    private int picture_id;



    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPlace() {
        return place;
    }

    public String getTime() {
        return time;
    }

    public int getPicture_id() {
        return picture_id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPicture_id(int picture_id) {
        this.picture_id = picture_id;
    }
}
