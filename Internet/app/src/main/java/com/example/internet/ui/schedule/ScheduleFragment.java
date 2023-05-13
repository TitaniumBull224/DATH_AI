package com.example.internet.ui.schedule;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.internet.R;
import com.example.internet.databinding.FragmentNotificationsBinding;
import com.example.internet.databinding.FragmentScheduleBinding;
import com.example.internet.ui.notifications.NotificationsViewModel;

import java.util.ArrayList;

public class ScheduleFragment extends Fragment {

    private FragmentScheduleBinding binding;
    private ScheduleViewModel scheduleViewModel;
    private ScheduleAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        scheduleViewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);

        binding = FragmentScheduleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        adapter = new ScheduleAdapter(scheduleViewModel, new ArrayList<>());
        binding.schedulesListView.setAdapter(adapter);

        scheduleViewModel.getSchedules().observe(getViewLifecycleOwner(), schedules -> {
            adapter.setSchedules(schedules);
            adapter.notifyDataSetChanged();
        });

        binding.createButton.setOnClickListener(v -> {
            String name = binding.nameEditText.getText().toString();
            String dateBegin = binding.dateBeginEditText.getText().toString();
            String dateEnd = binding.dateEndEditText.getText().toString();
            String times = binding.timesEditText.getText().toString();
            String description = binding.descriptionEditText.getText().toString();
            Schedule schedule = new Schedule(0, name, dateBegin, dateEnd, times, description);
            scheduleViewModel.createSchedule(schedule);
            binding.nameEditText.setText("");
            binding.dateBeginEditText.setText("");
            binding.dateEndEditText.setText("");
            binding.timesEditText.setText("");
            binding.descriptionEditText.setText("");
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}