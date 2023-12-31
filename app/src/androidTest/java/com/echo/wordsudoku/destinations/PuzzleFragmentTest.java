package com.echo.wordsudoku.destinations;

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

// UI Test class for the PuzzleFragment class


//For more information: https://developer.android.com/training/testing/other-components/ui-automator

@RunWith(AndroidJUnit4.class)
public class PuzzleFragmentTest {

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







    //Test that the puzzle page displays all necessary buttons and TextViews to the user horizontally
//    @Ignore("Working test")
    @Test
    public void testPuzzleFragmentDisplaysHorizontalAllPuzzleSizes() {
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

        //Test that board is displayed
        UiObject sudokuBoard = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/sudoku_board").className("android.view.View"));
        if (!sudokuBoard.exists()) {
            fail("Sudoku Board was not displayed on Puzzle size " + dim);
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


    //Test that the Puzzle Results win display is correctly shown on correct solution
//    @Ignore("Working test")
    @Test
    public void testPuzzleResultsWinNavigation() {
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



    //Test that puzzle results loss page is correctly displayed on puzzle loss
//    @Ignore("Working test")
    @Test
    public void testPuzzleResultsLossNavigation() {
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

    //Test that puzzle results are displayed correctly in horizontal orientation
//    @Ignore("Working test")
    @Test
    public void testPuzzleResultsDisplayHorizontalNavigation() {
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

    //Test that the garbage button correctly clears a cell in the puzzle fragment page
    @Test
    public void testGarbageButtonErasesCell() {
        int totalCells = 4 * 4;

        UiObject newGameButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/new_game_button").className("android.widget.Button"));
        try {
            newGameButton.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
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

        int emptyCellIndex = -1;

        for (int i = 0; i < totalCells; i++) {
            try {
                UiObject someCell = sudokuBoard.getChild(new UiSelector().instance(i));
                if (someCell.getText().equals("EMPTYCELL")) {
                    emptyCellIndex = i;
                }
            } catch (UiObjectNotFoundException e) {
                fail("All cells not found");
            }
        }

        //Click on an empty cell
        try {
            UiObject emptyCell = sudokuBoard.getChild(new UiSelector().instance(emptyCellIndex));
            emptyCell.click();
        } catch (UiObjectNotFoundException e) {
            fail("No empty cell found");
        }

        //Click to enter word
        UiObject button1 = ourDevice.findObject(new UiSelector().className("android.widget.Button").resourceId("com.echo.wordsudoku:id/id1"));
        try {
            button1.click();
        } catch (UiObjectNotFoundException e) {
            fail("No input button found");
        }

        ourDevice.pressBack();
        ourDevice.pressBack();

        //Cell should not be empty
        try {
            UiObject emptyCellNowFilled = sudokuBoard.getChild(new UiSelector().instance(emptyCellIndex));
            if (emptyCellNowFilled.getText().equals("EMPTYCELL")) {
                fail("Cell did not fill with word");
            }
        } catch (UiObjectNotFoundException e) {
            fail("Empty cell that we filled was not found");
        }

        //Click erase button
        UiObject garbageButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/options_clear_button"));
        try {
            garbageButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("No garbage button found");
        }

        ourDevice.pressBack();
        ourDevice.pressBack();

        //Cell should now be empty
        try {
            UiObject emptyCellNowFilled = sudokuBoard.getChild(new UiSelector().instance(emptyCellIndex));
            if (!emptyCellNowFilled.getText().equals("EMPTYCELL")) {
                fail("Garbage button did not erase word from cell");
            }
        } catch (UiObjectNotFoundException e) {
            fail("Empty cell that we filled was not found");
        }

    }


        //Test that clicking on the 3 dots (kebab button) opens up a dialog options
//    @Ignore("Working test")
    @Test
    public void testKebabButtonPuzzleFragmentDisplays() {
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

    //Test that the kebab menu keeps users on the puzzle fragment page if the user tries to complete with
    // an unfilled board
    @Test
    public void testKebabDoneButtonUnfilledBoard() {
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

        UiObject doneButton = ourDevice.findObject(new UiSelector().textContains("Done").className("android.widget.TextView"));
        try {
            doneButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Done button not found");
        }

        UiObject puzzleFragment = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/puzzle_fragment"));
        if (!puzzleFragment.exists()) {
            fail("Puzzle fragment did not appear after user pressed done through kebab menu on unfilled board");
        }

    }

    //Test that the kebab menu keeps user to the puzzle results page after clicking done on
    // completely filled board
    @Test
    public void testKebabDoneButtonFilledBoard() {
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

        UiObject kebabButton = ourDevice.findObject(new UiSelector().descriptionContains("More options").className("android.widget.ImageView"));
        try {
            kebabButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("kebab button not found");
        }

        UiObject doneButton = ourDevice.findObject(new UiSelector().textContains("Done").className("android.widget.TextView"));
        try {
            doneButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("Done button not found");
        }

        UiObject puzzleResultsFragment = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/ResultsFragmentPage"));
        if (!puzzleResultsFragment.exists()) {
            fail("Puzzle results fragment did not appear after user pressed done through kebab menu on completely filled board");
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
    public void testPuzzleFragmentDisplayAllPuzzleSizes() {
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

        //Test that board is displayed
        UiObject sudokuBoard = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/sudoku_board").className("android.view.View"));
        if (!sudokuBoard.exists()) {
            fail("Sudoku Board was not displayed on Puzzle size " + dim);
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



}

