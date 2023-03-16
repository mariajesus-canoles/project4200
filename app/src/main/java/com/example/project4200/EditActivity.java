package com.example.project4200;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {

    EditText title, des;
    Spinner icons;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        title = findViewById(R.id.edit_title);
        des = findViewById(R.id.edit_des);
        icons = findViewById(R.id.spinner);
        save = findViewById(R.id.btn_save);

        //create a list of items for the spinner.
        ArrayList<String> items = new ArrayList<String>();
        items.add("1");
        items.add("22");
        items.add("333");
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        icons.setAdapter(adapter);

//        Get from database: title, des, list of icon

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t = title.getText().toString();
                String d = des.getText().toString();
//                Write to database
//                If saved success
                Intent intent = new Intent(EditActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}