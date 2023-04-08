/*
* Description: This is the main menu fragment. It is the first fragment that is loaded when the app is opened.
* It contains buttons that allow the user to start a new game, load a saved game, change the language, tweak the settings, enter their own custom words for puzzles and exit the app.
* */

package com.echo.wordsudoku.ui.destinations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.echo.wordsudoku.R;
import com.echo.wordsudoku.exceptions.IllegalLanguageException;
import com.echo.wordsudoku.models.language.BoardLanguage;
import com.echo.wordsudoku.models.json.PuzzleJsonReader;
import com.echo.wordsudoku.ui.MainActivity;
import com.echo.wordsudoku.ui.SettingsViewModel;
import com.echo.wordsudoku.ui.dialogs.ChoosePuzzleLanguage;
import com.echo.wordsudoku.ui.puzzleParts.PuzzleViewModel;

public class MainMenuFragment extends Fragment {


    private static final String LOAD_MAIN_MENU = "com.echo.wordsudoku.load_main_menu";

    private final int LOAD_PUZZLE_ACTION = R.id.loadPuzzleAction;

    // UI references.
    private Button mNewGameButton;
    private Button mLoadGameButton;
    private Button mExitButton;

    private Button mSettingsButton;

    private Button mChooseCustomWordsButton;

    private Button mChangeLanguageButton;

    private int mSettingsPuzzleLanguage;

    private SettingsViewModel mSettingsViewModel;
    private PuzzleViewModel mPuzzleViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main_menu, container, false);

        mSettingsViewModel = new ViewModelProvider(getActivity()).get(SettingsViewModel.class);
        mPuzzleViewModel = new ViewModelProvider(getActivity()).get(PuzzleViewModel.class);


        // This is used to navigate to the different fragments
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);


        // if user pressed new game button, then load the choose puzzle mode fragment where they can choose the puzzle mode they wish to play
        mNewGameButton = root.findViewById(R.id.new_game_button);
        mNewGameButton.setOnClickListener(v -> {
            Integer inputLanguage = mSettingsViewModel.getButtonInputLanguage().getValue();
            Integer puzzleLanguage = mSettingsViewModel.getPuzzleLanguage().getValue();
            if (inputLanguage == null || puzzleLanguage == null) {
                Toast.makeText(getContext(), "Please set the puzzle language", Toast.LENGTH_SHORT).show();
                return;
            }
            navController.navigate(R.id.choosePuzzleModeFragment);
        });

        // if user pressed load game button, then load the load puzzle fragment where they can load a saved puzzle
        mLoadGameButton = root.findViewById(R.id.load_game_button);
        mLoadGameButton.setOnClickListener(v -> {
            if (!((MainActivity)requireActivity()).doesPuzzleSaveFileExist()) {
                Toast.makeText(getContext(), "No saved game found", Toast.LENGTH_SHORT).show();
                return;
            }
            ((MainActivity)requireActivity()).loadPuzzle();
            navController.navigate(LOAD_PUZZLE_ACTION);
        });


        // [EXIT BUTTON]
        // Set up the exit button
        // When the button is clicked, finish the activity

        mExitButton = root.findViewById(R.id.exit_button);
        mExitButton.setOnClickListener(v -> getActivity().finish());


        // The puzzle change language button
        mChangeLanguageButton = root.findViewById(R.id.change_language_button);

        mSettingsViewModel.getPuzzleLanguage().observe(getViewLifecycleOwner(), language -> {
            String changeLanguageButtonText = getString(R.string.no_languages_set);
            Integer inputLanguage = mSettingsViewModel.getButtonInputLanguage().getValue();
            if(!(language == null || inputLanguage ==null) && language!=inputLanguage){
                try {
                    changeLanguageButtonText = "( " + BoardLanguage.getLanguageName(language) + " - "+BoardLanguage.getLanguageName(inputLanguage)
                            +" )";
                } catch (IllegalLanguageException e) {
                    throw new RuntimeException(e);
                }
            }
            mChangeLanguageButton.setText(changeLanguageButtonText);
        });

        mChangeLanguageButton.setOnClickListener(v -> {
            new ChoosePuzzleLanguage().show(getChildFragmentManager(), ChoosePuzzleLanguage.TAG);
//            try {
//                mSettingsPuzzleLanguage = BoardLanguage.getOtherLanguage(mSettingsPuzzleLanguage);
//                mSettingsViewModel.setPuzzleLanguage(mSettingsPuzzleLanguage);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        });

        // The settings button
        mSettingsButton = root.findViewById(R.id.settings_button);
        mSettingsButton.setOnClickListener(v -> {
            navController.navigate(R.id.startSettingsAction);
        });

        // The choose custom words button
        mChooseCustomWordsButton = root.findViewById(R.id.custom_words_button);
        mChooseCustomWordsButton.setOnClickListener(v -> {
            navController.navigate(R.id.startCustomWordsAction);
        });
        return root;
    }
}
