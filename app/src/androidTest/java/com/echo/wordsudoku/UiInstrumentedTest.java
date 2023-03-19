package com.echo.wordsudoku;

import static android.os.SystemClock.sleep;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiScrollable;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;
import androidx.test.uiautomator.By;
import android.content.Intent;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

//For more information: https://developer.android.com/training/testing/other-components/ui-automator

@RunWith(AndroidJUnit4.class)
public class UiInstrumentedTest {

    //Warning: These tests take up to X time to complete

    int[] puzzleSizes = new int[] {4, 6, 9, 12};

    private UiDevice ourDevice;
    final String INSTRUTEST = "INSTRU_TESTING";

    //If set up exceeds time, will fail test
    private static final int TIMEOUT_TIME = 5000;

    private static final String WORDSUDOKU_PACKAGE = "com.echo.wordsudoku";

    //Test brings us to home page then starts up
    @Before
    public void testSetUp() {
        ourDevice = UiDevice.getInstance(getInstrumentation());
        ourDevice.pressHome();

        final String appLauncher = ourDevice.getLauncherPackageName();
        if (appLauncher.equals(null)) {
            //If app launcher does not exist fail the test
            fail("App launcher does not exist");
        }

        ourDevice.wait(Until.hasObject(By.pkg(appLauncher).depth(0)), TIMEOUT_TIME);
        //Get the context to launch our app
        Context context = ApplicationProvider.getApplicationContext();
        final Intent appIntent = context.getPackageManager().getLaunchIntentForPackage(WORDSUDOKU_PACKAGE);
        appIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //Start the app up
        context.startActivity(appIntent);
        //Wait until device has app
        ourDevice.wait(Until.hasObject(By.pkg(WORDSUDOKU_PACKAGE).depth(0)), TIMEOUT_TIME);
    }

    //Test that clicking the rules button in puzzle page displays the rules pops up and displays instructions
//    @Ignore("Working test")
    @Test
    public void testRulesPopUpDialog() {
        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("New Game Button not found");
        }

        //Start classic puzzle
        UiObject classicPuzzleButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/classic_puzzle_button").className("android.widget.Button"));
        try {
            classicPuzzleButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Classic Puzzles Button not found");
        }

        UiObject rulesButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/help_button"));
        try {
            rulesButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Rules Button not found");
        }

        UiObject rulesInfoText = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/PopUp_Rules"));
        if (!rulesInfoText.exists()) {
            fail("Rules dialog properly open");
        }

        UiObject rulesInfo = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/RulesInformation"));
        if (!rulesInfo.exists()) {
            fail("Rules are not being displayed");
        }


    }


    //Test that the rules dialog exit button correctly closes rules dialog
//    @Ignore("Working test")
    @Test
    public void testRulesDialogExitButton() {
        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("New Game Button not found");
        }

        //Start classic puzzle
        UiObject classicPuzzleButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/classic_puzzle_button").className("android.widget.Button"));
        try {
            classicPuzzleButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Classic Puzzles Button not found");
        }

        UiObject rulesButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/help_button"));
        try {
            rulesButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Rules Button not found");
        }

        UiObject rulesExitButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/rulesExitButton"));
        try {
            rulesExitButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Rules button does not exist");
        }

        UiObject rulesInfoText = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/PopUp_Rules"));
        if (rulesInfoText.exists()) {
            fail("Rules dialog did not properly close");
        }

    }


    //Test that dictionary peeks are limited to 2 per game; correct dialog appears after 2 peeks
//    @Ignore("Working test")
    @Test
    public void testDictionaryPeeksLimitDialog() {
        //Start new game
        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }

        //Start classic puzzle
        UiObject classicPuzzleButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/classic_puzzle_button").className("android.widget.Button"));
        try {
            classicPuzzleButton.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }

        UiObject dictionaryButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/options_dictionary_help_button").className("android.widget.ImageButton"));

        for (int i = 0 ; i < 2; i++) {
            try {
                dictionaryButton.click();
            } catch (UiObjectNotFoundException e) {
                fail("Dictionary button not found");
            }
            UiObject dictionaryExitButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/dictionaryExitButton").className("android.widget.ImageButton"));
            try {
                dictionaryExitButton.click();
            } catch (UiObjectNotFoundException e) {
                fail("Dictionary exit button not found");
            }
        }

        UiObject dictionaryFinalButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/options_dictionary_help_button").className("android.widget.ImageButton"));
        try {
            dictionaryFinalButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Dictionary button not found");
        }

        UiObject dictionaryLimitDialogPopUp = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/DictionaryLimit").className("android.widget.TextView"));
        if (!dictionaryLimitDialogPopUp.exists()) {
            fail("Dictionary limit failed to appear even after 2 peeks");
        }
    }


    //Test that clicking the Custom Words button on the Choose Puzzle Mode with no custom words
    //entered, correctly navigates the user to the Choose Custom Words page
//    @Ignore("Working test")
    @Test
    public void testCustomWordsNoEnteredCustomWordsNavigation() {
        //Start new game
        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("New Game button was not displayed");
        }

        //Start custom words puzzle
        UiObject customWordsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_words_button").className("android.widget.Button"));
        try {
            customWordsButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Custom sized button was not displayed");
        }

        UiObject chooseCustomWordsFragment = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/chooseCustomWordsFragment").className("android.view.ViewGroup"));

        if (!chooseCustomWordsFragment.exists()) {
            fail("Custom Words Fragment was not shown");
        }
    }



    //Test that the timer is correctly displayed on the puzzle page when turned on
//    @Ignore("Working test")
    @Test
    public void testTimerDisplayOnPuzzleFragment() {
        UiObject settingsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/settings_button"));
        try {
            settingsButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Settings Button not found");
        }
        UiObject settingsFragment = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/recycler_view"));
        try {
            UiObject timerLayout = settingsFragment.getChild(new UiSelector().index(1));
            UiObject timerLayout1 = timerLayout.getChild(new UiSelector().index(1));
            UiObject switchWidget = timerLayout1.getChild(new UiSelector().resourceId("com.echo.wordsudoku:id/switchWidget"));
            if (!switchWidget.isChecked()) {
                switchWidget.click();
            }
        } catch (UiObjectNotFoundException e) {
            fail("Settings fragment was not correctly displayed");
        }
        ourDevice.pressBack();

        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("New Game Button not found");
        }

        //Start classic puzzle
        UiObject classicPuzzleButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/classic_puzzle_button").className("android.widget.Button"));
        try {
            classicPuzzleButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Classic Puzzles Button not found");
        }

        //Start classic puzzle
        UiObject timerDisplay = ourDevice.findObject(new UiSelector().className("android.widget.TextView").textContains("00:"));
        if (!timerDisplay.exists()) {
            fail("Timer is not correctly being displayed when turned on");
        }

    }


    //Test that clicking the timer switch in the settings correctly updates the subtext below Timer Puzzle
    // header
//    @Ignore("Working test")
    @Test
    public void testSettingsTimerSwitch() {
        UiObject settingsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/settings_button"));
        try {
            settingsButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Settings Button not found");
        }
        UiObject settingsFragment = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/recycler_view"));
        try {
            UiObject timerLayout = settingsFragment.getChild(new UiSelector().index(1));
            UiObject timerLayout1 = timerLayout.getChild(new UiSelector().index(1));
            UiObject switchWidget = timerLayout1.getChild(new UiSelector().resourceId("com.echo.wordsudoku:id/switchWidget"));
            switchWidget.click();
            if (switchWidget.isChecked()) {
                UiObject timerDisplay = ourDevice.findObject(new UiSelector().textContains("Timer is on"));
                if (!timerDisplay.exists()) {
                    fail("Timer display did not correctly update");
                }
            } else {
                UiObject timerDisplay = ourDevice.findObject(new UiSelector().textContains("Timer is off"));
                if (!timerDisplay.exists()) {
                    fail("Timer display did not correctly update");
                }
            }

        } catch (UiObjectNotFoundException e) {
            fail("Settings fragment was not correctly displayed");
        }


    }

    //Test that the save dialog correctly appears when user presses back on puzzle page
