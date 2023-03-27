package com.example.project4200;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AllDAO{
    //----- PICTURE -----
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertPicture(Picture picture);

    @Query("SELECT * FROM table_picture")
    List<Picture> getAllPictures();

    @Delete
    int deletePicture(Picture picture);

    @Update
    int updatePicture(Picture picture);

    @Query("SELECT * FROM table_picture WHERE table_picture.id=:id")
    Picture getPictureById(int id);

    @Query("SELECT * FROM table_picture WHERE table_picture.name=:name")
    Picture getPictureByName(String name);

    //------ EVENT ------
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertEvent(Event event);

    @Query("select * from table_event")
    List<Event> getAllEvents();

    @Delete
    int deleteEvent(Event event);

    @Update
    int updateEvent(Event event);
    
    @Query("UPDATE table_event SET title=:title, description=:description, place=:place, date=:date, time=:time, picture_name=:picture_name WHERE table_event.id=:id")
    int updateEventById(String title, String description, String place, String date, String time, String picture_name, int id);

    @Query("SELECT * FROM table_event WHERE table_event.id=:id")
    Event getEventById(int id);

}
