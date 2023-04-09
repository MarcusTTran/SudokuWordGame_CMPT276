package com.echo.wordsudoku.puzzleParts;

import static android.os.SystemClock.sleep;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
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

// UI Test class for the PuzzleTopMenuBarFragment class (Makes up the PuzzleFragment class)


//For more information: https://developer.android.com/training/testing/other-components/ui-automator

@RunWith(AndroidJUnit4.class)
public class PuzzleTopMenuBarFragmentTest {


    //Warning: These tests take up to 10 minutes to complete

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



    //Test that rules dialog is correctly displayed on click
    @Test
    public void testRulesButtonNavigation() {
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

        UiObject rulesPopUpDialog = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/PopUp_Rules"));
        if (!rulesPopUpDialog.exists()) {
            fail("Rules pop up box was not displayed");
        }
    }

    //Test that the timer is correctly displayed on top menu bar of puzzle fragment
//    @Ignore("Working test")
    @Test
    public void testTimerDifficultyDisplay() {
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



    //Test that dictionary dialog is correctly displayed on click
    @Test
    public void testDictionaryButtonNavigation() {
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
        try {
            dictionaryButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Dictionary button was not displayed");
        }

        UiObject dictionaryPopup = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/PopUp_DictionaryBox"));
        if (!dictionaryPopup.exists()) {
            fail("Dictionary pop up box was not displayed");
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

        ourDevice.pressBack();
        ourDevice.pressBack();


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



}

