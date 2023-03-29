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
    Button save, back, select_date, select_time;
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
        back = findViewById(R.id.btn_back);
        date = findViewById(R.id.date);
        time = findViewById((R.id.time));
        place = findViewById(R.id.edit_place);
        select_date = findViewById(R.id.btn_date);
        select_time = findViewById(R.id.btn_time);

        Calendar globalCal = Calendar.getInstance();
//
        db = Room.databaseBuilder(getApplicationContext(), DataBase.class, "countdowntimer.db").allowMainThreadQueries().build();

        final List<String> img_icons = Arrays.asList("calendar","airplane_landing","airplane_take_off",
                "alarm","beach","birthday","booking","bow_cupid","camping","christmas_tree","christmas_wreath",
                "confetti","easter_egg","easter_eggs","firework_explosion","flowers","ghost","gift",
                "gingerbread_man","jackolantern","music_festival","pay","shopping_basket","trailer");
        SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), img_icons);
        adapter.setDropDownViewResource(R.layout.dropdown);
        //  Set the spinners adapter to the previously created one.
        icons.setAdapter(adapter);

        icons.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(EditActivity.this, "seled   "+icons.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //  Get from database: title, des, list of icon
        Intent get = getIntent();
        int check = get.getIntExtra("state", 0);
        int id = get.getIntExtra("id", -1);
//        int check = 1;
        //  Get event info if is editing event
        if (check == 1) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    Event event = db.allDAO().getEventById(id);
                    title.setText(event.getTitle());
                    des.setText(event.getDescription());
                    date.setText(event.getDate());
                    time.setText(event.getTime());
                    place.setText(event.getPlace());
//                    icons.setSelection();


                    String[] date_elements = event.getDate().split("-");
                    String[] time_elements = event.getTime().split(":");

                    if (date_elements.length == 3) {
                        Calendar countdownDate = Calendar.getInstance();

                        if (time_elements.length == 2) {

                            countdownDate.set(Calendar.YEAR, Integer.parseInt(date_elements[2]));
                            countdownDate.set(Calendar.MONTH, Integer.parseInt(date_elements[1]) - 1);
                            countdownDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date_elements[0]));

                            countdownDate.set(Calendar.HOUR, Integer.parseInt(time_elements[0]));
                            countdownDate.set(Calendar.MINUTE, Integer.parseInt(time_elements[1]));

                            globalCal.set(Calendar.YEAR, Integer.parseInt(date_elements[2]));
                            globalCal.set(Calendar.MONTH, Integer.parseInt(date_elements[1]) - 1);
                            globalCal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date_elements[0]));

                            globalCal.set(Calendar.HOUR, Integer.parseInt(time_elements[0]));
                            globalCal.set(Calendar.MINUTE, Integer.parseInt(time_elements[1]));

                            SimpleDateFormat dateFormat3 = new SimpleDateFormat("EEE, MMM dd, yyyy");
                            SimpleDateFormat dateFormat4 = new SimpleDateFormat("hh:mm a");

                            String formattedDate = dateFormat3.format(countdownDate.getTime());
                            String formattedDate2 = dateFormat4.format(countdownDate.getTime());

                            date.setText(formattedDate);
                            time.setText(formattedDate2);


                        } else {
                            countdownDate.set(Calendar.YEAR, Integer.parseInt(date_elements[2]));
                            countdownDate.set(Calendar.MONTH, Integer.parseInt(date_elements[1]) - 1);
                            countdownDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date_elements[0]));

                            globalCal.set(Calendar.YEAR, Integer.parseInt(date_elements[2]));
                            globalCal.set(Calendar.MONTH, Integer.parseInt(date_elements[1]) - 1);
                            globalCal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date_elements[0]));

                            SimpleDateFormat dateFormat2 = new SimpleDateFormat("EEE, MMMM dd, yyyy");

                            String formattedDate2 = dateFormat2.format(countdownDate.getTime());
                            date.setText(formattedDate2);
                            time.setText("-");

                        }
                    }
                }
            });
        }

        //  Back
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent_main);
            }
        });

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
                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.YEAR, year);
                        cal.set(Calendar.MONTH, monthOfYear);
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        globalCal.set(Calendar.YEAR, year);
                        globalCal.set(Calendar.MONTH, monthOfYear);
                        globalCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        SimpleDateFormat dateFormat2 = new SimpleDateFormat("EEE, MMMM dd, yyyy");

                        String formattedDate2 = dateFormat2.format(cal.getTime());
                        date.setText(formattedDate2);


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
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.HOUR, hourOfDay);
                        cal.set(Calendar.MINUTE, minute);

                        globalCal.set(Calendar.HOUR, hourOfDay);
                        globalCal.set(Calendar.MINUTE, minute);

                        SimpleDateFormat dateFormat3 = new SimpleDateFormat("hh:mm a");
                        String formattedDate = dateFormat3.format(cal.getTime());
                        time.setText(formattedDate);

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
                String in_des = des.getText().toString();
                String in_place = place.getText().toString();
                String in_icon = icons.getSelectedItem().toString();

                Event event = new Event();
                event.setTitle(in_title);
                event.setDescription(in_des);
                event.setPlace(in_place);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String formattedDate = dateFormat.format(globalCal.getTime());
                event.setDate(formattedDate);

                SimpleDateFormat dateFormat2 = new SimpleDateFormat("hh:mm");
                String formattedDate2 = dateFormat2.format(globalCal.getTime());
                event.setTime(formattedDate2);

                event.setPicture_name(in_icon);

                String in_date = event.getDate();
                String in_time = event.getTime();

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
}