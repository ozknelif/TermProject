package com.example.termproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class AddEventActivity extends AppCompatActivity {

    private EditText editEventName, editEventInfo, editEventStart, editEventEnd;
    private TextView selected_date_view;
    private String selectedDate;
    private Button saveButton;
    private static final String FILE_NAME = "events.txt";
    private CalEvent event;
    private Context context = this;
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        saveButton = findViewById(R.id.saveButton);



        editEventName = findViewById(R.id.edit_event_name);
        editEventInfo = findViewById(R.id.edit_event_info);
        editEventStart = findViewById(R.id.edit_event_startdate);
        editEventEnd = findViewById(R.id.edit_event_enddate);


        Intent intent = getIntent();
        selectedDate = intent.getStringExtra("date");
        editEventStart.setText(selectedDate);
        editEventEnd.setText(selectedDate);

        position = intent.getIntExtra("position", -1);
        System.out.println(position);
        if(position!=-1){
            editEventName.setText(ListEvents.event_list.get(position).getName());
            editEventInfo.setText(ListEvents.event_list.get(position).getInfo());
            editEventStart.setText(ListEvents.event_list.get(position).getStartDate());
            editEventEnd.setText(ListEvents.event_list.get(position).getEndDate());
        }
        editEventStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                month += 1;
                                editEventStart.setText(dayOfMonth + "/" + month + "/" + year);
                            }
                        }, year, month, day);
                dpd.setButton(DatePickerDialog.BUTTON_POSITIVE, "Select", dpd);
                dpd.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Cancel", dpd);
                dpd.show();

            }
        });

        editEventEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                month += 1;
                                editEventEnd.setText(dayOfMonth + "/" + month + "/" + year);
                            }
                        }, year, month, day);
                dpd.setButton(DatePickerDialog.BUTTON_POSITIVE, "Select", dpd);
                dpd.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Cancel", dpd);
                dpd.show();

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position!=-1){
                    ListEvents.event_list.remove(position);
                    writeToNewFile(ListEvents.event_list);
                }
                    String name;
                    String info;
                    String startDate;
                    String endDate;
                    name = editEventName.getText().toString();
                    info = editEventInfo.getText().toString();
                    startDate = editEventStart.getText().toString();
                    endDate = editEventEnd.getText().toString();
                    if (name.equals("") & startDate.matches("")) {
                        Toast.makeText(getApplicationContext(), "All fields must be edited", Toast.LENGTH_LONG).show();
                    } else {
                        event = new CalEvent(name, info, startDate, endDate, "location");
                        ListEvents.event_list.add(event);
                        System.out.println("name=" + name + ", date=" + startDate);
                        writeToFile(name + "_" + info + "_" + startDate + "_" + endDate + "_location\n", getApplicationContext());

                    }
                    Toast.makeText(getApplicationContext(), "Event created", Toast.LENGTH_LONG).show();

                editEventName.setText("");
                editEventInfo.setText("");
                editEventStart.setText("");
                editEventEnd.setText("");
                finish();
            }
        });


    }

    private void writeToFile(String data, Context context) {
        File path = context.getFilesDir();
        File file = new File(path, FILE_NAME);
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(file, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            stream.write(data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void writeToNewFile(ArrayList<CalEvent> list) {
        File path = context.getFilesDir();
        File file = new File(path, FILE_NAME);
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            for(CalEvent val : list){
                String data = val.getName()+"_"+ val.getInfo() + "_" + val.getStartDate() + "_" + val.getEndDate() + "_location\n";
                stream.write(data.getBytes());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