//    @Ignore("Working test")
    @Test
    public void testSaveDialogDisplaysOnBackPress() {
        //For CANCEL, YES, NO
        int totalButtons = 3;

        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("New Game Button not found");
        }

        //Start classic puzzle
        UiObject classicPuzzleButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/classic_puzzle_button").className("android.widget.Button"));
        try {
            classicPuzzleButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Classic Puzzles Button not found");
        }

        //Press back
        ourDevice.pressBack();

        //Check that the save dialog content exists
        UiObject saveDialog = ourDevice.findObject(new UiSelector().resourceId("android:id/content").className("android.widget.FrameLayout"));
        if (!saveDialog.exists()) {
            fail("Save dialog box did not appear on back press");
        }

        for (int i = 0; i < totalButtons; i++) {
            UiObject button = ourDevice.findObject(new UiSelector().resourceId("android:id/button"+ (i + 1)));
            if (!button.exists()) {
                fail("All buttons in save dialog were not displayed");
            }
        }

    }

    //Check that the puzzle page displays all necessary buttons and TextViews to the user
//    @Ignore("Working test")
    @Test
    public void testPuzzlePageDisplay() {
        for (int i = 0; i < puzzleSizes.length; i++) {
            testPuzzlePageDisplayHelper(puzzleSizes[i]);
        }
    }

    public void testPuzzlePageDisplayHelper(int dim) {

        //Start new game
        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("New Game button was not displayed");
        }

        //Start custom sized puzzle
        UiObject customSizePuzzleButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_size_button").className("android.widget.Button"));
        try {
            customSizePuzzleButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Custom Sized button was not displayed on Puzzle size " + dim);
        }

        //Choose puzzle size dialog
        UiObject choosePuzzleSizeDialog = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/size_radio_group").className("android.widget.RadioGroup"));
        try {
            UiObject sizeSelection = choosePuzzleSizeDialog.getChild(new UiSelector().textContains(Integer.toString(dim)));
            sizeSelection.click();
        } catch (UiObjectNotFoundException e) {
            fail("Puzzle size selection was not displayed on Puzzle size " + dim);
        }

        UiObject doneChooseSizeDialog = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/done_button").className("android.widget.Button"));
        try {
            doneChooseSizeDialog.click();
        } catch (UiObjectNotFoundException e) {
            fail("Done button in Choose size dialog was not displayed on Puzzle size " + dim);
        }

        UiObject doneButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/options_finish_button").className("android.widget.Button"));
        if (!doneButton.exists()) {
            fail("Done Button was not displayed on Puzzle size " + dim);
        }

        UiObject resetButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/options_reset_puzzle_button").className("android.widget.Button"));
        if (!resetButton.exists()) {
            fail("Reset Button was not displayed on Puzzle size " + dim);
        }

        UiObject dictionaryButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/options_dictionary_help_button").className("android.widget.ImageButton"));
        if (!dictionaryButton.exists()) {
            fail("Dictionary Button was not displayed on Puzzle size " + dim);
        }

        UiObject helpButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/help_button").className("android.widget.ImageButton"));
        if (!helpButton.exists()) {
            fail("Help Button was not displayed on Puzzle size " + dim);
        }

        UiObject inputButtons = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/board_input_buttons").className("android.widget.LinearLayout"));
        try {
            for(int i = 0; i < inputButtons.getChildCount(); i++) {
                UiObject buttonLayouts = inputButtons.getChild(new UiSelector().instance(i));
                if (!buttonLayouts.exists()) {
                    fail("All buttons are not displayed on Puzzle size " + dim);
                }
            }
        } catch (UiObjectNotFoundException e) {
            fail("Input buttons are not displayed on Puzzle size " + dim);
        }



        UiObject timerDisplay = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/timer_text_view").className("android.widget.TextView"));
        if (!timerDisplay.exists()) {
            fail("Timer/Difficulty was not displayed on Puzzle size " + dim);
        }

        //Test that all cells are displayed
        UiObject sudokuBoard = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/sudoku_board").className("android.view.View"));
        if (!sudokuBoard.exists()) {
            fail("Sudoku Board was not displayed on Puzzle size " + dim);
        }

        for (int i = 0; i < dim * dim; i++) {
            try {
                if (!sudokuBoard.getChild(new UiSelector().instance(i)).exists()) {
                    fail("All cells not displayed on Puzzle size " + dim);
                }
            } catch (UiObjectNotFoundException e) {
                fail("All cells not displayed on Puzzle size " + dim);
            }
        }

        UiObject actionBar = ourDevice.findObject(new UiSelector().className("android.widget.FrameLayout"));
        try {
            UiObject backButton = actionBar.getChild(new UiSelector().index(1));
            UiObject kebabButton = actionBar.getChild(new UiSelector().index(3));
            if (!backButton.exists()) {
                fail("Back button was not displayed on Puzzle size " + dim);
            }
            if (!kebabButton.exists()) {
                fail("Kebab button was not displayed on Puzzle size " + dim);
            }
        } catch (UiObjectNotFoundException e) {
            fail();
        }

        ourDevice.pressBack();

        UiObject noButton = ourDevice.findObject(new UiSelector().resourceId("android:id/button2").className("android.widget.Button"));
        try {
            noButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("No button was not displayed on Puzzle size " + dim);
        }
    }

    //Test that the puzzle page displays all necessary buttons and TextViews to the user
//    @Ignore("Working test")
    @Test
    public void testPuzzlePageDisplayHorizontalEverySizes() {
        try {
            ourDevice.setOrientationLeft();
            ourDevice.waitForWindowUpdate(null, 3000);
        } catch (android.os.RemoteException e) {
            fail();
        }
        for (int i = 0; i < puzzleSizes.length; i++) {
            testPuzzlePageDisplayHorizontalHelper(puzzleSizes[i]);
        }
    }

    public void testPuzzlePageDisplayHorizontalHelper(int dim) {

        //Start new game
        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("New Game button was not displayed on Puzzle size " + dim);
        }

        //Start custom sized puzzle
        UiObject customSizePuzzleButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_size_button").className("android.widget.Button"));
        try {
            customSizePuzzleButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Custom Sized button was not displayed on Puzzle size " + dim);
        }

        //Choose puzzle size dialog
        UiObject choosePuzzleSizeDialog = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/size_radio_group").className("android.widget.RadioGroup"));
        try {
            UiObject sizeSelection = choosePuzzleSizeDialog.getChild(new UiSelector().textContains(Integer.toString(dim)));
            sizeSelection.click();
        } catch (UiObjectNotFoundException e) {
            fail("Puzzle size selection was not displayed on Puzzle size " + dim);
        }

        UiObject doneChooseSizeDialog = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/done_button").className("android.widget.Button"));
        try {
            doneChooseSizeDialog.click();
        } catch (UiObjectNotFoundException e) {
            fail("Done button in Choose size dialog was not displayed on Puzzle size " + dim);
        }

        UiObject doneButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/options_finish_button").className("android.widget.Button"));
        if (!doneButton.exists()) {
            fail("Done Button was not displayed on Puzzle size " + dim);
        }

        UiObject resetButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/options_reset_puzzle_button").className("android.widget.Button"));
        if (!resetButton.exists()) {
            fail("Reset Button was not displayed on Puzzle size " + dim);
        }

        UiObject dictionaryButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/options_dictionary_help_button").className("android.widget.ImageButton"));
        if (!dictionaryButton.exists()) {
            fail("Dictionary Button was not displayed on Puzzle size " + dim);
        }

        UiObject helpButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/help_button").className("android.widget.ImageButton"));
        if (!helpButton.exists()) {
            fail("Help Button was not displayed on Puzzle size " + dim);
        }

        UiObject inputButtons = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/board_input_buttons").className("android.widget.LinearLayout"));
        try {
            for(int i = 0; i < inputButtons.getChildCount(); i++) {
                UiObject buttonLayouts = inputButtons.getChild(new UiSelector().instance(i));
                if (!buttonLayouts.exists()) {
                    fail("All buttons are not displayed on Puzzle size " + dim);
                }
            }
        } catch (UiObjectNotFoundException e) {
            fail("Input buttons are not displayed on Puzzle size " + dim);
        }



        UiObject timerDisplay = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/timer_text_view").className("android.widget.TextView"));
        if (!timerDisplay.exists()) {
            fail("Timer/Difficulty was not displayed on Puzzle size " + dim);
        }

        //Test that all cells are displayed
        UiObject sudokuBoard = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/sudoku_board").className("android.view.View"));
        if (!sudokuBoard.exists()) {
            fail("Sudoku Board was not displayed on Puzzle size " + dim);
        }

        for (int i = 0; i < dim * dim; i++) {
            try {
                if (!sudokuBoard.getChild(new UiSelector().instance(i)).exists()) {
                    fail("All cells not displayed on Puzzle size " + dim);
                }
            } catch (UiObjectNotFoundException e) {
                fail("All cells were not displayed on Puzzle size " + dim);
            }
        }

        UiObject actionBar = ourDevice.findObject(new UiSelector().className("android.widget.FrameLayout"));
        try {
            UiObject backButton = actionBar.getChild(new UiSelector().index(1));
            UiObject kebabButton = actionBar.getChild(new UiSelector().index(3));
            if (!backButton.exists()) {
                fail("Back button was not displayed on Puzzle size " + dim);
            }
            if (!kebabButton.exists()) {
                fail("Kebab button was not displayed on Puzzle size " + dim);
            }
        } catch (UiObjectNotFoundException e) {
            fail();
        }

        ourDevice.pressBack();

        UiObject noButton = ourDevice.findObject(new UiSelector().resourceId("android:id/button2").className("android.widget.Button"));
        try {
            noButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("No button was not displayed on Puzzle size " + dim);
        }
    }


    //Test that the correct amount of entry boxes are displayed for the option selected in the dropdown
    // when in the Choose Custom Words fragment in a Horizontal orientation
