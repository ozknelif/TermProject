package com.example.termproject;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton mMainAddFab, mAddEventFab, mListEventFab;
    private TextView mAddEventText, mListEventText;
    private CalendarView calendarView;
    private Animation mFabOpenAnim, mFabCloseAnim;
    private String selectedDate;
    private boolean isOpen;





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

        isOpen = false;

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

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                selectedDate = (dayOfMonth+" / "+month+" / "+year);
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
                //listEventIntent.putExtra("date",selectedDate);
                isOpen = false;
                startActivity(listEventIntent);

            }
        });
    }

        public void onResume() {

            super.onResume();

        }

}
