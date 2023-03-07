package com.echo.wordsudoku.ui.destinations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.echo.wordsudoku.R;
import com.echo.wordsudoku.ui.SettingsViewModel;

public class SettingsFragment extends PreferenceFragmentCompat {

    private SettingsViewModel settingsViewModel;

    public SettingsFragment() {
        // Do nothing
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        Preference preferenceTimer = findPreference("timer");
        preferenceTimer.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
                settingsViewModel.setTimer(true);
                return true;
            }
        });

        Preference preferenceUiImmersion = findPreference("uiImmersion");
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Inflate view
        View view1 = super.onCreateView(inflater, container, savedInstanceState);

        //Store ViewModel
        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        return view1;
    }
}