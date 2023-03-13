package com.echo.wordsudoku.ui.destinations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.echo.wordsudoku.R;
import com.echo.wordsudoku.models.sudoku.GameResult;
import com.echo.wordsudoku.ui.SettingsViewModel;
import com.echo.wordsudoku.ui.destinations.PuzzleResultFragmentDirections;

public class PuzzleResultFragment extends Fragment {

    private GameResult mGameResult;
    private SettingsViewModel mSettingsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_puzzle_result, container, false);
        mSettingsViewModel = new ViewModelProvider(requireActivity()).get(SettingsViewModel.class);
        Button mainMenuButton = root.findViewById(R.id.main_menu_button);
        Button retryPuzzleButton = root.findViewById(R.id.result_retry_puzzle_button);
        mainMenuButton.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.backToMainMenuAction));

        retryPuzzleButton.setOnClickListener(v -> {
            PuzzleResultFragmentDirections.RetryPuzzleAction action = PuzzleResultFragmentDirections.retryPuzzleAction();
            action.setIsRetry(true);
            Navigation.findNavController(v).navigate(action);
        });

        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String message,timerMessage;
        int imageResource;
        mGameResult = new GameResult(PuzzleResultFragmentArgs.fromBundle(getArguments()).getIsWin(), PuzzleResultFragmentArgs.fromBundle(getArguments()).getMistakes());
        int timer = PuzzleResultFragmentArgs.fromBundle(getArguments()).getTimer();

        if(mGameResult.getResult() == true) {
            message = getString(R.string.msg_win);
            imageResource = R.drawable.success;
        }
        else {
            if (mGameResult.getMistakes() == 1)
                message = getString(R.string.msg_fail)+" " + mGameResult.getMistakes() + " " + getString(R.string.word_mistake_singular);
            else
                message = getString(R.string.msg_fail)+" " + mGameResult.getMistakes() + " " +  getString(R.string.word_mistake_plural);
            imageResource = R.drawable.fail;
        }

        if (timer != -1)
            timerMessage = getString(R.string.msg_puzzle_timer) +" "+ timer+" "+ getString(R.string.word_seconds);
        else {
            timerMessage = "Difficulty: " + getResources().getStringArray(R.array.difficulty_entries)[mSettingsViewModel.getDifficulty()-1];
        }

        TextView puzzleResultTextView = view.findViewById(R.id.puzzle_result_message_text_view);
        TextView puzzleTimerTextView = view.findViewById(R.id.puzzle_result_timer_text_view);
        ImageView resultImageView = view.findViewById(R.id.result_image_view);

        puzzleResultTextView.setText(message);
        puzzleTimerTextView.setText(timerMessage);
        resultImageView.setImageResource(imageResource);
    }
}
