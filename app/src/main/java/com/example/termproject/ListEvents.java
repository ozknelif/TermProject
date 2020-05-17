package com.example.termproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ListEvents extends AppCompatActivity {
    private Context context = this;
    static ArrayList<CalEvent> event_list = new ArrayList<>();
    private EventAdapter eAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_events);

        if(event_list.isEmpty())
            readFromFile(getApplicationContext());

        recyclerView = findViewById(R.id.recyclerView);
        registerForContextMenu(recyclerView);
        openContextMenu(recyclerView);
        eAdapter = new EventAdapter(event_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(eAdapter);


        eAdapter.notifyDataSetChanged();



        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent editorIntent = new Intent(ViewNotes.this, CreateNote.class);
                editorIntent.putExtra("noteId", i);
                startActivity(editorIntent);
            }
        });*/

       /* listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int delete, long id) {

                new AlertDialog.Builder(getApplicationContext())
                        .setMessage("Delete this event?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                event_list.remove(delete);
                                adapter.notifyDataSetChanged();
                                //dosyadan da silme yapılacak
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            }
        });*/

    }

    /*@Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.rv_menu, menu);
    }*/
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
                return true;
            case 122:
                System.out.println("delete "+item.getGroupId());
                updateFile(item.getGroupId());
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
                    CalEvent event = new CalEvent(line.split("_")[0], line.split("_")[1], line.split("_")[2], line.split("_")[3], "location");
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
                String data = event.getName()+"_"+event.getInfo()+"_"+event.getStartDate()+"_"+event.getEndDate()+"_location\n";
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