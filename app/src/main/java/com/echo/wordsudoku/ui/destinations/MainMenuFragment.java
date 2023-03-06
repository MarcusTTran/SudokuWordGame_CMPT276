package com.echo.wordsudoku.ui.destinations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.echo.wordsudoku.R;
import com.echo.wordsudoku.models.BoardLanguage;
import com.echo.wordsudoku.ui.SettingsViewModel;
import com.echo.wordsudoku.ui.destinations.MainMenuFragmentDirections.StartPuzzleAction;

public class MainMenuFragment extends Fragment {


    private static final String LOAD_MAIN_MENU = "com.echo.wordsudoku.load_main_menu";

    // UI references.
    private Button mNewGameButton;
    private Button mLoadGameButton;
    private Button mExitButton;

    private Button mChangeLanguageButton;

    private int mSettingsPuzzleLanguage;

    private SettingsViewModel mSettingsViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main_menu, container, false);

        mSettingsViewModel = new ViewModelProvider(getActivity()).get(SettingsViewModel.class);


        // This is used to navigate to the different fragments
        NavHostFragment navHostFragment =
                (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        mNewGameButton = root.findViewById(R.id.new_game_button);
        mNewGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartPuzzleAction action = MainMenuFragmentDirections.startPuzzleAction();
                action.setIsNewGame(true);
                navController.navigate(action);
            }
        });

        mLoadGameButton = root.findViewById(R.id.load_game_button);
        mLoadGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartPuzzleAction action = MainMenuFragmentDirections.startPuzzleAction();
                action.setIsNewGame(false);
                navController.navigate(action);
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

        mSettingsViewModel.getPuzzleLanguage().observe(getViewLifecycleOwner(), language -> {
            mSettingsPuzzleLanguage = language;
            String changeLanguageButtonText = "Puzzle Language : " + BoardLanguage.getLanguageName(mSettingsPuzzleLanguage);
            mChangeLanguageButton.setText(changeLanguageButtonText);
        });

        mChangeLanguageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mSettingsPuzzleLanguage = BoardLanguage.getOtherLanguage(mSettingsPuzzleLanguage);
                    mSettingsViewModel.setPuzzleLanguage(mSettingsPuzzleLanguage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return root;
    }
}
