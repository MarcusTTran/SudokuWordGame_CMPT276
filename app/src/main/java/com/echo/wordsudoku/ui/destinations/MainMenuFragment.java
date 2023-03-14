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
import com.echo.wordsudoku.models.language.BoardLanguage;
import com.echo.wordsudoku.models.json.PuzzleJsonReader;
import com.echo.wordsudoku.ui.MainActivity;
import com.echo.wordsudoku.ui.SettingsViewModel;
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

        mNewGameButton = root.findViewById(R.id.new_game_button);
        mNewGameButton.setOnClickListener(v -> {
            navController.navigate(R.id.choosePuzzleModeFragment);
        });

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

        mChangeLanguageButton = root.findViewById(R.id.change_language_button);

        mSettingsViewModel.getPuzzleLanguage().observe(getViewLifecycleOwner(), language -> {
            mSettingsPuzzleLanguage = language;
            String changeLanguageButtonText = "Puzzle Language : " + BoardLanguage.getLanguageName(mSettingsPuzzleLanguage);
            mChangeLanguageButton.setText(changeLanguageButtonText);
        });

        mChangeLanguageButton.setOnClickListener(v -> {
            try {
                mSettingsPuzzleLanguage = BoardLanguage.getOtherLanguage(mSettingsPuzzleLanguage);
                mSettingsViewModel.setPuzzleLanguage(mSettingsPuzzleLanguage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        mSettingsButton = root.findViewById(R.id.settings_button);
        mSettingsButton.setOnClickListener(v -> {
            navController.navigate(R.id.startSettingsAction);
        });

        mChooseCustomWordsButton = root.findViewById(R.id.custom_words_button);
        mChooseCustomWordsButton.setOnClickListener(v -> {
            navController.navigate(R.id.startCustomWordsAction);
        });
        return root;
    }
}
