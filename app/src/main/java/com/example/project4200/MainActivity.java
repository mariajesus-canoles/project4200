package com.example.project4200;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<Appointment> appointmentList = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            appointmentList.add(new Appointment(1, "Vacation in Quebec", "Quebec",
                    LocalDateTime.of(LocalDate.now(), LocalTime.now())));
            appointmentList.add(new Appointment(1, "Vacation in Manitoba", "Winnipeg",
                    LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.now().plusHours(6))));
            appointmentList.add(new Appointment(1, "Vacation in Quebec", "Quebec",
                    LocalDateTime.of(LocalDate.now(), LocalTime.now())));
        }

        //ArrayList<Appointment> appointmentList = DBHelper.getInfoAsList(); would be easiest!

        MyAdapter adapter = new MyAdapter(appointmentList, MainActivity.this);
        recyclerView.setAdapter(adapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);
            }


        });
    }
}