//    @Ignore("Working test")
    @Test
    public void testCustomWordsPageAllEditTextsDisplayedHorizontalScrollable() {
        try {
            ourDevice.setOrientationLeft();
            ourDevice.waitForWindowUpdate(null, 3000);
        } catch (android.os.RemoteException e) {
            fail();
        }

        UiObject chooseCustomWordsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_words_button"));
        try {
            chooseCustomWordsButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Choose custom words button not found");
        }

        for (int i = 0; i < puzzleSizes.length; i++) {
            testCustomWordsPageAllEditTextsDisplayedHelperHorizontal(puzzleSizes[i], i);
        }

    }


    public void testCustomWordsPageAllEditTextsDisplayedHelperHorizontal(int totalEditTexts, int index) {

        UiObject puzzleSizeDropdown = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/puzzleSizeCustomDropdown"));
        try {
            puzzleSizeDropdown.click();
        } catch (UiObjectNotFoundException e) {
            fail("Puzzle size dropdown was not displayed on Puzzle size " + totalEditTexts);
        }

        UiObject dropdownOptions = ourDevice.findObject(new UiSelector().className("android.widget.ListView"));
        try {
            dropdownOptions.getChild(new UiSelector().index(index)).click();
        } catch (UiObjectNotFoundException e) {
            fail("Dropdown options was not displayed on Puzzle size " + totalEditTexts);
        }

        UiObject entryBox1 = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/buttonLanguageEntries"));
        UiObject entryBox2 = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/boardLanguageEntries"));

        int EditTextCounter = 0;
        try {
            for (int i = 0; i < entryBox1.getChildCount(); i++) {
                UiObject edittext = entryBox1.getChild(new UiSelector().index(i));

                UiScrollable entryBoxScrollView = new UiScrollable(new UiSelector().resourceId("com.echo.wordsudoku:id/entryBoxesScrollView"));
                try {
                    entryBoxScrollView.scrollIntoView(edittext);
                } catch (UiObjectNotFoundException e) {
                    fail("ScrollView not found");
                }

                if (!edittext.exists()) {
                    fail("EditText at index " + i + " was not displayed at puzzle size: " + totalEditTexts);
                }
                EditTextCounter++;
            }
            if (EditTextCounter != totalEditTexts) {
                fail("Not all edit texts were displayed; Only " + EditTextCounter);
            }
        } catch (UiObjectNotFoundException e) {
            fail("EditTexts were not all displayed");
        }

        //Scroll the ScrollView back up to count the 2nd row
        UiScrollable scrollBackUp = new UiScrollable(new UiSelector().resourceId("com.echo.wordsudoku:id/entryBoxesScrollView"));
        try {
            scrollBackUp.flingBackward();
        } catch (UiObjectNotFoundException e) {
            fail("ScrollView not found");
        }

        EditTextCounter = 0;
        try {
            for (int i = 0; i < entryBox2.getChildCount(); i++) {
                UiObject edittext = entryBox2.getChild(new UiSelector().index(i));

                UiScrollable entryBoxScrollView = new UiScrollable(new UiSelector().resourceId("com.echo.wordsudoku:id/entryBoxesScrollView"));
                try {
                    entryBoxScrollView.scrollIntoView(edittext);
                } catch (UiObjectNotFoundException e) {
                    fail("ScrollView not found");
                }

                if (!edittext.exists()) {
                    fail("EditText at index " + i + " was not displayed at puzzle size: " + totalEditTexts);
                }
                EditTextCounter++;
            }
            if (EditTextCounter != totalEditTexts) {
                fail("Not all edit texts were displayed; Only " + EditTextCounter);
            }
        } catch (UiObjectNotFoundException e) {
            fail("EditTexts were not all displayed");
        }

        //Scroll the ScrollView back up to count the 2nd row
        scrollBackUp = new UiScrollable(new UiSelector().resourceId("com.echo.wordsudoku:id/entryBoxesScrollView"));
        try {
            scrollBackUp.flingBackward();
        } catch (UiObjectNotFoundException e) {
            fail("ScrollView not found");
        }

    }

    // Test that Save Game button correctly loads in previously entered words when user
    // clicks Load Game option
//    @Ignore("Working test")
    @Test
    public void testSaveButtonLoadGame() {
        List<Integer> filledCellIndex = new ArrayList<>();
        List<String> filledWord = new ArrayList<>();

        //Start new game
        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("New Game button was not displayed");
        }

        //Start classic game
        UiObject classicPuzzleButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/classic_puzzle_button").className("android.widget.Button"));
        try {
            classicPuzzleButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Classic Puzzle button was not displayed");
        }

        //Find SudokuBoard
        UiObject sudokuBoard = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/sudoku_board"));

        //Iterate through board while filling it
        int iterateThroughButtons = 1;
        for (int i = 0; i < 24; i++) {
            try {
                UiObject someCell = sudokuBoard.getChild(new UiSelector().instance(i));
                if (someCell.getText().equals("EMPTYCELL")) {
                    someCell.click();
                    Log.d(INSTRUTEST, "iterateThroughButtons is: " + iterateThroughButtons);
                    UiObject someInputButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/id" + iterateThroughButtons));
                    someInputButton.click();

                    filledCellIndex.add(i);
                    filledWord.add(someInputButton.getText().toLowerCase());

                    iterateThroughButtons = (iterateThroughButtons % 9) + 1;
                }
            } catch (UiObjectNotFoundException e) {
                fail("Not all cells or buttons were displayed: " + iterateThroughButtons);
            }

        }

        ourDevice.pressBack();
        UiObject yesButton = ourDevice.findObject(new UiSelector().resourceId("android:id/button1").className("android.widget.Button"));
        try {
            yesButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Yes button was not displayed");
        }

        //Start load game
        UiObject LoadGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/load_game_button").className("android.widget.Button"));
        try {
            LoadGameButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Load game button was not displayed");
        }


        //Find SudokuBoard
        sudokuBoard = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/sudoku_board"));

        //Iterate through board while filling it
        for (int i = 0; i < filledCellIndex.size(); i++) {
            try {
                UiObject someCell = sudokuBoard.getChild(new UiSelector().instance(filledCellIndex.get(i)));

                if (!someCell.getText().equals(filledWord.get(i))) {
                    someCell.click();
                    fail("Loaded game does not match previously saved game");
                }
            } catch (UiObjectNotFoundException e) {
                fail("Not all cells or buttons were displayed: " + iterateThroughButtons);
            }
        }
    }


    //Test that the difficulty Text correctly displays difficulty selected in Settings page on the
    // Puzzle page
    @Test
    public void testDifficultyButtonDisplay() {
        UiObject settingsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/settings_button"));
        try {
            settingsButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Settings Button not found");
        }
        UiObject settingsFragment = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/recycler_view"));
        try {
            UiObject timerLayout = settingsFragment.getChild(new UiSelector().index(1));
            UiObject timerLayout1 = timerLayout.getChild(new UiSelector().index(1));
            UiObject switchWidget = timerLayout1.getChild(new UiSelector().resourceId("com.echo.wordsudoku:id/switchWidget"));
            if (switchWidget.isChecked()) {
                switchWidget.click();
            }
        } catch (UiObjectNotFoundException e) {
            fail("Settings fragment was not correctly displayed");
        }

        String s = "";
        try {
            UiObject difficultySetting = settingsFragment.getChild(new UiSelector().index(3));
            UiObject difficultyLayout = difficultySetting.getChild(new UiSelector().index(0));
            UiObject difficultySelected = difficultyLayout.getChild(new UiSelector().index(1));
            s = difficultySelected.getText().toLowerCase();
            Log.d(INSTRUTEST, s);
        } catch (UiObjectNotFoundException e) {
            fail("Difficulty not found");
        }

        ourDevice.pressBack();

        //Start new game
        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("New Game button was not displayed");
        }

        //Start classic game
        UiObject classicPuzzleButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/classic_puzzle_button").className("android.widget.Button"));
        try {
            classicPuzzleButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Classic Puzzle button was not displayed");
        }

        //Start classic game
        UiObject timerDifficultyButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/timer_text_view").className("android.widget.TextView"));
        try {
            String b = timerDifficultyButton.getText().toLowerCase();
            Log.d(INSTRUTEST, b);
            if (!s.equals(b)) {
                fail("Correct difficulty was not displayed");
            }
        } catch (UiObjectNotFoundException e) {
            fail("Difficulty/Timer button was not displayed");
        }
    }


    //Test that the correct amount of Entry boxes are displayed for the option selected in the dropdown
    // when in the Choose custom words fragment in landscape orientation
