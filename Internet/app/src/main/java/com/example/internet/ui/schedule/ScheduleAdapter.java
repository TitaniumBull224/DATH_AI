package com.example.internet.ui.schedule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.internet.R;

import java.util.List;

public class ScheduleAdapter extends BaseAdapter {
    private List<Schedule> schedules;

    public ScheduleAdapter(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    @Override
    public int getCount() {
        return schedules.size();
    }

    @Override
    public Object getItem(int position) {
        return schedules.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_rows, parent, false);
        }
        Schedule schedule = schedules.get(position);
        TextView nameTextView = convertView.findViewById(R.id.nameTextView);
        TextView datesTextView = convertView.findViewById(R.id.datesTextView);
        TextView timesTextView = convertView.findViewById(R.id.timesTextView);
        TextView descriptionTextView = convertView.findViewById(R.id.descriptionTextView);
        nameTextView.setText(schedule.getName());
        datesTextView.setText(schedule.getDateBegin() + " - " + schedule.getDateEnd());
        timesTextView.setText(schedule.getTimes());
        descriptionTextView.setText(schedule.getDescription());
        return convertView;
    }
}