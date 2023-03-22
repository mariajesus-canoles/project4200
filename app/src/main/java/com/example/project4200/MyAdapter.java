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
    private ArrayList<Event> eventList;

    /**
     *
     * @param eventList
     * @param context
     */
    public MyAdapter(ArrayList<Event> eventList, Context context) {
        this.eventList = eventList;
        this.context = context;
    }


    /**
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return
     */
    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new MyViewHolder(view);
    }

    /**
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.title.setText(event.getTitle());
        holder.date.setText(event.getTime());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            holder.days.setText("(to fix) " + event.getTime());
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

    /**
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return eventList.size();
    }

    /**
     *
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView date;
        TextView days;
        CardView cardView;
        ImageView imageView;


        /**
         *
         * @param itemView
         */
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