//    @Ignore("Working test")
    @Test
    public void testCustomWordsPageAllEditTextsDisplayed() {
        UiObject chooseCustomWordsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_words_button"));
        try {
            chooseCustomWordsButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Choose custom words button not found");
        }

        for (int i = 0; i < puzzleSizes.length; i++) {
            testCustomWordsPageAllEditTextsDisplayedHelper(puzzleSizes[i], i);
        }
    }

    public void testCustomWordsPageAllEditTextsDisplayedHelper(int totalEditTexts, int index) {

        UiObject puzzleSizeDropdown = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/puzzleSizeCustomDropdown"));
        try {
            puzzleSizeDropdown.click();
        } catch (UiObjectNotFoundException e) {
            fail("Puzzle size dropdown was not displayed");
        }

        UiObject dropdownOptions = ourDevice.findObject(new UiSelector().className("android.widget.ListView"));
        try {
            dropdownOptions.getChild(new UiSelector().index(index)).click();
        } catch (UiObjectNotFoundException e) {
            fail("Dropdown options was not displayed");
        }

        UiObject entryBox1 = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/buttonLanguageEntries"));
        UiObject entryBox2 = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/boardLanguageEntries"));

        int EditTextCounter = 0;
        try {
            for (int i = 0; i < entryBox1.getChildCount() - 1; i++) {
                UiObject edittext = entryBox1.getChild(new UiSelector().index(i + 1));
                if (!edittext.exists()) {
                    fail("EditText at index " + i + " was not displayed");
                }
                EditTextCounter++;
            }
            if (EditTextCounter != totalEditTexts) {
                fail("Not all edit texts were displayed");
            }
        } catch (UiObjectNotFoundException e) {
            fail("EditTexts were not all displayed");
        }

        EditTextCounter = 0;
        try {
            for (int i = 0; i < entryBox2.getChildCount() - 1; i++) {
                UiObject edittext = entryBox2.getChild(new UiSelector().index(i + 1));
                if (!edittext.exists()) {
                    fail("EditText at index " + i + " was not displayed");
                }
                EditTextCounter++;
            }
            if (EditTextCounter != totalEditTexts) {
                fail("Not all edit texts were displayed");
            }
        } catch (UiObjectNotFoundException e) {
            fail("EditTexts were not all displayed");
        }
    }



    //Test that when users click the puzzle language button on the main menu, the button text displays the
    //language it has been set to
//    @Ignore("Working test")
    @Test
    public void testPuzzleLanguageButtonUpdate() {
        UiObject puzzleLanguageButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/change_language_button"));
        String s = "";
        try {
            s = puzzleLanguageButton.getText();
            puzzleLanguageButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Puzzle language button not found");
        }

        UiObject puzzleLanguageUpdateText = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/change_language_button"));
        try {
            if (s.contains("French")) {
                if (!puzzleLanguageUpdateText.getText().equals("Puzzle Language : English")) {
                    fail("Puzzle Language Button did not display set language correctly");
                }
            } else {
                if (!puzzleLanguageUpdateText.getText().equals("Puzzle Language : French")) {
                    fail("Puzzle Language Button did not display set language correctly");
                }
            }

        } catch (UiObjectNotFoundException e) {
            fail("Puzzle Language Button text did not update on click");
        }

    }

    //Test that when clicking Custom Words button the user is correctly taken to Choose custom words page
//    @Ignore("Working test")
    @Test
    public void testCustomWordsButtonNavigation() {
        //Start new game
        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("New Game button was not displayed");
        }

        //Click custom words button
        UiObject customWordsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_words_button").className("android.widget.Button"));
        try {
            customWordsButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Custom Words button was not displayed");
        }

        //Check if chooseCustomWordsFragment is being displayed
        UiObject chooseCustomWordsFragment = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/chooseCustomWordsFragment").className("android.view.ViewGroup"));
        if (!chooseCustomWordsFragment.exists()) {
            fail("Choose custom words fragment was not displayed");
        }
    }

    //Test that all options buttons are displayed in the Choose Puzzle Mode page in a horizontal orientation
//    @Ignore("Working test")
    @Test
    public void testChoosePuzzleModeAllButtonsDisplayedHorizontal() {
        try {
            ourDevice.setOrientationLeft();
            ourDevice.waitForWindowUpdate(null, 3000);
        } catch (android.os.RemoteException e) {
            fail();
        }

        //Start new game
        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("New Game button was not displayed");
        }

        UiObject classicPuzzleButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/classic_puzzle_button").className("android.widget.Button"));
        if (!classicPuzzleButton.exists()) {
            fail("Classic Puzzle button was not displayed");
        }

        UiObject customSizedButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_size_button").className("android.widget.Button"));
        if (!customSizedButton.exists()) {
            fail("Custom Size button was not displayed");
        }

        UiObject customWordsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_words_button").className("android.widget.Button"));
        if (!customWordsButton.exists()) {
            fail("Custom Words button was not displayed");
        }

    }

    //Test that all options buttons are displayed in the Choose Puzzle Mode page
//    @Ignore("Working test")
    @Test
    public void testChoosePuzzleModeAllButtonsDisplayedEverySize() {
        //Start new game
        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("New Game button was not displayed");
        }

        UiObject classicPuzzleButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/classic_puzzle_button").className("android.widget.Button"));
        if (!classicPuzzleButton.exists()) {
            fail("Classic Puzzle button was not displayed");
        }

        UiObject customSizedButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_size_button").className("android.widget.Button"));
        if (!customSizedButton.exists()) {
            fail("Custom Size button was not displayed");
        }

        UiObject customWordsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_words_button").className("android.widget.Button"));
        if (!customWordsButton.exists()) {
            fail("Custom Words button was not displayed");
        }

    }


    //Test that all main menu buttons are correctly displayed in a horizontal orientation
//    @Ignore("Working test")
    @Test
    public void testAllButtonsDisplayedMainMenuHorizontal() {
        try {
            ourDevice.setOrientationLeft();
        } catch (android.os.RemoteException e) {
            fail();
        }

        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button"));
        if (!newGameButton.exists()) {
            fail("New game button does not appear");
        }

        UiObject loadGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/load_game_button"));
        if (!loadGameButton.exists()) {
            fail("Load game button does not appear");
        }

        UiObject puzzleLanguageButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/change_language_button"));
        if (!puzzleLanguageButton.exists()) {
            fail("Change language button does not appear");
        }

        UiObject exitButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/exit_button"));
        if (!exitButton.exists()) {
            fail("Exit button does not appear");
        }

        UiObject customWordsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_words_button"));
        if (!customWordsButton.exists()) {
            fail("Custom words button does not appear");
        }

        UiObject settingsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/settings_button"));
        if (!settingsButton.exists()) {
            fail("Settings button does not appear");
        }

    }






    //Test that all buttons are displayed correctly in a puzzle in a horizontal orientation and that clicking
    // them displays the correct button text in an empty cell
