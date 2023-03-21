package com.example.project4200;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Picture.class, Event.class}, version = 1)
public abstract class Database extends RoomDatabase {
    public abstract AllDAO allDAO();

}
