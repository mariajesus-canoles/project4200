package com.example.project4200;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;
import androidx.room.Room;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EditActivity extends AppCompatActivity {

    EditText title, des, place;
    TextView date, time;
    Spinner icons;
    Button save, select_date, select_time;
    int mYear, mMonth, mDay, mHour, mMinute;
    DataBase db;
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Handler handler = HandlerCompat.createAsync(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent_main = new Intent(EditActivity.this, MainActivity.class);

        title = findViewById(R.id.edit_title);
        des = findViewById(R.id.edit_des);
        icons = findViewById(R.id.spinner);
        save = findViewById(R.id.btn_save);
        date = findViewById(R.id.date);
        time = findViewById((R.id.time));
        place = findViewById(R.id.edit_place);
        select_date = findViewById(R.id.btn_date);
        select_time = findViewById(R.id.btn_time);

        Calendar globalCal = Calendar.getInstance();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);

        // Customize the back button
        actionBar.setHomeAsUpIndicator(R.drawable.back_arrow);
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        db = Room.databaseBuilder(getApplicationContext(), DataBase.class, "countdowntimer.db").allowMainThreadQueries().build();

        final List<String> img_icons = Arrays.asList("calendar","alarm","beach","birthday",
                "bow_cupid","camping","christmas_tree","confetti","easter_egg",
                "firework_explosion","gingerbread_man","jackolantern","music_festival");
        SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), img_icons);
        adapter.setDropDownViewResource(R.layout.dropdown);
        //  Set the spinners adapter to the previously created one.
        icons.setAdapter(adapter);

        //  Get from database: title, des, list of icon
        Intent get = getIntent();
        int check = get.getIntExtra("state", 0);
        int id = get.getIntExtra("id", 0);

        //  Get event info if is editing event
        if (check == 1) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    Event event = db.allDAO().getEventById(id);
                    title.setText(event.getTitle());
                    des.setText(event.getDescription());

                    place.setText(event.getPlace());
                    icons.setSelection(img_icons.indexOf(event.getPicture_name()));

                    Calendar countdownDate = Calendar.getInstance();
                    if (event.getDate() != "empty") {

                        String[] date_elements = event.getDate().split("-");

                        date.setText(event.getDate());

                        if (date_elements.length == 3) {
                            mYear = Integer.parseInt(date_elements[2]);
                            mMonth = Integer.parseInt(date_elements[1]);
                            mDay = Integer.parseInt(date_elements[0]);

                            countdownDate.set(Calendar.YEAR, Integer.parseInt(date_elements[2]));
                            countdownDate.set(Calendar.MONTH, Integer.parseInt(date_elements[1]) - 1);
                            countdownDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date_elements[0]));

                            globalCal.set(Calendar.YEAR, Integer.parseInt(date_elements[2]));
                            globalCal.set(Calendar.MONTH, Integer.parseInt(date_elements[1]) - 1);
                            globalCal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date_elements[0]));

                            SimpleDateFormat dateFormat3 = new SimpleDateFormat("EEE, MMM dd, yyyy");

                            String formattedDate = dateFormat3.format(countdownDate.getTime());

                            date.setText(formattedDate);

                        }
                    }
                    if (event.getTime() != "empty") {

                        String[] time_elements = event.getTime().split(":");
                        time.setText(event.getTime());

                        if (time_elements.length == 2) {
                            mHour = Integer.parseInt(time_elements[0]);
                            mMinute = Integer.parseInt(time_elements[1]);

                            countdownDate.set(Calendar.HOUR, Integer.parseInt(time_elements[0]));
                            countdownDate.set(Calendar.MINUTE, Integer.parseInt(time_elements[1]));

                            globalCal.set(Calendar.HOUR, Integer.parseInt(time_elements[0]));
                            globalCal.set(Calendar.MINUTE, Integer.parseInt(time_elements[1]));

                            SimpleDateFormat dateFormat4 = new SimpleDateFormat("hh:mm a");
                            String formattedDate2 = dateFormat4.format(countdownDate.getTime());
                            time.setText(formattedDate2);
                        }
                    }
                }
            });
        }

        //  Select date
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
                        Calendar cal = Calendar.getInstance(); //-------------------------------------------------------------------------------------
                        cal.set(Calendar.YEAR, year);
                        cal.set(Calendar.MONTH, monthOfYear);
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        globalCal.set(Calendar.YEAR, year);
                        globalCal.set(Calendar.MONTH, monthOfYear);
                        globalCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        SimpleDateFormat dateFormat2 = new SimpleDateFormat("EEE, MMMM dd, yyyy");

                        String formattedDate2 = dateFormat2.format(cal.getTime());
                        date.setText(formattedDate2); //-------------bis hier------------------------

                        mMonth = monthOfYear+1;
                        mDay = dayOfMonth;
                        mYear = year;
                    }
                    }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        //  Select time
        select_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                //  Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(EditActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    String h, m, ap;
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar cal = Calendar.getInstance(); //------------------------
                        cal.set(Calendar.HOUR, hourOfDay);
                        cal.set(Calendar.MINUTE, minute);

                        globalCal.set(Calendar.HOUR, hourOfDay);
                        globalCal.set(Calendar.MINUTE, minute);

                        SimpleDateFormat dateFormat3 = new SimpleDateFormat("hh:mm a");
                        String formattedDate = dateFormat3.format(cal.getTime());
                        time.setText(formattedDate); //- bis hier -------------------------------

                        mMinute = minute;
                        mHour = hourOfDay;
                    }
                    }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        //  Save
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String in_title = title.getText().toString();
                String in_des = (des.getText().toString() != "") ? des.getText().toString() : "9999";
                String in_date;
                String in_time;
                String in_place = place.getText().toString();
                String in_icon = icons.getSelectedItem().toString();

                Event event = new Event();
                event.setTitle(in_title);
                event.setDescription(in_des);
                event.setPlace(in_place);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String formattedDate = dateFormat.format(globalCal.getTime());
                if (mYear == 0) {
                    in_date = "empty";
                    event.setDate("empty");
                }
                else {
                    event.setDate(formattedDate);
                    in_date = (mDay + "-" + (mMonth) + "-" + mYear);
                }

                SimpleDateFormat dateFormat2 = new SimpleDateFormat("hh:mm");
                String formattedDate2 = dateFormat2.format(globalCal.getTime());
                if (mHour == 0) {
                    in_time = "empty";
                    event.setTime("empty");
                }
                else {
                    event.setTime(formattedDate2);
                    in_time = (mHour + ":" + mMinute);
                }
                event.setPicture_name(in_icon);


                if (check == 0) {  // add new to db
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            long l2 = db.allDAO().insertEvent(event);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if(l2 > 0){
                                        Toast.makeText(EditActivity.this, "Event added!", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(EditActivity.this, "Event insertion failed!", Toast.LENGTH_SHORT).show();
                                    }
                                    startActivity(intent_main);
                                }
                            });
                        }
                    });

                }
                else if (check == 1) {  // save to db
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            int l2 = db.allDAO().updateEventById(in_title, in_des, in_place, in_date, in_time, in_icon, id);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if(l2 == 1){
                                        Toast.makeText(EditActivity.this, "Event is updated!", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(EditActivity.this, "Event updated failed!", Toast.LENGTH_SHORT).show();
                                    }
                                    startActivity(intent_main);
                                }
                            });
                        }
                    });
                }
                else {
                    Toast.makeText(EditActivity.this, "An error has occurred -_- Please try again.", Toast.LENGTH_SHORT).show();
                    startActivity(intent_main);
                }
            }
        });
    }

    // this event will enable the back
    // function to the button on press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}