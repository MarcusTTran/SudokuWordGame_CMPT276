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

// UI Test class for the PuzzleResultFragment class


//For more information: https://developer.android.com/training/testing/other-components/ui-automator

@RunWith(AndroidJUnit4.class)
public class PuzzleResultFragmentTest {

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


    //Test the puzzle result fragment is displayed correctly
//    @Ignore("Working test")
    @Test
    public void testPuzzleResultsDisplays() {
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
        if (!mainMenuButton.exists()) {
            fail("Main Menu return button not displayed");
        }

        UiObject retryPuzzleButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/result_retry_puzzle_button").className("android.widget.Button"));
        if (!retryPuzzleButton.exists()) {
            fail("retry puzzle button was not displayed");
        }

        UiObject resultImage = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/result_image_view").className("android.widget.ImageView"));
        if (!resultImage.exists()) {
            fail("Image result was not displayed");
        }

        UiObject winLossMessage = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/puzzle_result_message_text_view"));
        if (!winLossMessage.exists()) {
            fail("win loss message was not displayed");
        }

        UiObject puzzleTimerDifficulty = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/puzzle_result_timer_text_view"));
        if (!puzzleTimerDifficulty.exists()) {
            fail("Puzzle win or difficulty was not displayed");
        }



    }

    //Test that the main menu button at the puzzle results page correctly takes the user back to the main
    // menu
//    @Ignore("Working test")
    @Test
    public void testPuzzleResultsDisplaysHorizontal() {
        try {
            ourDevice.setOrientationLeft();
            ourDevice.waitForWindowUpdate(null, 3000);
        } catch (android.os.RemoteException e) {
            fail();
        }

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
        if (!mainMenuButton.exists()) {
            fail("Main Menu return button not displayed");
        }

        UiObject retryPuzzleButton = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/result_retry_puzzle_button").className("android.widget.Button"));
        if (!retryPuzzleButton.exists()) {
            fail("retry puzzle button was not displayed");
        }

        UiObject resultImage = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/result_image_view").className("android.widget.ImageView"));
        if (!resultImage.exists()) {
            fail("Image result was not displayed");
        }

        UiObject winLossMessage = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/puzzle_result_message_text_view"));
        if (!winLossMessage.exists()) {
            fail("win loss message was not displayed");
        }

        UiObject puzzleTimerDifficulty = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/puzzle_result_timer_text_view"));
        if (!puzzleTimerDifficulty.exists()) {
            fail("Puzzle win or difficulty was not displayed");
        }

    }

    //Test that the main menu button at the puzzle results page correctly takes the user back to the main
    // menu
//    @Ignore("Working test")
    @Test
    public void testPuzzleResultsBackButtonNavigation() {
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

        UiObject actionBar = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/action_bar_container"));
        try {
            UiObject viewGroup = actionBar.getChild(new UiSelector().instance(0));
            viewGroup.getChild(new UiSelector().instance(0)).click();
        } catch (UiObjectNotFoundException e) {
            fail("Back button was not displayed");
        }


    }


}

