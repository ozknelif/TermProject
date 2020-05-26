package com.example.termproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.core.view.MenuItemCompat;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Share extends AppCompatActivity {
    private TextView display;
    private ShareActionProvider provider = new ShareActionProvider(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        display = findViewById(R.id.display_details);
        Intent rett = getIntent();
        int pos = rett.getIntExtra("eventId", -1);
        getActionBar();

        display.setText(ListEvents.event_list.get(pos).getName()+"\n"+ListEvents.event_list.get(pos).getInfo());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.share_menu, menu);
        provider = (ShareActionProvider) MenuItemCompat.getActionProvider(menu.findItem(R.id.share));
        if(provider!=null){
            Intent share = new Intent();
            share.setAction(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT, display.getText());
            provider.setShareIntent(share);
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.share) {

            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT, display.getText());
            provider.setShareIntent(share);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
