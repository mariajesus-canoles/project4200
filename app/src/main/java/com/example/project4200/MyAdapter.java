package com.example.project4200;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Appointment> appointmentList;

    public MyAdapter(ArrayList<Appointment> appointmentList, Context context) {
        this.appointmentList = appointmentList;
        this.context = context;
    }


    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        Appointment appointment = appointmentList.get(position);
        holder.title.setText(appointment.getTitle());
        holder.date.setText(appointment.getTime().toString());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            holder.days.setText("" + appointment.getTime().getDayOfYear());
        }


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewActivity.class);
                //TODO is that correct?
                startActivity(context, intent, null);


            }
        });
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView date;
        TextView days;
        CardView cardView;
        ImageView imageView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView_title);
            date = itemView.findViewById(R.id.textView_date);
            cardView = itemView.findViewById(R.id.cardView);
            days = itemView.findViewById(R.id.textView_days);
            imageView = itemView.findViewById(R.id.imageView);
        }

    }
}
