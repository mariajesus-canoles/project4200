package com.example.project4200;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.project4200.dto.Event;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "countdown_timer.db";
    public static final String TABLE1_NAME = "event_table";
    public static final String TABLE1_COL1 = "id";
    public static final String TABLE1_COL2 = "title";
    public static final String TABLE1_COL3 = "description";
    public static final String TABLE1_COL4 = "place";
    public static final String TABLE1_COL5 = "time";
    public static final String TABLE1_COL6 = "id_picture";

    public static final String TABLE2_NAME = "picture_table";
    public static final String TABLE2_COL1 = "id";
    public static final String TABLE2_COL2 = "name";

    public DBHelper(@Nullable Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE "+TABLE1_NAME+" ("+TABLE1_COL1+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ""+TABLE1_COL2+" TEXT, "+TABLE1_COL3+" TEXT, "+TABLE1_COL4+" TEXT, "+TABLE1_COL5+" TEXT, "+TABLE1_COL6+" INTEGER)");
        db.execSQL("CREATE TABLE "+TABLE2_NAME+" ("+TABLE2_COL1+" INTEGER PRIMARY KEY AUTOINCREMENT, "+TABLE2_COL2+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE1_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE2_NAME);
        onCreate(db);
    }

    // ---------   CRUD   ---------
    public long createDataTable1(String titleInput, String descriptionInput, String placeInput, String timeInput, Long id_picture){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE1_COL2, titleInput);
        contentValues.put(TABLE1_COL3, descriptionInput);
        contentValues.put(TABLE1_COL4, placeInput);
        contentValues.put(TABLE1_COL5, timeInput);
        contentValues.put(TABLE1_COL6, id_picture);
        return db.insert(TABLE1_NAME, null, contentValues);
    }

    public long createDataTable2(String nameInput){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE2_COL2, nameInput);
        return db.insert(TABLE2_NAME, null, contentValues);
    }

    public Cursor readDataTable1(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE1_NAME, null);
        return cursor;
    }

    public Cursor readDataTable2(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE2_NAME, null);
        return cursor;
    }

    public ArrayList<Event> readEvents(Integer id){
        Cursor cursorTable1 = readDataTable1();
        Cursor cursorTable2 = readDataTable2();
        ArrayList<Event> events = new ArrayList<Event>();
        while (cursorTable1.moveToNext()){
            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            //LocalDateTime dateTime = LocalDateTime.parse(cursorTable1.getString(4), formatter);
            //-----ERROR: call requires api level 26 (current min is 21)-----
            String namePicture = "";
            while (cursorTable2.moveToNext()){
                if (cursorTable1.getString(6) == cursorTable2.getString(1)){
                    namePicture = cursorTable2.getString(1);
                }
            }
            Event event = new Event(cursorTable1.getInt(1),
                    cursorTable1.getString(2),
                    cursorTable1.getString(3),
                    cursorTable1.getString(4),
                    cursorTable1.getString(5),
                    namePicture);
            events.add(event);
        }
        return events;
    }


    public void updateDataTable1(Integer id, String titleIput, String descriptionInput, String placeInput, String timeInput, Long id_picture){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE "+TABLE1_NAME+" SET "
                +TABLE1_COL2+" = "+titleIput+", "
                +TABLE1_COL3+" = "+descriptionInput+", "
                +TABLE1_COL4+" = "+placeInput+", "
                +TABLE1_COL5+" = "+timeInput+", "
                +TABLE1_COL6+" = "+id_picture+" WHERE "+TABLE1_COL1+" = "+id);
    }

    public void updateDataTable2(Integer id, String nameInput){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE "+TABLE2_NAME+" SET "+TABLE1_COL2+" = "+nameInput+" WHERE "+TABLE2_COL1+" = "+id);
    }

    public void deleteDataTable1(Integer id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE1_NAME+" WHERE "+TABLE1_COL1+"="+id);
    }

    public void deleteDataTable2(Integer id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE2_NAME+" WHERE "+TABLE1_COL1+"="+id);
    }


}
