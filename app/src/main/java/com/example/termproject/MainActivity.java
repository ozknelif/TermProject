package com.example.termproject;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.EventLog;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CalendarView;
import android.widget.TextView;

import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.termproject.SettingsActivity.isNightModeOn;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton mMainAddFab, mAddEventFab, mListEventFab;
    private TextView mAddEventText, mListEventText;
    private CalendarView calendarView;
    private Animation mFabOpenAnim, mFabCloseAnim;
    private String selectedDate;
    private boolean isOpen;
    private SharedPreferences appSettingPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainAddFab = findViewById(R.id.main_add_fab);
        mAddEventFab = findViewById(R.id.add_event_fab);
        mListEventFab = findViewById(R.id.list_events_fab);
        calendarView = findViewById(R.id.calenderView);
        mAddEventText = findViewById(R.id.add_event_text);
        mListEventText = findViewById(R.id.list_events_text);

        mFabOpenAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fab_open);
        mFabCloseAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fab_close);

        appSettingPrefs = getSharedPreferences("AppSettingPrefs", 0);
        isNightModeOn = appSettingPrefs.getBoolean("NightMode", false);

        if(isNightModeOn){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        isOpen = false;

        if(isNightModeOn){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        mMainAddFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isOpen){

                    mAddEventFab.setAnimation(mFabCloseAnim);
                    mListEventFab.setAnimation(mFabCloseAnim);

                    mAddEventText.setVisibility(View.INVISIBLE);
                    mListEventText.setVisibility(View.INVISIBLE);

                    isOpen = false;
                } else {

                    mAddEventFab.setAnimation(mFabOpenAnim);
                    mListEventFab.setAnimation(mFabOpenAnim);

                    mAddEventText.setVisibility(View.VISIBLE);
                    mListEventText.setVisibility(View.VISIBLE);

                    isOpen = true;
                }

            }
        });

        mAddEventFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addEventIntent = new Intent(getApplicationContext(), AddEventActivity.class);
                addEventIntent.putExtra("date",selectedDate);
                isOpen = false;
                startActivity(addEventIntent);

            }
        });
        mListEventFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listEventIntent = new Intent(getApplicationContext(), ListEvents.class);
                isOpen = false;
                startActivity(listEventIntent);

            }
        });
    }

        public void onResume() {

            super.onResume();

        }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settingsmenu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.sttngs) {
            Intent settings = new Intent(this, SettingsActivity.class);
            startActivity(settings);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
