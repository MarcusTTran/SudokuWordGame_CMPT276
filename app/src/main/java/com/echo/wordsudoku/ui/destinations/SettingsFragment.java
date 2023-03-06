package com.echo.wordsudoku.ui.destinations;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.echo.wordsudoku.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}