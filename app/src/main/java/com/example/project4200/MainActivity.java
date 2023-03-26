package com.example.project4200;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
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

        /*Picture p = new Picture();
        p.setId(1);
        p.setName("testPicture");*/

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                //db.allDAO().insertPicture(p);
                ArrayList<Event> eventList = (ArrayList<Event>) db.allDAO().getAllEvents();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    eventList.sort(new Comparator<Event>() {
                        @Override
                        public int compare(Event o1, Event o2) {
                            String[] date_elements = o1.getDate().split("-");
                            String[] date_elements2 = o2.getDate().split("-");

                            if (date_elements.length == 3 && date_elements2.length == 3) {
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                Date event_date, event_date2;

                                try {
                                    event_date = dateFormat.parse(o1.getDate());
                                    event_date2 = dateFormat.parse(o2.getDate());
                                } catch (ParseException e) {
                                    throw new RuntimeException(e);
                                }

                                return event_date.compareTo(event_date2);

                            }
                            return 0;

                        }
                    });
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                         myAdapter = new MyAdapter(eventList, MainActivity.this);
                        recyclerView.setAdapter(myAdapter);
                    }
                });
            }
        });

        CustomItemDecorator itemDecorator = new CustomItemDecorator(10);
        recyclerView.addItemDecoration(itemDecorator);


        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                myAdapter.onItemDismiss(position);
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<Event> eventList = (ArrayList<Event>) db.allDAO().getAllEvents();
                        db.allDAO().deleteEvent(eventList.get(position));
                       /* handler.post(new Runnable() {
                            @Override
                            public void run() {
                                myAdapter = new MyAdapter(eventList, MainActivity.this);
                                recyclerView.setAdapter(myAdapter);

                            }
                        });*/
                    }
                });
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);


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

    /*protected void onResume() {
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
    }*/
}