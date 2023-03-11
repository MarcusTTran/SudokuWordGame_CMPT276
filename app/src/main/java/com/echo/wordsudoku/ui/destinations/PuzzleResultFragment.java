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
import androidx.navigation.Navigation;

import com.echo.wordsudoku.R;
import com.echo.wordsudoku.models.sudoku.GameResult;
import com.echo.wordsudoku.ui.destinations.PuzzleResultFragmentDirections.RetryPuzzleAction;

public class PuzzleResultFragment extends Fragment {

    private TextView mResultTextView;
    private ImageView mResultImageView;

    private GameResult mGameResult;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_puzzle_result, container, false);
        Button mainMenuButton = root.findViewById(R.id.main_menu_button);
        Button retryPuzzleButton = root.findViewById(R.id.result_retry_puzzle_button);
        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.MainMenuAfterPuzzleAction);
            }
        });

        retryPuzzleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetryPuzzleAction action = PuzzleResultFragmentDirections.retryPuzzleAction();
                action.setIsRetry(true);
                Navigation.findNavController(v).navigate(action);
            }
        });

        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String message;
        int imageResource;
        mGameResult = new GameResult(PuzzleResultFragmentArgs.fromBundle(getArguments()).getIsWin(), PuzzleResultFragmentArgs.fromBundle(getArguments()).getMistakes());

        if(mGameResult.getResult() == true) {
            message = getString(R.string.msg_win);
            imageResource = R.drawable.success;
        }
        else {
            if (mGameResult.getMistakes() == 1)
                message = getString(R.string.msg_fail) + mGameResult.getMistakes() + getString(R.string.word_mistake_singular);
            else
                message = getString(R.string.msg_fail) + mGameResult.getMistakes() + getString(R.string.word_mistake_plural);
            imageResource = R.drawable.fail;
        }

        mResultTextView = view.findViewById(R.id.result_text_view);
        mResultImageView = view.findViewById(R.id.result_image_view);

        mResultTextView.setText(message);
        mResultImageView.setImageResource(imageResource);
    }
}
