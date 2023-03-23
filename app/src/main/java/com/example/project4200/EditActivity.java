package com.example.project4200;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;
import androidx.room.Room;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EditActivity extends AppCompatActivity {

    EditText title, des, date, time, place;
    Spinner icons;
    Button save, back, select_date, select_time;
    int mYear, mMonth, mDay, mHour, mMinute;
    DataBase db;
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Handler handler = HandlerCompat.createAsync(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent = new Intent(EditActivity.this, MainActivity.class);

        title = findViewById(R.id.edit_title);
        des = findViewById(R.id.edit_des);
        icons = findViewById(R.id.spinner);
        save = findViewById(R.id.btn_save);
        back = findViewById(R.id.btn_back);
        date = findViewById(R.id.edit_date);
        time = findViewById((R.id.edit_time));
        place = findViewById(R.id.edit_place);
        select_date = findViewById(R.id.btn_date);
        select_time = findViewById(R.id.btn_time);

//        DBHelper db = new DBHelper(getApplicationContext());
//        db.getReadableDatabase();

        db = Room.databaseBuilder(getApplicationContext(), DataBase.class,
                "countdowntimer.db").allowMainThreadQueries().build();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        //create a list of items for the spinner.
        ArrayList<String> items = new ArrayList<String>();
        items.add("1");
        items.add("22");
        items.add("333");
//        Create an adapter to describe how the items are displayed, adapters are used in several places in android.
//        There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
//        Set the spinners adapter to the previously created one.
        icons.setAdapter(adapter);

//        Get from database: title, des, list of icon

        Intent get = getIntent();
        int check = get.getIntExtra("state", 0);

        select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                    }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        select_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(EditActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        time.setText(hourOfDay + ":" + minute);
                    }
                    }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check == 0) {  // add new to db

                    String t = title.getText().toString();
                    String d = des.getText().toString();
                    String da = date.getText().toString();
                    String ti = time.getText().toString();
                    String p = place.getText().toString();

                    Picture picture = new Picture();
                    picture.setName("picture_name");
                    Event event = new Event();
                    event.setTitle(t);
                    event.setDescription(d);
                    event.setPlace(p);
                    event.setTime(ti);
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            long l1 = db.allDAO().insertPicture(picture);
                            Picture picture = db.allDAO().getPictureByName("picture_name");
                            event.setPicture_id(picture.getId());
                            long l2 = db.allDAO().insertEvent(event);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if(l2 > 0 ){
                                        Toast.makeText(EditActivity.this, "The value inserted!", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(EditActivity.this, "The value insertion failed!", Toast.LENGTH_SHORT).show();
                                    }
                                    startActivity(intent);
                                }
                            });
                        }
                    });

                }
                else if (check == 1) {  // save to db

                }
                else {
                    Toast.makeText(EditActivity.this, "An error has occurred -_- Please try again.", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            }
        });
    }
}