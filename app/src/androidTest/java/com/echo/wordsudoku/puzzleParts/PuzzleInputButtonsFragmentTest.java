package com.echo.wordsudoku.puzzleParts;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

// UI Test class for the PuzzleInputButtonsFragment class (Makes up the PuzzleFragment class)

//For more information: https://developer.android.com/training/testing/other-components/ui-automator

@RunWith(AndroidJUnit4.class)
public class PuzzleInputButtonsFragmentTest {


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






    //Test that all buttons are displayed correctly in a puzzle and that clicking
    // them displays the correct button text in an empty cell
//    @Ignore("Working test")
    @Test
    public void testAllInputButtonCellDisplayMatch() {
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

    //Test that all buttons are displayed correctly in a puzzle in a horizontal orientation and that clicking
    // them displays the correct button text in an empty cell
//    @Ignore("Working test")
    @Test
    public void testAllInputButtonCellDisplayMatchHorizontal() {
        try {
            ourDevice.setOrientationLeft();
            ourDevice.waitForWindowUpdate(null, 3000);
        } catch (android.os.RemoteException e) {
            fail("Horizontal orientation failed");
        }
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


    //Test that all input buttons are displayed in the puzzle fragment in all puzzle sizes
//    @Ignore("Working test")
    @Test
    public void testInputButtonsDisplaysAllSizes() {
        for (int i = 0; i < puzzleSizes.length; i++) {
            testInputButtonsDisplayedHelper(puzzleSizes[i]);
        }
    }



    public void testInputButtonsDisplayedHelper(int dim) {
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

        int totalButtonLayouts = 0;
        int eachInputButtons = 0;
        UiObject inputButtons = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/board_input_buttons").className("android.widget.LinearLayout"));
        try {
            for(int i = 0; i < inputButtons.getChildCount(); i++) {
                UiObject buttonLayouts = inputButtons.getChild(new UiSelector().instance(i));
                totalButtonLayouts = inputButtons.getChildCount();

                for (int z = 0; z < buttonLayouts.getChildCount(); z++) {
                    eachInputButtons = buttonLayouts.getChildCount();
                }
                if (!buttonLayouts.exists()) {
                    fail("All buttons layouts are not displayed on Puzzle size " + dim);
                }

            }
        } catch (UiObjectNotFoundException e) {
            fail("Input buttons are not displayed on Puzzle size " + dim);
        }

        if (totalButtonLayouts * eachInputButtons != dim) {
            Log.d(INSTRUTEST, "DIM: " + dim + " totalButtonCount: " + totalButtonLayouts * eachInputButtons);
            fail("Not all buttons were displayed");
        }

        ourDevice.pressBack();

        UiObject noButton = ourDevice.findObject(new UiSelector().resourceId("android:id/button2").className("android.widget.Button"));
        try {
            noButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("No button was not displayed on Puzzle size " + dim);
        }
    }

    //Test that all input buttons are displayed in the puzzle fragment in all puzzle sizes horizontally
//    @Ignore("Working test")
    @Test
    public void testInputButtonsDisplaysHorizontalAllSizes() {
        try {
            ourDevice.setOrientationLeft();
            ourDevice.waitForWindowUpdate(null, 3000);
        } catch (android.os.RemoteException e) {
            fail("Horizontal orientation failed");
        }
        for (int i = 0; i < puzzleSizes.length; i++) {
            testInputButtonsDisplayedHelper(puzzleSizes[i]);
        }
    }



    public void testInputButtonsDisplayedHorizontalHelper(int dim) {
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

        int totalButtonLayouts = 0;
        int eachInputButtons = 0;
        UiObject inputButtons = ourDevice.findObject(new UiSelector().resourceId("com.echo.wordsudoku:id/board_input_buttons").className("android.widget.LinearLayout"));
        try {
            for(int i = 0; i < inputButtons.getChildCount(); i++) {
                UiObject buttonLayouts = inputButtons.getChild(new UiSelector().instance(i));
                totalButtonLayouts = inputButtons.getChildCount();

                for (int z = 0; z < buttonLayouts.getChildCount(); z++) {
                    eachInputButtons = buttonLayouts.getChildCount();
                }
                if (!buttonLayouts.exists()) {
                    fail("All buttons layouts are not displayed on Puzzle size " + dim);
                }

            }
        } catch (UiObjectNotFoundException e) {
            fail("Input buttons are not displayed on Puzzle size " + dim);
        }

        if (totalButtonLayouts * eachInputButtons != dim) {
            Log.d(INSTRUTEST, "DIM: " + dim + " totalButtonCount: " + totalButtonLayouts * eachInputButtons);
            fail("Not all buttons were displayed");
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


