package com.example.project4200;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    MyAdapter myAdapter;
    DataBase db;
     ExecutorService executorService = Executors.newSingleThreadExecutor();
     Handler handler = HandlerCompat.createAsync(Looper.getMainLooper());


    /**
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        db = Room.databaseBuilder(getApplicationContext(), DataBase.class,
                "countdowntimer.db").allowMainThreadQueries().build();

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                ArrayList<Event> eventList = (ArrayList<Event>) db.allDAO().getAllEvents();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                         myAdapter = new MyAdapter(eventList, MainActivity.this);
                        recyclerView.setAdapter(myAdapter);

                    }
                });
            }
        });

        /*ArrayList<Event> eventList2 = new ArrayList<>();
        Event event;
        event = new Event();
        event.setTitle("Vacation in Vancouver");
        event.setTime("End of April");
        eventList2.add(event);
        Event event2 = new Event();
        event2.setTitle("Banff National Park");
        event2.setTime("May to June");
        eventList2.add(event2);
        myAdapter = new MyAdapter(eventList2, MainActivity.this);
        recyclerView.setAdapter(myAdapter);*/



        /**
         *
         */
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("state", 0);
                startActivity(intent);
            }
        });
    }

    protected void onResume() {
        super.onResume();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                ArrayList<Event> eventList = (ArrayList<Event>) db.allDAO().getAllEvents();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        myAdapter = new MyAdapter(eventList, MainActivity.this);
                        recyclerView.setAdapter(myAdapter);

                    }
                });
            }
        });
    }
}