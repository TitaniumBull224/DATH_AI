package com.example.internet.ui.profile;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.internet.R;
import com.example.internet.databinding.FragmentNotificationsBinding;
import com.example.internet.databinding.FragmentProfileBinding;
import com.example.internet.databinding.FragmentScheduleBinding;
import com.example.internet.ui.notifications.NotificationsViewModel;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Create a ViewModelProvider.Factory to pass the Application object to the ProfileViewModel constructor
        ViewModelProvider.Factory factory = new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new ProfileViewModel(requireActivity().getApplication());
            }
        };

        // Use the factory to create an instance of the ProfileViewModel class
        ProfileViewModel profileViewModel = new ViewModelProvider(this, factory).get(ProfileViewModel.class);
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        CircleImageView profilePicture = root.findViewById(R.id.profile_picture);
        TextView greetingText = root.findViewById(R.id.greeting_text);

        // Set the profile picture using an image resource
        profilePicture.setImageResource(R.drawable.profile_picture);

        // Retrieve the email from the NavArgs
        String email = getArguments().getString("email");

        // Set the greeting text using the email from the NavArgs
        greetingText.setText("Hello " + email + "!");

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}