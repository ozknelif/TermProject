package com.example.termproject;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {
    private ArrayList<CalEvent> events = new ArrayList<>();
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        TextView ename, einfo, estart, etimestart, eloc;
        LinearLayout itemLayout;
        MyViewHolder(View view) {
            super(view);
            ename = view.findViewById(R.id.rveventName);
            einfo = view.findViewById(R.id.rvEventInfo);
            estart = view.findViewById(R.id.rvDate);
            etimestart = view.findViewById(R.id.rvTime);
            eloc = view.findViewById(R.id.rvLocation);
            itemLayout = view.findViewById(R.id.item_layout);
            itemLayout.setOnCreateContextMenuListener(this);
            view.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {


                menu.add(this.getAdapterPosition(),121,0,"Update");
                menu.add(this.getAdapterPosition(),122,1,"Delete");
                menu.add(this.getAdapterPosition(),123,2,"Share");


        }

    }
    public EventAdapter(ArrayList<CalEvent> events) {
        this.events = events;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_view, parent, false);

        return new MyViewHolder(itemView);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CalEvent event = events.get(position);
        holder.ename.setText(event.getName());
        holder.einfo.setText(event.getInfo());
        holder.estart.setText("Date: "+event.getStartDate()+" - "+event.getEndDate());
        holder.etimestart.setText("Time: "+event.getStartTime()+" - "+event.getEndTime());
        holder.eloc.setText("Location: " +event.getLocation());
    }
    @Override
    public int getItemCount() {
        return events.size();
    }


}

