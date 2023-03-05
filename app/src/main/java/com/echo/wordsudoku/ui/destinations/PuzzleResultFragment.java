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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.echo.wordsudoku.R;
import com.echo.wordsudoku.models.sudoku.GameResult;

public class PuzzleResultFragment extends Fragment {

    private TextView mResultTextView;
    private ImageView mResultImageView;

    private GameResult mGameResult;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_puzzle_result, container, false);
        Button mainMenuButton = root.findViewById(R.id.main_menu_button);
        Button newGameButton = root.findViewById(R.id.result_new_game_button);
        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.MainMenuAfterPuzzleAction);
            }
        });

        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.NewGameAfterPuzzleAction);
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
            message = "You won!";
            imageResource = R.drawable.success;
        }
        else {
            if (mGameResult.getMistakes() == 1)
                message = "You lost! You made " + mGameResult.getMistakes() + " mistake.";
            else
                message = "You lost! You made " + mGameResult.getMistakes() + " mistakes.";
            imageResource = R.drawable.fail;
        }

        mResultTextView = view.findViewById(R.id.result_text_view);
        mResultImageView = view.findViewById(R.id.result_image_view);

        mResultTextView.setText(message);
        mResultImageView.setImageResource(imageResource);
    }
}
