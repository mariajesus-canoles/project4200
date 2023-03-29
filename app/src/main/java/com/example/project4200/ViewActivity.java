package com.example.project4200;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.os.HandlerCompat;
import androidx.room.Room;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
        import android.os.Bundle;
        import android.os.CountDownTimer;
import android.view.MenuItem;
import android.widget.TextView;

        import java.text.DateFormat;
        import java.util.Calendar;
        import java.util.Locale;
        import java.util.concurrent.TimeUnit;

public class ViewActivity extends AppCompatActivity {

    TextView tvCountdownDate, tvCountdownTimer;
    TextView textView_title, textView_description, textView_place;

    Button btnEdit;
    ImageView imageView;

    DataBase db;
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Handler handler = HandlerCompat.createAsync(Looper.getMainLooper());
    String date = "", time = "", picture_name = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        btnEdit = findViewById(R.id.editBtn);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);

        // Customize the back button
        actionBar.setHomeAsUpIndicator(R.drawable.back_arrow);
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        tvCountdownDate = findViewById(R.id.tv_countdown_date);
        tvCountdownTimer = findViewById(R.id.tv_countdown_timer);
        textView_title = findViewById(R.id.textView_title2);
        textView_description = findViewById(R.id.textView_description);
        textView_place = findViewById(R.id.textView_place);
        imageView = findViewById(R.id.imageView2);

        db = Room.databaseBuilder(getApplicationContext(), DataBase.class,
                "countdowntimer.db").allowMainThreadQueries().build();

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                Event event = db.allDAO().getEventById(id);

                textView_place.setText(event.getPlace());
                textView_title.setText(event.getTitle());
                textView_description.setText(event.getDescription());
                date = event.getDate();
                time = event.getTime();
                picture_name = event.getPicture_name();

                handler.post(new Runnable() {
                    @Override
                    public void run() {

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

                        imageView.setImageResource(images.get(event.getPicture_name()).intValue());

                        String[] date_elements = date.split("-");
                        String[] time_elements = time.split(":");

                        if (date_elements.length == 3) {
                            Calendar countdownDate = Calendar.getInstance();
                            SimpleDateFormat dateFormat;
                            String formattedDate;
                            countdownDate.set(Calendar.YEAR, Integer.parseInt(date_elements[2]));
                            countdownDate.set(Calendar.MONTH, Integer.parseInt(date_elements[1]) - 1);
                            countdownDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date_elements[0]));
                            if (time_elements.length == 2) {
                                countdownDate.set(Calendar.HOUR, Integer.parseInt(time_elements[0]));
                                countdownDate.set(Calendar.MINUTE, Integer.parseInt(time_elements[1]));

                                dateFormat = new SimpleDateFormat("EEE, MMM dd, yyyy hh:mm a");
                                formattedDate = dateFormat.format(countdownDate.getTime());

                            } else {
                                dateFormat = new SimpleDateFormat("EEE, MMMM dd, yyyy");
                                formattedDate = dateFormat.format(countdownDate.getTime());
                            }
                            tvCountdownDate.setText("Countdown to: " + formattedDate);


                            new CountDownTimer(countdownDate.getTimeInMillis() - System.currentTimeMillis(), 1000) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    long days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished);
                                    millisUntilFinished -= TimeUnit.DAYS.toMillis(days);
                                    long hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished);
                                    millisUntilFinished -= TimeUnit.HOURS.toMillis(hours);
                                    long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                                    millisUntilFinished -= TimeUnit.MINUTES.toMillis(minutes);
                                    long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);

                                    String countdown = String.format(Locale.getDefault(), "%02d:%02d:%02d:%02d", days, hours, minutes, seconds);
                                    tvCountdownTimer.setText(countdown);
                                }

                                @Override
                                public void onFinish() {
                                    tvCountdownTimer.setText("Countdown finished!");
                                }
                            }.start();

                        } else {
                            tvCountdownDate.setText("Date and time not specified");

                        }
                    }
                });

            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewActivity.this, EditActivity.class);
                intent.putExtra("state", 1);
                intent.putExtra("id", id);
                startActivity(intent);
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