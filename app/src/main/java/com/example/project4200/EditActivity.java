package com.example.project4200;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {

    EditText title, des, time, place;
    Spinner icons;
    Button save, back;


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
        time = findViewById(R.id.edit_time);
        place = findViewById(R.id.edit_place);

        DBHelper db = new DBHelper(getApplicationContext());
        db.getReadableDatabase();

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

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check == 0) {  // add new to db
                    String t = title.getText().toString();
                    String d = des.getText().toString();
                    String ti = time.getText().toString();
                    String p = place.getText().toString();
                    long l =  db.createDataTable1(t, d, p, ti, 1L);
                    if (l<0) {
                        Toast.makeText(EditActivity.this, "Error - Add value", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(EditActivity.this, "Event added!", Toast.LENGTH_SHORT).show();
                        title.setText("");
                        des.setText("");
                        time.setText("");
                        place.setText("");
                    }
                    startActivity(intent);
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