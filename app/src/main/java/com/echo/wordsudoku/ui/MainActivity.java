package com.echo.wordsudoku.ui;

import static com.echo.wordsudoku.file.FileUtils.inputStreamToString;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.echo.wordsudoku.R;
import com.echo.wordsudoku.exceptions.IllegalDimensionException;
import com.echo.wordsudoku.exceptions.IllegalLanguageException;
import com.echo.wordsudoku.exceptions.IllegalWordPairException;
import com.echo.wordsudoku.exceptions.NegativeNumberException;
import com.echo.wordsudoku.exceptions.TooBigNumberException;
import com.echo.wordsudoku.file.FileUtils;
import com.echo.wordsudoku.models.language.BoardLanguage;
import com.echo.wordsudoku.models.json.PuzzleJsonReader;
import com.echo.wordsudoku.models.sudoku.Puzzle;
import com.echo.wordsudoku.models.json.WordPairJsonReader;
import com.echo.wordsudoku.models.words.WordPair;
import com.echo.wordsudoku.ui.dialogs.ChoosePuzzleSizeFragment;
import com.echo.wordsudoku.ui.dialogs.SaveGameDialog;
import com.echo.wordsudoku.ui.puzzleParts.PuzzleViewModel;
import com.google.android.material.navigation.NavigationView;
import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements SaveGameDialog.SaveGameDialogListener, ChoosePuzzleSizeFragment.OnPuzzleSizeSelectedListener {

    private static final String TAG = "Puzzle.MainActivity";

    private final int MAIN_MENU_ACTION = R.id.backToMainMenuAction;

    private final String wordPairJsonFile = "words.json";

    // The file name to read the json object
    private final String PUZZLE_JSON_SAVE_FILENAME = "wsudoku_puzzle.json";

    // The number of spaces for indentation
    private static final int JSON_OUTPUT_WHITESPACE = 4;

    private PuzzleViewModel mPuzzleViewModel;

    // This is used for accessing the shared preferences associated with this app
    private SharedPreferences mPreferences;

    public SettingsViewModel mSettingsViewModel;

    private int mSettingsPuzzleLanguage;
//    private int mSettingsPuzzleDifficulty;
//    private boolean mSettingsPuzzleTimer;

    private AppBarConfiguration appBarConfiguration;

    private NavController navController;

    private File mPuzzleJsonFile;

    private String[][] latestSavedPuzzle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // This is used to detect all the problems in the app with StrictMode.
        // It is commented out because it is only used for debugging purposes.

        /*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .build());
        }
        */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPuzzleViewModel = new ViewModelProvider(this).get(PuzzleViewModel.class);
        mSettingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        mPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        mPuzzleJsonFile = new File(getFilesDir(),PUZZLE_JSON_SAVE_FILENAME);


        mPuzzleViewModel = new ViewModelProvider(this).get(PuzzleViewModel.class);
        mSettingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        mPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        // This is used to load the settings from the shared preferences and update the settings view model and load the program according to these settings
        loadSettings();
        loadCustomWordPairs();

        // Setting up the game settings saved in the shared preferences
        // Get the puzzle language from the shared preferences
        mSettingsPuzzleLanguage = mPreferences.getInt(getString(R.string.puzzle_language_key), BoardLanguage.ENGLISH);
        mSettingsViewModel.setPuzzleLanguage(mSettingsPuzzleLanguage);

        loadJsonDatabase();

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Hooking the navigation with the drawer and the action bar

        appBarConfiguration =
                new AppBarConfiguration.Builder(R.id.mainMenuFragment).build();
        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }


    private void loadJsonDatabase() {
        // Load the wordpair reader from the json file
        new Thread(() -> {
            try {
                InputStream jsonFile = getAssets().open(wordPairJsonFile);
                mPuzzleViewModel.setWordPairReader(new WordPairJsonReader(inputStreamToString(jsonFile)));
            } catch (IOException e) {
                fatalErrorDialog(getString(R.string.error_load_wordpair_database));
            }
        }).start();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        int currentPage = navController.getCurrentDestination().getId();
        if (currentPage == R.id.puzzleFragment) {
            if(!isGameSaved()) {
                new SaveGameDialog().show(getSupportFragmentManager(), SaveGameDialog.TAG);
            } else {
                mainMenu();
            }
            return true;
        } else {
            return NavigationUI.navigateUp(navController, appBarConfiguration)
                    || super.onSupportNavigateUp();
        }
    }

    private void loadCustomWordPairs() {
        Set<String> englishCustomWordsSet = mPreferences.getStringSet(getString(R.string.custom_words_english_save_key), null);
        Set<String> frenchCustomWordsSet = mPreferences.getStringSet(getString(R.string.custom_words_french_save_key), null);

        if (englishCustomWordsSet != null && frenchCustomWordsSet != null) {
//            Log.d("TESTCOM", "Size of Set<String> " + englishCustomWordsSet.size());
            List<String> englishCustomWordsList = new ArrayList<>(englishCustomWordsSet);
            List<String> frenchCustomWordsList = new ArrayList<>(frenchCustomWordsSet);

            List<WordPair> loadedCustomWords = new ArrayList<>();
            for (int i = 0; i < englishCustomWordsList.size(); i++) {
                loadedCustomWords.add(new WordPair(englishCustomWordsList.get(i), frenchCustomWordsList.get(i)));
            }

            mPuzzleViewModel.setCustomWordPairs(loadedCustomWords);
        }
    }

    private void saveCustomWordsPairs() {
        // Save all of the game settings so when the user opens the app again, the settings are the same
        SharedPreferences.Editor editor = mPreferences.edit();
        List<String> customWordsEnglish = new ArrayList<>();
        List<String> customWordsFrench = new ArrayList<>();
        if (mPuzzleViewModel.getCustomWordPairs() != null) {
            for (int i = 0; i < mPuzzleViewModel.getCustomWordPairs().size(); i++) {
                customWordsEnglish.add(mPuzzleViewModel.getCustomWordPairs().get(i).getEnglish());
                customWordsFrench.add(mPuzzleViewModel.getCustomWordPairs().get(i).getFrench());
            }

            Set<String> englishSet = new HashSet<>(customWordsEnglish);
            Set<String> frenchSet = new HashSet<>(customWordsFrench);


            editor.putStringSet(getString(R.string.custom_words_english_save_key), englishSet);
            editor.putStringSet(getString(R.string.custom_words_french_save_key), frenchSet);

            editor.apply();
        } else {
            editor.putStringSet(getString(R.string.custom_words_english_save_key), null);
            editor.putStringSet(getString(R.string.custom_words_french_save_key), null);
            editor.apply();
        }

    }

    private void loadSettings() {
        // TODO: Load all of the game settings so when the user opens the app again, the settings are the same
        // Load the settings from the shared preferences
        int difficulty = mPreferences.getInt(getString(R.string.puzzle_difficulty_preference_key), 1);
        mSettingsViewModel.setDifficulty(difficulty);

        boolean timer = mPreferences.getBoolean(getString(R.string.puzzle_timer_preference_key), false);
         mSettingsViewModel.setTimer(timer);

        boolean autoSave = mPreferences.getBoolean(getString(R.string.puzzle_autosave_preference_key), false);
        mSettingsViewModel.setAutoSave(autoSave);
    }


    // TODO: Add more settings to be saved
    private void saveSettings() {
        // Save all of the game settings so when the user opens the app again, the settings are the same
        SharedPreferences.Editor editor = mPreferences.edit();
        mSettingsPuzzleLanguage = mSettingsViewModel.getPuzzleLanguage().getValue();

        boolean  mSettingsPuzzleTimer = mSettingsViewModel.isTimer();
        int mSettingsPuzzleDifficulty = mSettingsViewModel.getDifficulty();

        editor.putInt(getString(R.string.puzzle_language_key), mSettingsPuzzleLanguage);
        editor.putInt(getString(R.string.puzzle_difficulty_preference_key), mSettingsPuzzleDifficulty);
        editor.putBoolean(getString(R.string.puzzle_timer_preference_key), mSettingsPuzzleTimer);
        editor.putBoolean(getString(R.string.puzzle_autosave_preference_key), mSettingsViewModel.isAutoSave());
        editor.apply();
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Save settings of app before closing
        saveSettings();
        // Save the puzzle to the json file before app closes
        if(mSettingsViewModel.isAutoSave()) {
            savePuzzle();
        }

        saveCustomWordsPairs();

    }

    public boolean isGameSaved() {
        return Arrays.deepEquals(mPuzzleViewModel.getPuzzleView().getValue(), latestSavedPuzzle);
    }

    public void savePuzzle(){
        new Thread(() -> {
            if(mPuzzleViewModel.isPuzzleNonValid()) return;
            try {
                FileUtils.stringToPrintWriter(new PrintWriter(mPuzzleJsonFile),mPuzzleViewModel.getPuzzleJson().toString(JSON_OUTPUT_WHITESPACE));
                latestSavedPuzzle = mPuzzleViewModel.getPuzzleView().getValue();
            } catch (JSONException | IOException e) {
                fatalErrorDialog(getString(R.string.save_game_error));
            }
        }).start();
    }


    @Override
    public void onSaveDialogYes() {
        savePuzzle();
        mainMenu();
    }

    @Override
    public void onSaveDialogNo() {
        mainMenu();
    }

    @Override
    public void onPuzzleSizeSelected(int size) {
        try {
            mPuzzleViewModel.newPuzzle(size, mSettingsViewModel.getPuzzleLanguage().getValue(),mSettingsViewModel.getDifficulty());
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
    }

    public void loadPuzzle(){
        new Thread(() -> {
            try{
            // Load the puzzle from the file and update the class members
            Puzzle puzzle;
            PuzzleJsonReader puzzleJsonReader = new PuzzleJsonReader(FileUtils.inputStreamToString(new FileInputStream(mPuzzleJsonFile)));
            puzzle = puzzleJsonReader.readPuzzle();
            // Update the PuzzleViewModel
                if (puzzle != null) {
                    mPuzzleViewModel.loadPuzzle(puzzle);
                    latestSavedPuzzle = puzzle.toStringArray();
                }
            } catch (IOException e) {
                // run from main thread
                runOnUiThread(() -> {
                    fatalErrorDialog(getString(R.string.load_game_error));
                });
            }
        }).start();
    }

    public void fatalErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.word_error)
                .setMessage(message)
                .setPositiveButton(R.string.done_btn, (dialog, which) -> {
                    dialog.dismiss();
                    mainMenu();
                })
                .show();
    }

    public void mainMenu() {
        Navigation.findNavController(this, R.id.nav_host_fragment).navigate(MAIN_MENU_ACTION);
    }

    public boolean doesPuzzleSaveFileExist() {
        return mPuzzleJsonFile.exists();
    }
}