package com.echo.wordsudoku.ui.puzzleParts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.echo.wordsudoku.R;
import com.echo.wordsudoku.ui.SettingsViewModel;
import com.echo.wordsudoku.ui.destinations.PuzzleFragment;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class PuzzleTopMenuBarFragment extends Fragment {

    private int timer = 0;

    private PuzzleViewModel mPuzzleViewModel;
    private SettingsViewModel mSettingsViewModel;
    private boolean pauseTimer = false;

    private TextView timerLabel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_puzzle_top_menu_bar, container, false);
        mPuzzleViewModel = new ViewModelProvider(requireActivity()).get(PuzzleViewModel.class);
        mSettingsViewModel = new ViewModelProvider(requireActivity()).get(SettingsViewModel.class);


        String[] difficultyLevels = getResources().getStringArray(R.array.difficulty_entries);
        // The label that displays the timer
        timerLabel = view.findViewById(R.id.timer_text_view);
        timerLabel.setText(difficultyLevels[mSettingsViewModel.getDifficulty()-1]);

        Button doneButton = view.findViewById(R.id.options_finish_button);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PuzzleFragment puzzleFragment = (PuzzleFragment) getParentFragment();
                puzzleFragment.finishButtonPressed();
            }
        });
        ImageButton helpButton = view.findViewById(R.id.help_button);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PuzzleFragment puzzleFragment = (PuzzleFragment) getParentFragment();
                puzzleFragment.rulesButtonPressed();
            }
        });
        ImageButton dictionaryButton = view.findViewById(R.id.options_dictionary_help_button);
        dictionaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PuzzleFragment puzzleFragment = (PuzzleFragment) getParentFragment();
                puzzleFragment.dictionaryButtonPressed();
            }
        });

        Button restartButton = view.findViewById(R.id.options_reset_puzzle_button);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PuzzleFragment puzzleFragment = (PuzzleFragment) getParentFragment();
                puzzleFragment.resetGame(false);
            }
        });
        return view;
    }

    public void startTiming() {
        timer = mPuzzleViewModel.getPuzzle().getTimer();
        timerLabel.setTextSize(20);
        timerLabel.setText(secondsToTimerLabel(timer));
        Timer timerThread = new Timer();
        timerThread.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(pauseTimer) {
                    return;
                }
                timerLabel.post(() -> timerLabel.setText(secondsToTimerLabel(timer)));
                mPuzzleViewModel.getPuzzle().setTimer(timer);
                timer++;
            }
        }, 0, 1000);
    }

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

    @Override
    public void onStop() {
        super.onStop();
    }

    private String  secondsToTimerLabel(int timer) {
        int minutes = timer / 60;
        int seconds = timer % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
    }
}
