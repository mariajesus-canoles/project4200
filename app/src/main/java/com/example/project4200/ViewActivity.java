package com.example.project4200;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

        import androidx.appcompat.app.AppCompatActivity;

        import android.os.Bundle;
        import android.os.CountDownTimer;
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
}