//    @Ignore("Working test")
    @Test
    public void testWordInsertCellDisplayMatch() {
        int dim = 4;

        //Start new game
        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("New Game button was not displayed");
        }

        //Start custom sized puzzle
        UiObject customSizePuzzleButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_size_button").className("android.widget.Button"));
        try {
            customSizePuzzleButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Custom Sized button was not displayed");
        }

        //Choose puzzle size dialog
        UiObject choosePuzzleSizeDialog = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/size_radio_group").className("android.widget.RadioGroup"));
        try {
            UiObject sizeSelection = choosePuzzleSizeDialog.getChild(new UiSelector().textContains(Integer.toString(dim)));
            sizeSelection.click();
        } catch (UiObjectNotFoundException e) {
            fail("Puzzle size selection was not found");
        }

        UiObject doneChooseSizeDialog = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/done_button").className("android.widget.Button"));
        try {
            doneChooseSizeDialog.click();
        } catch (UiObjectNotFoundException e) {
            fail("Done button in Choose size dialog was not found");
        }

        //Find SudokuBoard
        UiObject sudokuBoard = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/sudoku_board"));

        int iterateThroughButtons = 1;
        for (int i = 0; i < dim * dim; i++) {
            try {
                UiObject someCell = sudokuBoard.getChild(new UiSelector().instance(i));
                if (someCell.getText().equals("EMPTYCELL")) {
                    someCell.click();
                    UiObject someInputButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/id" + iterateThroughButtons));
                    someInputButton.click();

                    ourDevice.pressBack();
                    UiObject cancelButton = ourDevice.findObject(new UiSelector().resourceId("android:id/button3").className("android.widget.Button"));
                    try {
                        cancelButton.click();
                    } catch (UiObjectNotFoundException e) {
                        fail("Cancel button was not displayed");
                    }

                    if(!someCell.getText().equals(someInputButton.getText().toLowerCase())) {
                        fail("Cell display and inserted word did not match: " + someCell.getText() + " " + someInputButton.getText());
                    }

                }
            } catch (UiObjectNotFoundException e) {
                fail("Not all cells or buttons were displayed: " + iterateThroughButtons);
            }
            iterateThroughButtons = (iterateThroughButtons % dim) + 1;
        }


        ourDevice.pressBack();
        UiObject noButton = ourDevice.findObject(new UiSelector().resourceId("android:id/button2").className("android.widget.Button"));
        try {
            noButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("No button was not displayed");
        }
    }





    //Test that Clicking the Choose custom words button takes us to the Choose custom words fragment
//    @Ignore("Working test")
    @Test
    public void testChooseCustomWordsButtonNavigation() {
        UiObject customWordsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_words_button").className("android.widget.Button"));
        try {
            customWordsButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Custom Words Button not found");
        }

        UiObject chooseCustomWords = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/chooseCustomWordsFragment").className("android.view.ViewGroup"));
        if (!chooseCustomWords.exists()) {
            fail("Choose custom words fragment was not displayed");
        }
    }


    //Test that clicking the new game button takes you to the Choose puzzle mode fragment
    //    @Ignore("Working test")
    @Test
    public void testNewGameButtonNavigation() {
        //Start new game
        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("New Game button was not found");
        }

        UiObject choosePuzzleModeFragment = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/choosePuzzleModeFragment").className("android.view.ViewGroup"));
        if (!choosePuzzleModeFragment.exists()) {
            fail("Choose Puzzle Mode was not displayed");
        }
    }


    //Test that clicking the back button in settings page takes us back to main menu
//    @Ignore("Working test")
    @Test
    public void testSettingsBackButtonNavigation() {
        UiObject settingsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/settings_button"));
        try {
            settingsButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Settings Button not found");
        }

        UiObject actionBar = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/action_bar").className("android.view.ViewGroup"));
        try {
            Log.d(INSTRUTEST, Integer.toString(actionBar.getChildCount()));
            UiObject backButton = actionBar.getChild(new UiSelector().index(0));
            backButton.click();
        } catch (UiObjectNotFoundException e) {
            fail();
        }

        UiObject mainMenuFragment = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/mainMenuScreen"));
        if (!mainMenuFragment.exists()) {
            fail("Clicking back button did not display main menu");
        }
    }


    //Test that clicking the Settings button in the main menu correctly displays the Settings fragment
//    @Ignore("Working test")
    @Test
    public void testMainMenuSettingsButtonNavigation() {
        UiObject settingsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/settings_button"));
        try {
            settingsButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Settings Button not found");
        }

        UiObject settingsActionBar = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/action_bar_container"));
        if (!settingsActionBar.exists()) {
            fail("Settings action bar was not displayed");
        }

        UiObject actionBar = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/action_bar"));
        try {
            actionBar.getChild(new UiSelector().className("android.widget.ImageButton"));
        } catch (UiObjectNotFoundException e) {
            fail();
        }

        UiObject settingsRecyclerFragment = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/recycler_view"));
        if (!settingsRecyclerFragment.exists()) {
            fail("Settings page was not displayed");
        }

    }


    //Test that all options buttons are correctly displayed on the main menu
//    @Ignore("Working test")
    @Test
    public void testAllOptionsButtonsDisplayedMainMenu() {
        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button"));
        if (!newGameButton.exists()) {
            fail("New game button does not appear");
        }

        UiObject loadGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/load_game_button"));
        if (!loadGameButton.exists()) {
            fail("Load game button does not appear");
        }

        UiObject puzzleLanguageButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/change_language_button"));
        if (!puzzleLanguageButton.exists()) {
            fail("Change language button does not appear");
        }

        UiObject exitButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/exit_button"));
        if (!exitButton.exists()) {
            fail("Exit button does not appear");
        }

        UiObject customWordsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_words_button"));
        if (!customWordsButton.exists()) {
            fail("Custom words button does not appear");
        }

        UiObject settingsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/settings_button"));
        if (!settingsButton.exists()) {
            fail("Settings button does not appear");
        }

    }



    //Test clicking the Exit button correctly exits the app
//    @Ignore("Working test")
    @Test
    public void testMainMenuExitButton() {
        UiObject exitButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/exit_button"));
        try {
            exitButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Exit Button not found");
        }

        UiObject mainMenu = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/main_menu_fragment"));
        if (mainMenu.exists()) {
            fail("Exit button did not work");
        }
    }


    //Test that all 4 radiobutton sizes [4, 6, 9, 12]
    // are displayed in the custom word fragments puzzle size options dropdown
//    @Ignore("Working test")
    @Test
    public void testSelectCustomSizedPuzzlesDialogDisplayed() {

        //Start new game
        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }

        //Start classic puzzle
        UiObject customSizedButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_size_button").className("android.widget.Button"));
        try {
            customSizedButton.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }

        UiObject dropdownPuzzleSizes = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/customPuzzleSizeSelect"));
        if (!dropdownPuzzleSizes.exists()) {
            fail();
        }

        UiObject puzzle4x4Option = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/choose_12x12_puzzle"));
        UiObject puzzle12x12Option = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/choose_6x6_button"));
        UiObject puzzle6x6Option = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/choose_9x9_puzzle"));
        UiObject puzzle9x9Option = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/choose_4x4_button"));
        if (!puzzle4x4Option.exists()) {
            fail("4x4 option was not displayed in Choose Custom Size pop up");
        }
        if (!puzzle6x6Option.exists()) {
            fail("6x6 option was not displayed in Choose Custom Size pop up");
        }
        if (!puzzle9x9Option.exists()) {
            fail("9x9 option was not displayed in Choose Custom Size pop up");
        }
        if (!puzzle12x12Option.exists()) {
            fail("12x12 option was not displayed in Choose Custom Size pop up");
        }
    }


    //Test that clicking on the 3 dots (kebab button) opens up a dialog options
