package com.example.qlass.gdziejestkanar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;

import java.util.Calendar;

public class SecondActivity extends AppCompatActivity {
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private CheckBox notification, linesCheckBox;
    private PendingIntent pendingIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        notification = (CheckBox) findViewById(R.id.notiCheckBox);
        linesCheckBox = (CheckBox) findViewById(R.id.linesCheckBox);


        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();
        checkSharedPreferences();
        Intent alarmIntent = new Intent(SecondActivity.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(SecondActivity.this, 0, alarmIntent, 0);
        linesCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    mEditor.putString(getString(R.string.show_my_lines), "True");
                    mEditor.apply();
                }
                else
                {
                    mEditor.putString(getString(R.string.show_my_lines), "False");
                    mEditor.commit();
                }
            }
        });
        notification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    mEditor.putString(getString(R.string.notification), "True");
                    mEditor.apply();

                    AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    int interval = 1000 * 60 * 20;

                    /* Set the alarm to start at 10:00 AM */
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.set(Calendar.HOUR_OF_DAY, 10);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.add(Calendar.DATE, 1);

                    /* Repeating on every day interval */
                    manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                            1000 * 60 * 60 * 24, pendingIntent);
                }
                else
                {
                    mEditor.putString(getString(R.string.notification), "False");
                    mEditor.commit();

                    AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    manager.cancel(pendingIntent);
                }
            }
        });




        Button tramBtn = (Button) findViewById(R.id.tramBtn);
        tramBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tram = new Intent(getApplicationContext(),TramActivity.class);
                startActivity(tram);
            }
        });

        Button busBtn = (Button) findViewById(R.id.busBtn);
        busBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bus = new Intent(getApplicationContext(),BusActivity.class);
                startActivity(bus);
            }
        });

        GridView listGridView = (GridView) findViewById(R.id.savedLine);
        SecondAdapter adapter = new SecondAdapter(this);
        listGridView.setAdapter(adapter);
    }

    private void checkSharedPreferences()
    {
        String notifi = mPreferences.getString(getString(R.string.notification), "False");
        String my_lines_check = mPreferences.getString(getString(R.string.show_my_lines), "False");

        if(notifi.equals("True"))
        {
            notification.setChecked(true);
        }
        else
            notification.setChecked(false);

        if(my_lines_check.equals("True"))
        {
            linesCheckBox.setChecked(true);
        }
        else
            linesCheckBox.setChecked(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        GridView listGridView = (GridView) findViewById(R.id.savedLine);
        SecondAdapter adapter = new SecondAdapter(this);
        listGridView.setAdapter(adapter);
    }




}
