package com.echo.wordsudoku.ui.destinations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.echo.wordsudoku.R;
import com.echo.wordsudoku.exceptions.IllegalDimensionException;
import com.echo.wordsudoku.exceptions.IllegalLanguageException;
import com.echo.wordsudoku.exceptions.IllegalWordPairException;
import com.echo.wordsudoku.exceptions.NegativeNumberException;
import com.echo.wordsudoku.exceptions.TooBigNumberException;
import com.echo.wordsudoku.ui.SettingsViewModel;
import com.echo.wordsudoku.ui.dialogs.ChoosePuzzleSizeFragment;
import com.echo.wordsudoku.ui.puzzleParts.PuzzleViewModel;

import org.json.JSONException;


public class ChoosePuzzleModeFragment extends Fragment {


    private static final String TAG = "ChoosePuzzleModeFragment";

    private final int CLASS_PUZZLE_SIZE = 9;
    private PuzzleViewModel mPuzzleViewModel;
    private SettingsViewModel mSettingsViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_choose_puzzle_mode, container, false);
        mPuzzleViewModel = new ViewModelProvider(requireActivity()).get(PuzzleViewModel.class);
        mSettingsViewModel = new ViewModelProvider(requireActivity()).get(SettingsViewModel.class);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int language = mSettingsViewModel.getPuzzleLanguage().getValue(), difficulty = mSettingsViewModel.getDifficulty();

        Button classicButton = view.findViewById(R.id.classic_puzzle_button);
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        classicButton.setOnClickListener(v -> {
            try {
                mPuzzleViewModel.newPuzzle(CLASS_PUZZLE_SIZE,language, difficulty);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            } catch (IllegalLanguageException e) {
                throw new RuntimeException(e);
            } catch (TooBigNumberException e) {
                throw new RuntimeException(e);
            } catch (NegativeNumberException e) {
                throw new RuntimeException(e);
            } catch (IllegalWordPairException e) {
                throw new RuntimeException(e);
            } catch (IllegalDimensionException e) {
                throw new RuntimeException(e);
            }
            navController.navigate(R.id.startPuzzleModeAction);
        });


        Button customButton = view.findViewById(R.id.custom_size_button);

        customButton.setOnClickListener(v -> {
                    new ChoosePuzzleSizeFragment().show(getChildFragmentManager(), ChoosePuzzleSizeFragment.TAG);
        });


        Button customWords = view.findViewById(R.id.custom_words_button);

        customWords.setOnClickListener(v -> {
            // If user has not entered custom words, navigate to custom words fragment
            if(!mPuzzleViewModel.hasSetCustomWordPairs()){
                navController.navigate(R.id.chooseCustomWordsFragment);
                Toast.makeText(getContext(), R.string.error_enter_custom_words, Toast.LENGTH_SHORT).show();
            } else {
                try {
                    mPuzzleViewModel.newCustomPuzzle(language,difficulty);
                } catch (IllegalLanguageException e) {
                    throw new RuntimeException(e);
                } catch (TooBigNumberException e) {
                    throw new RuntimeException(e);
                } catch (NegativeNumberException e) {
                    throw new RuntimeException(e);
                } catch (IllegalWordPairException e) {
                    throw new RuntimeException(e);
                } catch (IllegalDimensionException e) {
                    throw new RuntimeException(e);
                }
                navController.navigate(R.id.startPuzzleModeAction);
            }
        });

    }

}
