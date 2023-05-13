package com.example.internet.ui.schedule;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.internet.R;

import java.util.List;

public class ScheduleAdapter extends BaseAdapter {
    private List<Schedule> schedules;
    private ScheduleViewModel scheduleViewModel;
    public ScheduleAdapter(ScheduleViewModel scheduleViewModel, List<Schedule> schedules) {
        this.scheduleViewModel = scheduleViewModel;
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
        datesTextView.setText(String.format("%s - %s", schedule.getDateBegin(), schedule.getDateEnd()));
        timesTextView.setText(schedule.getTimes());
        descriptionTextView.setText(schedule.getDescription());
        Button deleteButton = convertView.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(v -> {
            schedules.remove(schedule);
            notifyDataSetChanged();
            scheduleViewModel.deleteSchedule(schedule);
        });
        Button updateButton = convertView.findViewById(R.id.updateButton);
        updateButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.update_schedule, null);
            builder.setView(view);

            EditText updateNameEditText = view.findViewById(R.id.updateNameEditText);
            EditText updateDateBeginEditText = view.findViewById(R.id.updateDateBeginEditText);
            EditText updateDateEndEditText = view.findViewById(R.id.updateDateEndEditText);
            EditText updateTimesEditText = view.findViewById(R.id.updateTimesEditText);
            EditText updateDescriptionEditText = view.findViewById(R.id.updateDescriptionEditText);

            updateNameEditText.setText(schedule.getName());
            updateDateBeginEditText.setText(schedule.getDateBegin());
            updateDateEndEditText.setText(schedule.getDateEnd());
            updateTimesEditText.setText(schedule.getTimes());
            updateDescriptionEditText.setText(schedule.getDescription());

            Button submitUpdateButton = view.findViewById(R.id.submitUpdateButton);
            submitUpdateButton.setOnClickListener(v1 -> {
                String name = updateNameEditText.getText().toString();
                String dateBegin = updateDateBeginEditText.getText().toString();
                String dateEnd = updateDateEndEditText.getText().toString();
                String times = updateTimesEditText.getText().toString();
                String description = updateDescriptionEditText.getText().toString();

                Schedule updatedSchedule = new Schedule(schedule.getId(), name, dateBegin, dateEnd, times, description);

                scheduleViewModel.updateSchedule(updatedSchedule);
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        });
        return convertView;
    }
}