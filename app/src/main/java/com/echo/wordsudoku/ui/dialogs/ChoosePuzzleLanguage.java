package com.echo.wordsudoku.ui.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.echo.wordsudoku.R;
import com.echo.wordsudoku.ui.SettingsViewModel;

public class ChoosePuzzleLanguage extends DialogFragment {

    public static final String TAG = "ChoosePuzzleLanguage";
    SettingsViewModel settingsViewModel;
    Spinner puzzle_language_spinner,button_input_language_spinner;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        settingsViewModel = new ViewModelProvider(getActivity()).get(SettingsViewModel.class);
        View v = inflater.inflate(R.layout.fragment_choose_puzzle_language, container, false);
        puzzle_language_spinner = v.findViewById(R.id.puzzle_language_spinner);
        button_input_language_spinner = v.findViewById(R.id.button_input_language_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.languages, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        puzzle_language_spinner.setAdapter(adapter);
        button_input_language_spinner.setAdapter(adapter);
        Button submit = v.findViewById(R.id.set_languages_button);
        submit.setOnClickListener(v1 -> {
            int puzzle_language = puzzle_language_spinner.getSelectedItemPosition(), button_input_language = button_input_language_spinner.getSelectedItemPosition();
            if (puzzle_language == button_input_language || puzzle_language == Spinner.INVALID_POSITION || button_input_language == Spinner.INVALID_POSITION) {
                Toast.makeText(getContext(), getString(R.string.error_two_languages_same), Toast.LENGTH_SHORT).show();
            } else {
                settingsViewModel.setPuzzleLanguage(puzzle_language);
                settingsViewModel.setButtonInputLanguage(button_input_language);
                dismiss();
            }
        });
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Integer puzzleLanguage = settingsViewModel.getPuzzleLanguage().getValue(),inputLanguage = settingsViewModel.getButtonInputLanguage().getValue();
        if(puzzleLanguage != null)
            puzzle_language_spinner.setSelection(settingsViewModel.getPuzzleLanguage().getValue());
        if(inputLanguage != null)
            button_input_language_spinner.setSelection(settingsViewModel.getButtonInputLanguage().getValue());
    }
}
