package com.echo.wordsudoku.ui.destinations;

import android.os.Bundle;
import android.util.Log;
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

        //Set a listener for each preference
        preferenceTimer.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object timer) {
                //Call the settingsViewModel and change the value of fields
                settingsViewModel.setTimer((boolean) timer);
//                Log.d("MYTEST", "Timer changed to: " + settingsViewModel.isTimer());
                return true;
            }
        });


        Preference preferenceDifficulty = findPreference("difficulty");
        preferenceDifficulty.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object difficulty) {
                settingsViewModel.setDifficulty(Integer.parseInt(difficulty.toString()));
//                Log.d("MYTEST", "Difficulty changed to: " + settingsViewModel.getDifficulty());
                return true;
            }
        });

        Preference preferenceUiImmersion = findPreference("uiImmersion");
        preferenceUiImmersion.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object uiImmersion) {
                settingsViewModel.setUiImmersion((boolean) uiImmersion);
//                Log.d("MYTEST", "uiImmersion changed to: " + settingsViewModel.isUiImmersion());
                return true;
            }
        });
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Inflate view
        View view1 = super.onCreateView(inflater, container, savedInstanceState);

        //Store the SettingsViewModel; This is where we will store our changes
        settingsViewModel = new ViewModelProvider(requireActivity()).get(SettingsViewModel.class);

        return view1;
    }
}