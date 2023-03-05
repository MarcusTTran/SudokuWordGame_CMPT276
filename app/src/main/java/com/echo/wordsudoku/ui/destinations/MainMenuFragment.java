package com.echo.wordsudoku.ui.destinations;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.echo.wordsudoku.R;
import com.echo.wordsudoku.models.BoardLanguage;

public class MainMenuFragment extends Fragment {


    private static final String LOAD_MAIN_MENU = "com.echo.wordsudoku.load_main_menu";

    // UI references.
    private Button mNewGameButton;
    private Button mLoadGameButton;
    private Button mExitButton;

    private Button mChangeLanguageButton;

    private int mPuzzleLanguage = BoardLanguage.ENGLISH;
    private SharedPreferences mPreferences;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main_menu, container, false);
        mPreferences = getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);


        // This is used to navigate to the different fragments
        NavHostFragment navHostFragment =
                (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        mNewGameButton = root.findViewById(R.id.new_game_button);
        mNewGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.startPuzzleAction);
            }
        });

        mLoadGameButton = root.findViewById(R.id.load_game_button);
        mLoadGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.startPuzzleAction);
            }
        });


        // [EXIT BUTTON]
        // Set up the exit button
        // When the button is clicked, finish the activity

        mExitButton = root.findViewById(R.id.exit_button);
        mExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });



        mChangeLanguageButton = root.findViewById(R.id.change_language_button);
        String changeLanguageButtonText = "Puzzle Language : " + BoardLanguage.getLanguageName(mPuzzleLanguage);
        mChangeLanguageButton.setText(changeLanguageButtonText);
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        mChangeLanguageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mPuzzleLanguage = BoardLanguage.getOtherLanguage(mPuzzleLanguage);
                    preferencesEditor.putInt(getString(R.string.puzzle_language_key), mPuzzleLanguage);
                    preferencesEditor.apply();
                    String changeLanguageButtonText = "Puzzle Language : " + BoardLanguage.getLanguageName(mPuzzleLanguage);
                    mChangeLanguageButton.setText(changeLanguageButtonText);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return root;
    }
}
