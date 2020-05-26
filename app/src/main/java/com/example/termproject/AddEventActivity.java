package com.example.termproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.applandeo.materialcalendarview.EventDay;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddEventActivity extends AppCompatActivity implements  AdapterView.OnItemSelectedListener{

    private EditText editEventName, editEventInfo;
    private TextView selected_loc, time_view, date_view, viewTime;
    private Button saveButton, pickLoc, addReminderBttn, btnEventStart, btnEventEnd, btnStartTime, btnEndTime,setDate, setTime;
    private static final String FILE_NAME = "events.txt";
    private CalEvent event = new CalEvent();
    private Context context = this;
    private int position;
    private long repetitionTimeinMillis;
    private String name, info, startDate ="", endDate= "", location="", startTime = "", endTime = "", selectedRepeat, remTime = "", remDate= "";
    private Spinner repSpinner;
    private Calendar myAlarmDate;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        saveButton = findViewById(R.id.saveButton);
        pickLoc = findViewById(R.id.pick_location);
        selected_loc = findViewById(R.id.selected_location);
        editEventName = findViewById(R.id.edit_event_name);
        editEventInfo = findViewById(R.id.edit_event_info);
        btnEventStart = findViewById(R.id.bttn_event_startdate);
        btnEventEnd = findViewById(R.id.bttn_event_enddate);
        btnStartTime = findViewById(R.id.bttn_event_starttime);
        btnEndTime = findViewById(R.id.bttn_event_endtime);
        time_view = findViewById(R.id.time_text);
        date_view = findViewById(R.id.date_text);
        addReminderBttn = findViewById(R.id.set_reminder);
        setDate = findViewById(R.id.bttn_remdate);
        setTime = findViewById(R.id.bttn_remtime);
        viewTime = findViewById(R.id.time_text_rem);
        repSpinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.repeat, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        repSpinner.setAdapter(adapter);
        repSpinner.setOnItemSelectedListener(this);

        Intent intent = getIntent();

        position = intent.getIntExtra("position", -1);

        if(position!=-1){
            editEventName.setText(ListEvents.event_list.get(position).getName());
            editEventInfo.setText(ListEvents.event_list.get(position).getInfo());
            time_view.setText("Time: "+ListEvents.event_list.get(position).getStartTime()+" - "+ListEvents.event_list.get(position).getEndTime());
            date_view.setText("Date: "+ListEvents.event_list.get(position).getStartDate()+" - "+ListEvents.event_list.get(position).getEndDate());
            selected_loc.setText(ListEvents.event_list.get(position).getLocation());
            viewTime.setText(ListEvents.event_list.get(position).getReminderList().size()+"Reminders:" +ListEvents.event_list.get(position).reminderList(ListEvents.event_list.get(position)));
        }

        btnStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                       startTime = ( selectedHour + ":" + selectedMinute);
                        time_view.setText("Time: "+startTime+" - "+endTime);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        btnEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        endTime = ( selectedHour + ":" + selectedMinute);
                        time_view.setText("Time: "+startTime+" - "+endTime);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        btnEventStart.setOnClickListener(new View.OnClickListener() {
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
                                startDate = (dayOfMonth + "/" + month + "/" + year);
                                date_view.setText("Date: "+startDate+" - "+endDate);
                            }
                        }, year, month, day);
                dpd.setButton(DatePickerDialog.BUTTON_POSITIVE, "Select", dpd);
                dpd.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Cancel", dpd);
                dpd.show();

            }
        });

        btnEventEnd.setOnClickListener(new View.OnClickListener() {
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
                                endDate = (dayOfMonth + "/" + month + "/" + year);
                                date_view.setText("Date: "+startDate+" - "+endDate);
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
                    name = editEventName.getText().toString();
                    info = editEventInfo.getText().toString();
                    if (name.equals("") & startDate.matches("")) {
                        Toast.makeText(getApplicationContext(), "All fields must be edited", Toast.LENGTH_LONG).show();
                    } else {
                        event.setName(name);
                        event.setInfo(info);
                        event.setStartDate(startDate);
                        event.setEndDate(endDate);
                        event.setLocation(location);
                        event.setStartTime(startTime);
                        event.setEndTime(endTime);
                        event.setRepeatTime(selectedRepeat);
                        ListEvents.event_list.add(event);
                        if(ListEvents.eAdapter != null)
                            ListEvents.eAdapter.notifyDataSetChanged();
                        writeToFile(name + "_" + info + "_" + startDate + "_" + endDate + "_"+location+"_"+startTime+"_"+endTime+"_"+selectedRepeat+"_"+event.reminderList(event)+"!\n", getApplicationContext());

                    }
                    Toast.makeText(getApplicationContext(), "Event created,", Toast.LENGTH_LONG).show();

                editEventName.setText("");
                editEventInfo.setText("");
                time_view.setText("");
                date_view.setText("");
                selected_loc.setText("");
                startAlarm(event);
                finish();
            }
        });

        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != getPackageManager().PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user, this condition will
                //        execute every time, else your else part will work
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        pickLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mapInt = new Intent(getApplicationContext(),MapsActivity.class);
                startActivityForResult(mapInt, 1);
            }
        });

        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        remTime = ( selectedHour + ":" + selectedMinute);
                        viewTime.setText("New reminder: "+remDate+"   "+remTime);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        setDate.setOnClickListener(new View.OnClickListener() {
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
                                remDate = (dayOfMonth + "/" + month + "/" + year);
                                viewTime.setText("New reminder: "+remDate+"   "+remTime);
                            }
                        }, year, month, day);
                dpd.setButton(DatePickerDialog.BUTTON_POSITIVE, "Select", dpd);
                dpd.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Cancel", dpd);
                dpd.show();

            }
        });
        addReminderBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               MyReminder rem = new MyReminder(remDate,remTime);
               event.getReminderList().add(rem);
               printReminderList(event.getReminderList());
            }
        });

    }

    private void startAlarm(CalEvent event) {
        for (MyReminder reminder:event.getReminderList()) {
            myAlarmDate = Calendar.getInstance();
            myAlarmDate.setTimeInMillis(System.currentTimeMillis());
            myAlarmDate.set(Integer.parseInt(reminder.getDate().split("/")[2]),
                    Integer.parseInt(reminder.getDate().split("/")[1])-1,
                    Integer.parseInt(reminder.getDate().split("/")[0]),
                    Integer.parseInt(reminder.getTime().split(":")[0]),
                    Integer.parseInt(reminder.getTime().split(":")[1]), 0);

            if(event.getRepeatTime().equals("Every Month")){
                Calendar cal = myAlarmDate;
                int currentMonth = myAlarmDate.get(Calendar.MONTH);
                currentMonth++;
                if(currentMonth > Calendar.DECEMBER){
                    currentMonth = Calendar.JANUARY;
                    cal.set(Calendar.YEAR, cal.get(Calendar.YEAR)+1);
                }
                cal.set(Calendar.MONTH, currentMonth);
                repetitionTimeinMillis = cal.getTimeInMillis() - myAlarmDate.getTimeInMillis();
            }else if(event.getRepeatTime().equals("Every Week")){
                repetitionTimeinMillis = DateUtils.WEEK_IN_MILLIS;
            }else if(event.getRepeatTime().equals("Every Day")){
                repetitionTimeinMillis = DateUtils.DAY_IN_MILLIS;
            }else if(event.getRepeatTime().equals("Every Year")){
                repetitionTimeinMillis = DateUtils.YEAR_IN_MILLIS;
            }

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            Intent intent = new Intent(this, ReminderReceiver.class);
            final int id = (int) System.currentTimeMillis();
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intent, 0);
            //alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, myAlarmDate.getTimeInMillis(), repetitionTimeinMillis, pendingIntent);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, myAlarmDate.getTimeInMillis(), pendingIntent);
            //Toast.makeText(this, "Reminders-->"+event.getReminderList().toString(), Toast.LENGTH_SHORT).show();
            //System.out.println("reminder--->"+reminder.getDate()+"--"+reminder.getTime());
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("result");
                if(!result.equals(""))
                    location = result;
                //System.out.println(location);
                selected_loc.setText("Lat:" + location.split(",")[0] + " Lon:" + location.split(",")[1]);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult

    private void printReminderList(ArrayList<MyReminder> reminderList){
        System.out.println("*************************************REMINDERLIST*************************************");
        for (MyReminder r: reminderList) {
            System.out.println("Reminder->"+r.getTime()+" - "+r.getDate());
        }
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
                String data = val.getName()+"_"+ val.getInfo() + "_" + val.getStartDate() + "_" + val.getEndDate() + "_"+val.getLocation()+"_"+val.getStartTime()+"_"+val.getEndTime()+"_"+val.getRepeatTime()+"_"+val.reminderList(val)+"!"+"\n";
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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        selectedRepeat = text;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
