package com.example.project4200;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

        import androidx.appcompat.app.AppCompatActivity;

        import android.os.Bundle;
        import android.os.CountDownTimer;
import android.view.MenuItem;
import android.widget.TextView;

        import java.text.DateFormat;
        import java.util.Calendar;
        import java.util.Locale;
        import java.util.concurrent.TimeUnit;

public class ViewActivity extends AppCompatActivity {

    private TextView tvCountdownDate;
    private TextView tvCountdownTimer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);

        // Customize the back button
        actionBar.setHomeAsUpIndicator(R.drawable.back_arrow);
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        tvCountdownDate = findViewById(R.id.tv_countdown_date);
        tvCountdownTimer = findViewById(R.id.tv_countdown_timer);

        // Set the date/time you want to count down to
        Calendar countdownDate = Calendar.getInstance();
        countdownDate.set(2023, 3, 31, 0, 0, 0); // 31 March 2023 00:00:00

        // Update the countdown date TextView
        String countdownDateString = DateFormat.getDateTimeInstance().format(countdownDate.getTime());
        tvCountdownDate.setText("Countdown to: " + countdownDateString);

        // Set up the countdown timer
        new CountDownTimer(countdownDate.getTimeInMillis() - System.currentTimeMillis(), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update the countdown timer TextView
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
                // Handle countdown timer finish event
                tvCountdownTimer.setText("Countdown finished!");
            }
        }.start();
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