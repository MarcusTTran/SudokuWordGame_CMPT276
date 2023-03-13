package com.echo.wordsudoku.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.echo.wordsudoku.R;
import com.echo.wordsudoku.models.BoardLanguage;
import com.echo.wordsudoku.models.Memory.JsonWriter;
import com.echo.wordsudoku.models.sudoku.Puzzle;
import com.echo.wordsudoku.models.words.WordPairReader;
import com.echo.wordsudoku.ui.destinations.ChoosePuzzleModeFragmentDirections;
import com.echo.wordsudoku.ui.dialogs.ChoosePuzzleSizeFragment;
import com.echo.wordsudoku.ui.dialogs.SaveGameDialog;
import com.echo.wordsudoku.ui.puzzleParts.PuzzleViewModel;
import com.google.android.material.navigation.NavigationView;
import org.json.JSONException;
import java.io.IOException;
import java.io.InputStream;

import com.echo.wordsudoku.ui.destinations.ChoosePuzzleModeFragmentDirections;

public class MainActivity extends AppCompatActivity implements SaveGameDialog.SaveGameDialogListener, ChoosePuzzleSizeFragment.OnPuzzleSizeSelectedListener {

    private static final String TAG = "Puzzle.MainActivity";

    private final String wordPairJsonFile = "words.json";
    private PuzzleViewModel mPuzzleViewModel;

    // This is used for accessing the shared preferences associated with this app
    private SharedPreferences mPreferences;

    public SettingsViewModel mSettingsViewModel;

    private int mSettingsPuzzleLanguage;
//    private int mSettingsPuzzleDifficulty;
//    private boolean mSettingsPuzzleTimer;

    private AppBarConfiguration appBarConfiguration;

    private DrawerLayout mDrawerLayout;

    private NavController navController;

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

        new Thread(() -> {
            try {
                InputStream jsonFile = getAssets().open(wordPairJsonFile);
                mPuzzleViewModel.setWordPairReader(new WordPairReader(jsonFile));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();

        mPuzzleViewModel = new ViewModelProvider(this).get(PuzzleViewModel.class);
        mSettingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        mPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        // This is used to load the settings from the shared preferences and update the settings view model and load the program according to these settings
        loadSettings();

        // Setting up the game settings saved in the shared preferences
        // Get the puzzle language from the shared preferences
        mSettingsPuzzleLanguage = mPreferences.getInt(getString(R.string.puzzle_language_key), BoardLanguage.ENGLISH);
        mSettingsViewModel.setPuzzleLanguage(mSettingsPuzzleLanguage);




        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Hooking the navigation with the drawer and the action bar

        appBarConfiguration =
                new AppBarConfiguration.Builder(R.id.mainMenuFragment).build();
        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        int currentPage = navController.getCurrentDestination().getId();
        if (currentPage == R.id.puzzleFragment && !mPuzzleViewModel.isGameSaved()) {
            new SaveGameDialog().show(getSupportFragmentManager(), SaveGameDialog.TAG);
            return true;
        } else {
            return NavigationUI.navigateUp(navController, appBarConfiguration)
                    || super.onSupportNavigateUp();
        }
    }

    private void loadSettings() {
        // TODO: Load all of the game settings so when the user opens the app again, the settings are the same
        // Load the settings from the shared preferences
        int difficulty = mPreferences.getInt(getString(R.string.puzzle_difficulty_preference_key), 1);
        mSettingsViewModel.setDifficulty(difficulty);

        boolean timer = mPreferences.getBoolean(getString(R.string.puzzle_timer_preference_key), false);
         mSettingsViewModel.setTimer(timer);
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
        editor.apply();
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Save settings of app before closing
        saveSettings();
        // Save the puzzle to the json file before app closes
        if(mSettingsViewModel.isAutoSave())
            saveGame();
    }

    public void saveGame(){
        new Thread(() -> {
            JsonWriter jsonWriter = new JsonWriter(MainActivity.this);
            Puzzle puzzle = mPuzzleViewModel.getPuzzle();
            if(puzzle == null) return;
            try {
                jsonWriter.writePuzzle(puzzle);
                mPuzzleViewModel.setGameSaved(true);
            } catch (JSONException | IOException e) {
                Toast.makeText(MainActivity.this,R.string.save_game_error , Toast.LENGTH_SHORT).show();
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        if (getOnBackPressedDispatcher().hasEnabledCallbacks()){
            super.onBackPressed();
        } else {
            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }
    }


    @Override
    public void onSaveGame() {
        saveGame();
        navController.navigate(R.id.backToMainMenuAction);
    }

    @Override
    public void onNotSaveGame() {
        navController.navigate(R.id.backToMainMenuAction);
    }

    @Override
    public void onPuzzleSizeSelected(int size) {
        ChoosePuzzleModeFragmentDirections.StartPuzzleModeAction action = ChoosePuzzleModeFragmentDirections.startPuzzleModeAction();
        action.setPuzzleSize(size);
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        navController.navigate(action);
    }
}