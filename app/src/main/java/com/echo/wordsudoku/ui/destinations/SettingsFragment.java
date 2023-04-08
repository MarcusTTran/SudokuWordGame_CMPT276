/*
* Description: This class is the fragment that is used to display the settings page
* It contains a list of settings that the user can change, such as the app language, the level of difficulty.
*  */

package com.echo.wordsudoku.ui.destinations;

import android.app.LocaleManager;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.echo.wordsudoku.R;
import com.echo.wordsudoku.ui.SettingsViewModel;

import java.util.Locale;

public class SettingsFragment extends PreferenceFragmentCompat {

    private SettingsViewModel settingsViewModel;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        //Store the SettingsViewModel; This is where we will store our changes
        settingsViewModel = new ViewModelProvider(requireActivity()).get(SettingsViewModel.class);

        SwitchPreferenceCompat preferenceAutoSave = findPreference("autoSave");
        preferenceAutoSave.setChecked(settingsViewModel.isAutoSave());

        preferenceAutoSave.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object autoSave) {
                settingsViewModel.setAutoSave((boolean) autoSave);
                return true;
            }
        });

        SwitchPreferenceCompat preferenceTimer = findPreference("timer");
        preferenceTimer.setChecked(settingsViewModel.isTimer());

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


        ListPreference preferenceDifficulty = findPreference("difficulty");
        preferenceDifficulty.setValueIndex(settingsViewModel.getDifficulty()-1);
        preferenceDifficulty.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object difficulty) {
                settingsViewModel.setDifficulty(Integer.parseInt(difficulty.toString()));
//                Log.d("MYTEST", "Difficulty changed to: " + settingsViewModel.getDifficulty());
                return true;
            }
        });

        SwitchPreferenceCompat preferenceUiImmersion = findPreference("uiImmersion");
        LocaleListCompat currentLocale = AppCompatDelegate.getApplicationLocales();
        LocaleListCompat english = LocaleListCompat.create(new Locale("en")), french = LocaleListCompat.create(new Locale("fr"));
        preferenceUiImmersion.setChecked(false);

        preferenceUiImmersion.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object uiImmersion) {
                boolean changeLanguage = (boolean) uiImmersion;
                if (currentLocale.equals(english)) {
                    if (changeLanguage) {
                        Toast.makeText(getContext(), getString(R.string.msg_language_french), Toast.LENGTH_SHORT).show();
                        AppCompatDelegate.setApplicationLocales(french);
                    }
                }
                else {
                    if (changeLanguage) {
                        Toast.makeText(getContext(), getString(R.string.msg_language_english), Toast.LENGTH_SHORT).show();
                        AppCompatDelegate.setApplicationLocales(english);
                    }
                }
                return true;
            }
        });


        SwitchPreferenceCompat textToSpeech = findPreference("textToSpeech");
        textToSpeech.setChecked(settingsViewModel.getTextToSpeech());

        textToSpeech.setOnPreferenceChangeListener((preference, textToSpeech1) -> {
            settingsViewModel.setTextToSpeech((boolean) textToSpeech1);
            return true;
        });
    }
}