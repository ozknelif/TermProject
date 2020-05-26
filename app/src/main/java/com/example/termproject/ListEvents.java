package com.example.termproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ListEvents extends AppCompatActivity {
    private Context context = this;
    static ArrayList<CalEvent> event_list = new ArrayList<>();
    static EventAdapter eAdapter;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_events);


        recyclerView = findViewById(R.id.recyclerView);
        registerForContextMenu(recyclerView);
        openContextMenu(recyclerView);

        if(event_list.isEmpty())
            readFromFile(getApplicationContext());


        eAdapter = new EventAdapter(event_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(eAdapter);
        eAdapter.notifyDataSetChanged();



    }

    protected void onResume() {
        super.onResume();
        eAdapter.notifyDataSetChanged();
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch ((item.getItemId())){
            case 121:
                System.out.println("update");
                Intent i = new Intent(this,AddEventActivity.class);
                i.putExtra("position",item.getGroupId());
                startActivity(i);
                Toast.makeText(getApplicationContext(), "Event updated", Toast.LENGTH_LONG).show();
                return true;
            case 122:
                System.out.println("delete "+item.getGroupId());
                updateFile(item.getGroupId());
                eAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "Event deleted", Toast.LENGTH_LONG).show();
                return true;
            case 123:
                Intent intent = new Intent(getApplicationContext(),Share.class);
                intent.putExtra("eventId", item.getGroupId());
                startActivity(intent);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void readFromFile(Context context) {
        File path = context.getFilesDir();
        File file = new File(path, "events.txt");
        try {
            FileInputStream inputStream = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            while(reader.ready()) {
                String line = reader.readLine();
                if(!line.equals("")) {
                    CalEvent event = new CalEvent(line.split("_")[0], line.split("_")[1], line.split("_")[2],
                            line.split("_")[3], line.split("_")[4], line.split("_")[5], line.split("_")[6],line.split("_")[7]);
                    ArrayList<MyReminder> list = new ArrayList<>();
                    int i = 0;
                    if(!line.split("_")[8].equals("")){
                        while (!line.split("_")[8].split(",")[i].equals("!")){
                            MyReminder rem = new MyReminder("","");
                            rem.setDate(line.split("_")[8].split(",")[i].split("-")[1]);
                            rem.setTime(line.split("_")[8].split(",")[i].split("-")[0]);
                            list.add(rem);
                            i++;
                        }
                    }
                    event.setReminderList(list);
                    event_list.add(event);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    private void updateFile(int position) {
        event_list.remove(position);
        File path = context.getFilesDir();
        File file = new File(path, "events.txt");
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            for (CalEvent event : ListEvents.event_list) {
                String data = event.getName()+"_"+event.getInfo()+"_"+event.getStartDate()+"_"+event.getEndDate()+"_"+event.getLocation()+"_"+event.getStartTime()+"_"+event.getEndTime()+"_"+event.getRepeatTime()+"_"+event.reminderList(event)+"\n";
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