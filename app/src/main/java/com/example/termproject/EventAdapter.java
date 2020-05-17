package com.example.termproject;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {
    private ArrayList<CalEvent> events = new ArrayList<>();

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        TextView ename, einfo, estart, eend;
        LinearLayout itemLayout;
        MyViewHolder(View view) {
            super(view);
            ename = view.findViewById(R.id.rveventName);
            einfo = view.findViewById(R.id.rvEventInfo);
            estart = view.findViewById(R.id.rvStart);
            eend = view.findViewById(R.id.rvEnd);
            itemLayout = view.findViewById(R.id.item_layout);
            itemLayout.setOnCreateContextMenuListener(this);
            view.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

                menu.add(this.getAdapterPosition(),121,0,"Update");
                menu.add(this.getAdapterPosition(),122,1,"Delete");

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
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CalEvent event = events.get(position);
        holder.ename.setText(event.getName());
        holder.einfo.setText(event.getInfo());
        holder.estart.setText(event.getStartDate());
        holder.eend.setText(event.getEndDate());
    }
    @Override
    public int getItemCount() {
        return events.size();
    }


}

