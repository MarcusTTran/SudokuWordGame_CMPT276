/*
* Description: This fragment is the top menu bar that contains the timer, the help button, the dictionary button, and the done button.
* It is used in the PuzzleFragment.
* */

package com.echo.wordsudoku.ui.puzzleParts;

import static com.echo.wordsudoku.models.utility.StringUtility.secondsToTimerLabel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.echo.wordsudoku.R;
import com.echo.wordsudoku.exceptions.NegativeNumberException;
import com.echo.wordsudoku.ui.SettingsViewModel;
import com.echo.wordsudoku.ui.destinations.PuzzleFragment;
import java.util.Timer;
import java.util.TimerTask;

public class PuzzleTopMenuBarFragment extends Fragment {

    private int timer = 0;

    private PuzzleViewModel mPuzzleViewModel;
    private SettingsViewModel mSettingsViewModel;
    private boolean pauseTimer = false;

    private boolean isGameTimed = false;

    private TextView timerLabel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_puzzle_top_menu_bar, container, false);
        mPuzzleViewModel = new ViewModelProvider(requireActivity()).get(PuzzleViewModel.class);
        mSettingsViewModel = new ViewModelProvider(requireActivity()).get(SettingsViewModel.class);

        isGameTimed = mSettingsViewModel.isTimer();

        // The difficulty levels that are displayed in the timer label if the game is not timed otherwise the timer in seconds is displayed
        String[] difficultyLevels = getResources().getStringArray(R.array.difficulty_entries);
        // The label that displays the timer
        timerLabel = view.findViewById(R.id.timer_text_view);
        timerLabel.setText(difficultyLevels[mSettingsViewModel.getDifficulty()-1]);

        // The button that is used to finish the puzzle
        Button doneButton = view.findViewById(R.id.options_finish_button);
        doneButton.setOnClickListener(v -> {
            PuzzleFragment puzzleFragment = (PuzzleFragment) getParentFragment();
            puzzleFragment.finishButtonPressed();
        });

        // The button that is used to display the rules of the game
        ImageButton helpButton = view.findViewById(R.id.help_button);
        helpButton.setOnClickListener(v -> {
            PuzzleFragment puzzleFragment = (PuzzleFragment) getParentFragment();
            puzzleFragment.rulesButtonPressed();
        });

        // The button that is used to display the dictionary
        ImageButton dictionaryButton = view.findViewById(R.id.options_dictionary_help_button);
        dictionaryButton.setOnClickListener(v -> {
            PuzzleFragment puzzleFragment = (PuzzleFragment) getParentFragment();
            puzzleFragment.dictionaryButtonPressed();
        });

        // The button that is used to reset the puzzle
        Button restartButton = view.findViewById(R.id.options_reset_puzzle_button);
        restartButton.setOnClickListener(v -> {
            PuzzleFragment puzzleFragment = (PuzzleFragment) getParentFragment();
            puzzleFragment.resetGame();
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        timerLabel.setTextSize(20);
        if(isGameTimed) {
        this.timer = mPuzzleViewModel.getPuzzleTimer();
            startTiming();
            mPuzzleViewModel.getTimer().observe(getViewLifecycleOwner(), timer -> {
                timerLabel.setText(secondsToTimerLabel(timer));
            });
        }
    }

    // This method is used to start the timer which runs as a background thread and updates the timer label every second
    public void startTiming() {
        Timer timerThread = new Timer();
        timerThread.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(pauseTimer) {
                    return;
                }
                try {
                    mPuzzleViewModel.postTimer(timer);
                } catch (NegativeNumberException e) {
                    throw new RuntimeException(e);
                }
                timer++;
            }
        }, 0, 1000);
    }

    // These methods are used to pause and resume the timer when the app is paused and resumed (so that the timer does not keep running in the background)
    @Override
    public void onPause() {
        super.onPause();
        pauseTimer = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        pauseTimer = false;
    }
}
