package com.example.internet.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.internet.databinding.FragmentDashboardBinding;

import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private DashboardViewModel dashboardViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        final TextView textTem = binding.txtTem;
        dashboardViewModel.getTem().observe(getViewLifecycleOwner(), textTem::setText);
        final TextView textHum = binding.txtHum;
        dashboardViewModel.getHum().observe(getViewLifecycleOwner(), textHum::setText);
        final TextView textLig = binding.txtLig;
        dashboardViewModel.getLig().observe(getViewLifecycleOwner(), textLig::setText);

        //final LabeledSwitch switchLig = binding.ligBtn;
        //dashboardViewModel.getLigBtn().observe(getViewLifecycleOwner(), switchLig::setText);
        binding.ligBtn.setOnToggledListener((toggleableView, isOn) -> {
            if(isOn) dashboardViewModel.sendDataMQTT("nhanchucqt/feeds/den", "1");
            else dashboardViewModel.sendDataMQTT("nhanchucqt/feeds/den", "0");
        });
        binding.fanBtn.setOnToggledListener((toggleableView, isOn) -> {
            if(isOn) dashboardViewModel.sendDataMQTT("nhanchucqt/feeds/quat", "1");
            else dashboardViewModel.sendDataMQTT("nhanchucqt/feeds/quat", "0");
        });
        dashboardViewModel.startMQTT(getActivity());

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}