//    @Ignore("Working test")
    @Test
    public void testKebabButtonInPuzzleFragmentDisplaysOptions() {
        //Start new game
        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("New game button not found");
        }

        //Start classic puzzle
        UiObject classicPuzzleButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/classic_puzzle_button").className("android.widget.Button"));
        try {
            classicPuzzleButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Classic puzzle button not found");
        }

        UiObject kebabButton = ourDevice.findObject(new UiSelector().descriptionContains("More options").className("android.widget.ImageView"));
        try {
            kebabButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("kebab button not found");
        }

        UiObject listView = ourDevice.findObject(new UiSelector().className("android.widget.ListView"));
        if (!listView.exists()) {
            fail("Kebab did load options page listview");
        }


        UiObject doneButton = ourDevice.findObject(new UiSelector().text("Done").className("android.widget.TextView"));
        UiObject rulesButton = ourDevice.findObject(new UiSelector().text("Rules").className("android.widget.TextView"));
        UiObject dictionaryButton = ourDevice.findObject(new UiSelector().text("Dictionary").className("android.widget.TextView"));
        UiObject savePuzzleButton = ourDevice.findObject(new UiSelector().text("Save Puzzle").className("android.widget.TextView"));
        UiObject mainMenuButton = ourDevice.findObject(new UiSelector().text("Main Menu").className("android.widget.TextView"));
        UiObject exitButton = ourDevice.findObject(new UiSelector().text("Exit").className("android.widget.TextView"));

        if (!doneButton.exists()) {
            fail("Done button not displayed");
        }
        if (!rulesButton.exists()) {
            fail("Rules button not displayed");
        }
        if (!dictionaryButton.exists()) {
            fail("Dictionary button not displayed");
        }
        if (!savePuzzleButton.exists()) {
            fail("Save Puzzle button not displayed");
        }
        if (!mainMenuButton.exists()) {
            fail("Main Menu not displayed");
        }
        if (!exitButton.exists()) {
            fail("Exit button not displayed");
        }

    }


    //Test that rules dialog pop up is displayed from Kebab menu option
//    @Ignore("Working test")
    @Test
    public void testKebabRulesButtonNavigation() {
        //Start new game
        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("New game button not found");
        }

        //Start classic puzzle
        UiObject classicPuzzleButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/classic_puzzle_button").className("android.widget.Button"));
        try {
            classicPuzzleButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Classic puzzle button not found");
        }

        UiObject kebabButton = ourDevice.findObject(new UiSelector().descriptionContains("More options").className("android.widget.ImageView"));
        try {
            kebabButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("kebab button not found");
        }

        UiObject rulesButton = ourDevice.findObject(new UiSelector().textContains("Rules").className("android.widget.TextView"));
        try {
            rulesButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Rules button not found");
        }

        UiObject popUpRules = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/PopUp_Rules"));
        UiObject rulesInfoText = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/RulesInformation"));
        UiObject rulesHeader = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/RulesHeader"));
        UiObject rulesExitButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/rulesExitButton"));

        if (!rulesInfoText.exists() || !rulesHeader.exists() || !rulesExitButton.exists() || !popUpRules.exists()) {
            fail("Rules pop up was not properly displayed");
        }


    }

    //Test that the dictionary pop up is correctly displayed from Kebab menu option
//    @Ignore("Working test")
    @Test
    public void testKebabDictionaryButtonNavigation() {
        //Start new game
        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("New game button not found");
        }

        //Start classic puzzle
        UiObject classicPuzzleButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/classic_puzzle_button").className("android.widget.Button"));
        try {
            classicPuzzleButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Classic puzzle button not found");
        }

        UiObject kebabButton = ourDevice.findObject(new UiSelector().descriptionContains("More options").className("android.widget.ImageView"));
        try {
            kebabButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("kebab button not found");
        }

        UiObject dictionaryButton = ourDevice.findObject(new UiSelector().textContains("Dictionary").className("android.widget.TextView"));
        try {
            dictionaryButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Rules button not found");
        }

        UiObject popupDictionary = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/PopUp_DictionaryBox"));
        UiObject dictionaryWordList1 = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/wordListLang2"));
        UiObject dictionaryWordList2 = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/wordListLang1"));
        UiObject dictionaryHeader = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/DictionaryHeader"));
        UiObject dictionaryExitButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/dictionaryExitButton"));

        if (!dictionaryWordList1.exists() || !dictionaryWordList2.exists() ||!dictionaryHeader.exists() || !dictionaryExitButton.exists() || !popupDictionary.exists()) {
            fail("Dictionary pop up was not properly displayed");
        }
    }

    //Test that the entry box in the choose custom words page limits the word length to 12
//    @Ignore("Working test")
    @Test
    public void testChooseCustomWordEditBoxMaxLength() {
        String longUserInputtedString = "awehuifuiawdghwuawidhauiwdhuawidawuidhawuihduwhduawidjawdjiwjawda";

        UiObject chooseCustomWordsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_words_button"));
        try {
            chooseCustomWordsButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Choose custom words button not found");
        }

        UiObject entryBox1 = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/buttonLanguageEntries"));

        try {
            UiObject editText = entryBox1.getChild(new UiSelector().index(1));
            editText.click();
            editText.setText(longUserInputtedString);
            String s = editText.getText();
            if (s.length() > 12) {
                fail("Words exceed the length of 12");
            }

        } catch (UiObjectNotFoundException e) {
            fail("EditTexts were not all displayed");
        }

    }

    //Test that a warning dialog is displayed when a user does not enter any text in the Choose Custom Words
    // page then presses confirm
//    @Ignore("Working test")
    @Test
    public void testChooseCustomWordEditBoxBlank() {

        UiObject chooseCustomWordsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_words_button"));
        try {
            chooseCustomWordsButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Choose custom words button not found");
        }

        UiObject confirmButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/buttonConfirmCustomWords"));

        try {
            confirmButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Confirm button does not exist");
        }

        UiObject warningPopUp = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/WarningPopUp"));
        if (!warningPopUp.exists()) {
            fail("No warning pop up was displayed");
        }

        UiObject okButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/warningDismiss"));
        try {
            okButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Ok Button was not displayed");
        }

        UiObject chooseCustomWordsFragment = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/chooseCustomWordsFragment"));
        if (!chooseCustomWordsFragment.exists()) {
            fail("Choose Custom Words fragment was not shown after warning pop up was dismissed");
        }



    }


    //Test that entering numbers into custom words displays error dialog
//    @Ignore("Working test")
    @Test
    public void testCustomWordsNumbersErrorDialog() {
        UiObject chooseCustomWordsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_words_button"));
        try {
            chooseCustomWordsButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Choose custom words button not found");
        }

        UiObject entryBox1 = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/buttonLanguageEntries"));

        try {
            UiObject editText = entryBox1.getChild(new UiSelector().index(1));
            editText.click();
            editText.setText("123");
        } catch (UiObjectNotFoundException e) {
            fail("EditTexts were not all displayed");
        }

        UiObject confirmButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/buttonConfirmCustomWords"));

        try {
            confirmButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Confirm button does not exist");
        }

        UiObject warningPopUp = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/WarningPopUp"));
        if (!warningPopUp.exists()) {
            fail("No warning pop up was displayed");
        }

        UiObject okButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/warningDismiss"));
        try {
            okButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Ok Button was not displayed");
        }

        UiObject chooseCustomWordsFragment = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/chooseCustomWordsFragment"));
        if (!chooseCustomWordsFragment.exists()) {
            fail("Choose Custom Words fragment was not shown after warning pop up was dismissed");
        }
    }

    //Test that all setting headers and switches are displayed in horizontal orientation
//    @Ignore("Working test")
    @Test
    public void testSettingsHorizontal() {
        try {
            ourDevice.setOrientationLeft();
            ourDevice.waitForWindowUpdate(null, 3000);
        } catch (android.os.RemoteException e) {
            fail();
        }

        UiObject settingsButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/settings_button"));
        try {
            settingsButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Settings button not found");
        }


        UiScrollable scrollableOptions = new UiScrollable(new UiSelector().resourceId("com.echo.wordsudoku:id/recycler_view").className("androidx.recyclerview.widget.RecyclerView"));
        try {
            UiObject puzzleOptions = ourDevice.findObject(new UiSelector().textContains("Puzzle Timer"));
            UiObject puzzleDifficulty = ourDevice.findObject(new UiSelector().textContains("Set Puzzle Difficulty"));
            UiObject setUiImmersion = ourDevice.findObject(new UiSelector().textContains("Set UI immersion"));
            UiObject autoSave = ourDevice.findObject(new UiSelector().textContains("Auto Save"));
            scrollableOptions.scrollIntoView(puzzleOptions);
            scrollableOptions.scrollIntoView(puzzleDifficulty);
            scrollableOptions.scrollIntoView(setUiImmersion);
            scrollableOptions.scrollIntoView(autoSave);

        } catch (UiObjectNotFoundException e) {
            fail("All settings options were not displayed");
        }
    }

    //Test that clicking the done button on an unfilled board does not take the user away from the puzzle
    // page
