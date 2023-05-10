package com.example.internet.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.internet.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView resultTextView = binding.resultTextView;
        homeViewModel.getResult().observe(getViewLifecycleOwner(), resultTextView::setText);
        binding.button0.setOnClickListener(v -> homeViewModel.appendNumber("0"));
        binding.button1.setOnClickListener(v -> homeViewModel.appendNumber("1"));
        binding.button2.setOnClickListener(v -> homeViewModel.appendNumber("2"));
        binding.button3.setOnClickListener(v -> homeViewModel.appendNumber("3"));
        binding.button4.setOnClickListener(v -> homeViewModel.appendNumber("4"));
        binding.button5.setOnClickListener(v -> homeViewModel.appendNumber("5"));
        binding.button6.setOnClickListener(v -> homeViewModel.appendNumber("6"));
        binding.button7.setOnClickListener(v -> homeViewModel.appendNumber("7"));
        binding.button8.setOnClickListener(v -> homeViewModel.appendNumber("8"));
        binding.button9.setOnClickListener(v -> homeViewModel.appendNumber("9"));
        binding.buttonAdd.setOnClickListener(v -> homeViewModel.setOperator("+"));
        binding.buttonSubtract.setOnClickListener(v -> homeViewModel.setOperator("-"));
        binding.buttonMultiply.setOnClickListener(v -> homeViewModel.setOperator("*"));
        binding.buttonDivide.setOnClickListener(v -> homeViewModel.setOperator("/"));
        binding.buttonEquals.setOnClickListener(v -> homeViewModel.calculateResult());
        binding.buttonClear.setOnClickListener(v -> homeViewModel.clear());

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}