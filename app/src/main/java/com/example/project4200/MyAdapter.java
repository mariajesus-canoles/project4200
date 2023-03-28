package com.example.project4200;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Event> eventList;
    RecyclerView recyclerView;

    public MyAdapter(ArrayList<Event> eventList, Context context) {
        this.eventList = eventList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new MyViewHolder(view);
    }

    public void onItemDismiss(int position) {
        eventList.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.title.setText(event.getTitle());
        holder.date.setText(event.getTime());

        // HashMap to bind drawable image with name
        HashMap<String, Integer> images = new HashMap<String, Integer>();
        images.put("calendar", Integer.valueOf(R.drawable.calendar));
        images.put("airplane_landing", Integer.valueOf(R.drawable.airplane_landing));
        images.put("airplane_take_off", Integer.valueOf(R.drawable.airplane_take_off));
        images.put("alarm", Integer.valueOf(R.drawable.alarm));
        images.put("beach", Integer.valueOf(R.drawable.beach));
        images.put("birthday", Integer.valueOf(R.drawable.birthday));
        images.put("booking", Integer.valueOf(R.drawable.booking));
        images.put("bow_cupid", Integer.valueOf(R.drawable.bow_cupid));
        images.put("camping", Integer.valueOf(R.drawable.camping));
        images.put("christmas_tree", Integer.valueOf(R.drawable.christmas_tree));
        images.put("christmas_wreath", Integer.valueOf(R.drawable.christmas_wreath));
        images.put("confetti", Integer.valueOf(R.drawable.confetti));
        images.put("easter_egg", Integer.valueOf(R.drawable.easter_egg));
        images.put("easter_eggs", Integer.valueOf(R.drawable.easter_eggs));
        images.put("firework_explosion", Integer.valueOf(R.drawable.firework_explosion));
        images.put("flowers", Integer.valueOf(R.drawable.flowers));
        images.put("ghost", Integer.valueOf(R.drawable.ghost));
        images.put("gift", Integer.valueOf(R.drawable.gift));
        images.put("gingerbread_man", Integer.valueOf(R.drawable.gingerbread_man));
        images.put("jackolantern", Integer.valueOf(R.drawable.jackolantern));
        images.put("music_festival", Integer.valueOf(R.drawable.music_festival));
        images.put("pay", Integer.valueOf(R.drawable.pay));
        images.put("shopping_basket", Integer.valueOf(R.drawable.shopping_basket));
        images.put("trailer", Integer.valueOf(R.drawable.trailer));

        holder.imageView.setImageResource(images.get(event.getPicture_name()).intValue());

        String[] date_elements = event.getDate().split("-");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());

        if(date_elements.length == 3) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date event_date;
            try {
                 event_date = dateFormat.parse(event.getDate());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            Date now = new Date();
            long diffInMillis = event_date.getTime() - now.getTime();
            long daysBetween = diffInMillis / (1000 * 60 * 60 * 24);
            holder.days.setText(Long.toString(daysBetween));

            SimpleDateFormat dateFormat2 = new SimpleDateFormat("EEE, MMMM dd, yyyy");
            String formattedDate = dateFormat2.format(event_date);
            holder.date.setText(formattedDate);
        }else {
            holder.const_string.setText("");
            holder.date.setText("");
            holder.days.setText("-");
        }

        int tmp = 0;
        int tmp2 = 0;
        switch(position % 6) {
            case 0:
                tmp = ContextCompat.getColor(context, R.color._1light_grey);
                tmp2 = ContextCompat.getColor(context, R.color._7peach);
                break;
            case 1:
                tmp = ContextCompat.getColor(context, R.color._2sky_blue);
                tmp2 = ContextCompat.getColor(context, R.color._8peach);
                break;
            case 2:
                tmp = ContextCompat.getColor(context, R.color._3seafoam_green);
                tmp2 = ContextCompat.getColor(context, R.color._9peach);
                break;
            case 3:
                tmp = ContextCompat.getColor(context, R.color._4lavender);
                tmp2 = ContextCompat.getColor(context, R.color._10peach);
                break;
            case 4:
                tmp = ContextCompat.getColor(context, R.color._5pale_pink);
                tmp2 = ContextCompat.getColor(context, R.color._11peach);
                break;
            case 5:
                tmp = ContextCompat.getColor(context, R.color._6peach);
                tmp2 = ContextCompat.getColor(context, R.color._12peach);
                break;
        }
        holder.cardView.setCardBackgroundColor(tmp);
        holder.linearLayout.setBackgroundColor(tmp2);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewActivity.class);
                intent.putExtra("id", event.getId());
                //TODO is that correct?
                startActivity(context, intent, null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView date;
        TextView days;
        TextView const_string;
        CardView cardView;
        ImageView imageView;
        LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView_title);
            date = itemView.findViewById(R.id.textView_date);
            cardView = itemView.findViewById(R.id.cardView);
            days = itemView.findViewById(R.id.textView_days);
            imageView = itemView.findViewById(R.id.imageView);
            const_string = itemView.findViewById(R.id.textView_const_string);
            linearLayout = itemView.findViewById(R.id.linearLayout_days_left);
        }
    }
}