//    @Ignore("Working test")
    @Test
    public void testPuzzlePageDoneButton() {
        //Start new game
        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("New game button not found");
        }

        //Start custom sized puzzle
        UiObject customSizedButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_size_button").className("android.widget.Button"));
        try {
            customSizedButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Custom size puzzle button not found");
        }

        UiObject puzzleSize9x9 = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/choose_9x9_puzzle").className("android.widget.RadioButton"));
        try {
            puzzleSize9x9.click();
        } catch (UiObjectNotFoundException e) {
            fail("9x9 size puzzle button not found");
        }

        UiObject doneMenuButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/done_button").className("android.widget.Button"));
        try {
            doneMenuButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Done button not found");
        }

        UiObject doneButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/options_finish_button").className("android.widget.Button"));
        try {
            doneButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Done puzzle button was not displayed");
        }

        UiObject puzzleFragment = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/puzzle_fragment").className("android.view.ViewGroup"));
        try {
            puzzleFragment.click();
        } catch (UiObjectNotFoundException e) {
            fail("Puzzle page was not displayed after clicking done on unfinished board");
        }


    }


    //Test that puzzle results are displayed correctly in horizontal orientation
//    @Ignore("Working test")
    @Test
    public void testPuzzleResultsDisplayHorizontal() {
        int dim = 4 * 4;

        try {
            ourDevice.setOrientationLeft();
            sleep(500);
        } catch (android.os.RemoteException e) {
            fail();
        }

        //Start new game
        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("New game button not found");
        }

        //Start custom sized puzzle
        UiObject customSizedButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_size_button").className("android.widget.Button"));
        try {
            customSizedButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Custom size puzzle button not found");
        }

        UiObject puzzleSize4x4 = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/choose_4x4_button").className("android.widget.RadioButton"));
        try {
            puzzleSize4x4.click();
        } catch (UiObjectNotFoundException e) {
            fail("4x4 size puzzle button not found");
        }

        UiObject doneMenuButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/done_button").className("android.widget.Button"));
        try {
            doneMenuButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Done button not found");
        }

        UiObject sudokuBoard = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/sudoku_board").className("android.view.View"));
        for (int i = 0; i < dim; i++) {
            try {
                UiObject someCell = sudokuBoard.getChild(new UiSelector().instance(i));
                if (someCell.getText().equals("EMPTYCELL")) {
                    someCell.click();
                    UiObject entryButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/id1").className("android.widget.Button"));
                    entryButton.click();
                }
            } catch (UiObjectNotFoundException e) {
                fail("Cell not found at index " + i);
            }
        }

        UiObject doneButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/options_finish_button").className("android.widget.Button"));
        try {
            doneButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Done puzzle button was not displayed");
        }

        UiObject resultLossPage = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/ResultsFragmentPage").className("android.view.ViewGroup"));
        if (!resultLossPage.exists()) {
            fail("Result loss page did not appear");
        }
    }

    //Test that puzzle results loss page is correctly displayed on puzzle loss
//    @Ignore("Working test")
    @Test
    public void testPuzzleResultsLossDisplay() {
        int dim = 4 * 4;

        //Start new game
        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("New game button not found");
        }

        //Start custom sized puzzle
        UiObject customSizedButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_size_button").className("android.widget.Button"));
        try {
            customSizedButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Custom size puzzle button not found");
        }

        UiObject puzzleSize4x4 = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/choose_4x4_button").className("android.widget.RadioButton"));
        try {
            puzzleSize4x4.click();
        } catch (UiObjectNotFoundException e) {
            fail("4x4 size puzzle button not found");
        }

        UiObject doneMenuButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/done_button").className("android.widget.Button"));
        try {
            doneMenuButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Done button not found");
        }

        UiObject sudokuBoard = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/sudoku_board").className("android.view.View"));
        for (int i = 0; i < dim; i++) {
            try {
                UiObject someCell = sudokuBoard.getChild(new UiSelector().instance(i));
                if (someCell.getText().equals("EMPTYCELL")) {
                    someCell.click();
                    UiObject entryButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/id1").className("android.widget.Button"));
                    entryButton.click();
                }
            } catch (UiObjectNotFoundException e) {
                fail("Cell not found at index " + i);
            }
        }

        UiObject doneButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/options_finish_button").className("android.widget.Button"));
        try {
            doneButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Done puzzle button was not displayed");
        }

        UiObject resultLossPage = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/ResultsFragmentPage").className("android.view.ViewGroup"));
        if (!resultLossPage.exists()) {
            fail("Result loss page did not appear");
        }
    }

    //Test that the main menu button at the puzzle results page correctly takes the user back to the main
    // menu
//    @Ignore("Working test")
    @Test
    public void testPuzzleResultsMainMenuNavigation() {
        int dim = 4 * 4;

        //Start new game
        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("New game button not found");
        }

        //Start custom sized puzzle
        UiObject customSizedButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_size_button").className("android.widget.Button"));
        try {
            customSizedButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Custom size puzzle button not found");
        }

        UiObject puzzleSize4x4 = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/choose_4x4_button").className("android.widget.RadioButton"));
        try {
            puzzleSize4x4.click();
        } catch (UiObjectNotFoundException e) {
            fail("4x4 size puzzle button not found");
        }

        UiObject doneMenuButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/done_button").className("android.widget.Button"));
        try {
            doneMenuButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Done button not found");
        }

        UiObject sudokuBoard = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/sudoku_board").className("android.view.View"));
        for (int i = 0; i < dim; i++) {
            try {
                UiObject someCell = sudokuBoard.getChild(new UiSelector().instance(i));
                if (someCell.getText().equals("EMPTYCELL")) {
                    someCell.click();
                    UiObject entryButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/id1").className("android.widget.Button"));
                    entryButton.click();
                }
            } catch (UiObjectNotFoundException e) {
                fail("Cell not found at index " + i);
            }
        }

        UiObject doneButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/options_finish_button").className("android.widget.Button"));
        try {
            doneButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Done puzzle button was not displayed");
        }

        UiObject resultLossPage = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/ResultsFragmentPage").className("android.view.ViewGroup"));
        if (!resultLossPage.exists()) {
            fail("Result loss page did not appear");
        }

        UiObject mainMenuButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/main_menu_button").className("android.widget.Button"));
        try {
            mainMenuButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Main Menu return button not displayed");
        }

        UiObject mainMenuScreen = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/mainMenuScreen").className("android.view.ViewGroup"));
        if (!mainMenuScreen.exists()) {
            fail("Main Menu screen was not displayed");
        }

    }

    //Test that the puzzle screen is displayed when the retry button is clicked
//    @Ignore("Working test")
    @Test
    public void testPuzzleResultsRetryNavigation() {
        int dim = 4 * 4;

        //Start new game
        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("New game button not found");
        }

        //Start custom sized puzzle
        UiObject customSizedButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_size_button").className("android.widget.Button"));
        try {
            customSizedButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Custom size puzzle button not found");
        }

        UiObject puzzleSize4x4 = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/choose_4x4_button").className("android.widget.RadioButton"));
        try {
            puzzleSize4x4.click();
        } catch (UiObjectNotFoundException e) {
            fail("4x4 size puzzle button not found");
        }

        UiObject doneMenuButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/done_button").className("android.widget.Button"));
        try {
            doneMenuButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Done button not found");
        }

        UiObject sudokuBoard = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/sudoku_board").className("android.view.View"));
        for (int i = 0; i < dim; i++) {
            try {
                UiObject someCell = sudokuBoard.getChild(new UiSelector().instance(i));
                if (someCell.getText().equals("EMPTYCELL")) {
                    someCell.click();
                    UiObject entryButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/id1").className("android.widget.Button"));
                    entryButton.click();
                }
            } catch (UiObjectNotFoundException e) {
                fail("Cell not found at index " + i);
            }
        }

        UiObject doneButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/options_finish_button").className("android.widget.Button"));
        try {
            doneButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Done puzzle button was not displayed");
        }

        UiObject resultLossPage = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/ResultsFragmentPage").className("android.view.ViewGroup"));
        if (!resultLossPage.exists()) {
            fail("Result loss page did not appear");
        }

        UiObject retryPuzzleButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/result_retry_puzzle_button").className("android.widget.Button"));
        try {
            retryPuzzleButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Main Menu return button not displayed");
        }

        UiObject puzzleFragmentScreen = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/puzzle_fragment").className("android.view.ViewGroup"));
        if (!puzzleFragmentScreen.exists()) {
            fail("Puzzle page was not shown after retry button clicked");
        }

    }



    //Test that the Puzzle Results win display is correctly shown on correct solution
