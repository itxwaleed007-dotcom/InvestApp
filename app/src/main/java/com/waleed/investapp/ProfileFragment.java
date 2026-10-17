package com.waleed.investapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private static final String[] SETTINGS = {
            "Account details", "Security", "Notifications", "Help & support", "Log out"
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        RowBinder.bindSettings(requireContext(), (LinearLayout) v.findViewById(R.id.settings_list), SETTINGS);
        return v;
    }
}