//    @Ignore("Working test")
    @Test
    public void testPuzzleResultsWinDisplay() {
        int dim = 4 * 4;

        //Start new game
        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("New game button not found");
        }

        //Start custom sized puzzle
        UiObject customSizedButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_size_button").className("android.widget.Button"));
        try {
            customSizedButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Custom size puzzle button not found");
        }

        UiObject puzzleSize4x4 = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/choose_4x4_button").className("android.widget.RadioButton"));
        try {
            puzzleSize4x4.click();
        } catch (UiObjectNotFoundException e) {
            fail("4x4 size puzzle button not found");
        }

        UiObject doneMenuButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/done_button").className("android.widget.Button"));
        try {
            doneMenuButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Done button not found");
        }

        UiObject sudokuBoard = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/sudoku_board").className("android.view.View"));
        for (int i = 0; i < dim; i++) {
            try {
                UiObject someCell = sudokuBoard.getChild(new UiSelector().instance(i));
                if (someCell.getText().equals("EMPTYCELL")) {
                    someCell.click();
                    UiObject entryButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/id1").className("android.widget.Button"));
                    entryButton.click();
                }
            } catch (UiObjectNotFoundException e) {
                fail("Cell not found at index " + i);
            }
        }

        UiObject doneButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/options_finish_button").className("android.widget.Button"));
        try {
            doneButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Done puzzle button was not displayed");
        }

        UiObject resultLossPage = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/ResultsFragmentPage").className("android.view.ViewGroup"));
        if (!resultLossPage.exists()) {
            fail("Result loss page did not appear");
        }
    }

    //Test that the reset button erases all user work
//    @Ignore("Working test")
    @Test
    public void testResetButton() {
        int dim = 4 * 4;
        List<Integer> indexFilledCells = new ArrayList<>();

        //Start new game
        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("New game button not found");
        }

        //Start custom sized puzzle
        UiObject customSizedButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_size_button").className("android.widget.Button"));
        try {
            customSizedButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Custom size puzzle button not found");
        }

        UiObject puzzleSize4x4 = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/choose_4x4_button").className("android.widget.RadioButton"));
        try {
            puzzleSize4x4.click();
        } catch (UiObjectNotFoundException e) {
            fail("4x4 size puzzle button not found");
        }

        UiObject doneButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/done_button").className("android.widget.Button"));
        try {
            doneButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Done button not found");
        }

        UiObject sudokuBoard = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/sudoku_board").className("android.view.View"));
        for (int i = 0; i < dim; i++) {
            try {
                UiObject someCell = sudokuBoard.getChild(new UiSelector().instance(i));
                if (someCell.getText().equals("EMPTYCELL")) {
                    someCell.click();
                    UiObject entryButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/id1").className("android.widget.Button"));
                    entryButton.click();
                    indexFilledCells.add(i);
                }
            } catch (UiObjectNotFoundException e) {
                fail("Cell not found at index " + i);
            }
        }

        //Click reset button
        UiObject resetButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/options_reset_puzzle_button").className("android.widget.Button"));
        try {
            resetButton.click();
            sleep(1000);
        } catch (UiObjectNotFoundException e) {
            fail("Reset button was not found");
        }

        UiObject helpButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/help_button").className("android.widget.ImageButton"));
        try {
            helpButton.click();
            ourDevice.pressBack();
        } catch (UiObjectNotFoundException e) {
            fail("didn't work bro");
        }


        UiObject sudokuBoard1 = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/sudoku_board").className("android.view.View"));
        for (int i = 0; i < dim; i++) {
            for (int z = 0; z < indexFilledCells.size(); z++) {
                try {
                    if (i == indexFilledCells.get(z)) {
                        Log.d(INSTRUTEST, "indexFilledCells at index: " + z + " is: " + indexFilledCells.get(z));
                        UiObject someCell1 = sudokuBoard1.getChild(new UiSelector().instance(i));
                        Log.d(INSTRUTEST, "Cell at index: " + i + " is: " + someCell1.getText());
                        Log.d(INSTRUTEST, "Cell at index: " + i + " contentDescription is: " + someCell1.getContentDescription());

                        if (!someCell1.getText().equals("EMPTYCELL")) {
                            fail("Reset button did not empty filled cells: " + someCell1.getText());
                        }
                    }
                } catch (UiObjectNotFoundException e) {
                    fail("Cell not found at index " + i);
                }
            }

        }


    }



    //Test that the cancel button on the Custom sized puzzle selection dialog cancels the dialog
//    @Ignore("Working test")
    @Test
    public void testCustomSizedDialogCancelButton() {
        //Start new game
        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("New game button not found");
        }

        //Start custom sized puzzle
        UiObject customSizedButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/custom_size_button").className("android.widget.Button"));
        try {
            customSizedButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Custom size puzzle button not found");
        }

        //Start custom sized puzzle
        UiObject customSizedCancelButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/cancel_button").className("android.widget.Button"));
        try {
            customSizedCancelButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Custom size puzzle cancel button not found");
        }

        UiObject choosePuzzleModeFragment = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/choosePuzzleModeFragment").className("android.view.ViewGroup"));
        if (!choosePuzzleModeFragment.exists()) {
            fail("Choose Puzzle Mode Fragment was not displayed");
        }

    }

    //Test selecting exit option in the kebab menu on puzzle page closes the app
//    @Ignore("Working test")
    @Test
    public void testKebabExitButtonNavigation() {
        //Start new game
        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("New game button not found");
        }

        //Start classic puzzle
        UiObject classicPuzzleButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/classic_puzzle_button").className("android.widget.Button"));
        try {
            classicPuzzleButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Classic puzzle button not found");
        }

        UiObject kebabButton = ourDevice.findObject(new UiSelector().descriptionContains("More options").className("android.widget.ImageView"));
        try {
            kebabButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("kebab button not found");
        }

        UiObject exitButton = ourDevice.findObject(new UiSelector().textContains("Exit").className("android.widget.TextView"));
        try {
            exitButton.click();
            sleep(1000);
        } catch (UiObjectNotFoundException e) {
            fail("Exit button not found");
        }

        UiObject puzzlePage = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/puzzle_fragment").className("android.view.ViewGroup"));
        UiObject mainMenuScreen = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/mainMenuScreen").className("android.view.ViewGroup"));

        if (puzzlePage.exists() || mainMenuScreen.exists()) {
            fail("Exit button did not work");
        }
    }


    // Test that a user cannot override a permanent cell
//    @Ignore("Working test")
    @Test
    public void testCellNotOverridable() {
        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }

        UiObject classicPuzzleButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/classic_puzzle_button").className("android.widget.Button"));
        try {
            classicPuzzleButton.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }

        String permanentCellValue = "WRONG_VALUE";

        UiObject permanentCell = ourDevice.findObject(new UiSelector().descriptionContains("contains text").className("android.view.View"));
        try {
            permanentCellValue = permanentCell.getText();
            permanentCell.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
            fail();
        }

        UiObject button1 = ourDevice.findObject(new UiSelector().className("android.widget.Button").resourceId("com.echo.wordsudoku:id/id1"));
        try {
            button1.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
            fail();
        }

        try {
            assertTrue(permanentCell.getText().equals(permanentCellValue));
        } catch (UiObjectNotFoundException e) {
            fail();
        }
    }

}