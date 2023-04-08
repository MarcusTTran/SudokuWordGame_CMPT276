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

import java.util.ArrayList;
import java.util.List;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

//For more information: https://developer.android.com/training/testing/other-components/ui-automator

@RunWith(AndroidJUnit4.class)
public class PuzzleBoardFragmentTest {


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

    //Test that empty cells are overridable
    @Test
    public void testCellOverridable() {
        int totalCells = 9 * 9;

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

        try {
            UiObject emptyCell = sudokuBoard.getChild(new UiSelector().instance(emptyCellIndex));
            emptyCell.click();
        } catch (UiObjectNotFoundException e) {
            fail("No empty cell found");
        }

        UiObject button1 = ourDevice.findObject(new UiSelector().className("android.widget.Button").resourceId("com.echo.wordsudoku:id/id1"));

        String inputButtonString = "";
        try {
            inputButtonString = button1.getText();
            button1.click();
        } catch (UiObjectNotFoundException e) {
            fail("No input button found");
        }

        ourDevice.pressBack();
        ourDevice.pressBack();

        try {
            UiObject emptyCell = sudokuBoard.getChild(new UiSelector().instance(emptyCellIndex));

            assertTrue(emptyCell.getText().equals(inputButtonString.toLowerCase()));
        } catch (UiObjectNotFoundException e) {
            fail("Empty cell text did not match inputted button string");
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

    //Test that the board displays all cells
    @Test
    public void testBoardDisplaysAllCells() {
        for (int i = 0; i < puzzleSizes.length; i++) {
            testBoardDisplaysAllCellsHorizontalHelper(puzzleSizes[i]);
        }
    }

    public void testBoardDisplaysAllCellsHelper(int dim) {

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

        ourDevice.pressBack();

        UiObject noButton = ourDevice.findObject(new UiSelector().resourceId("android:id/button2").className("android.widget.Button"));
        try {
            noButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("No button was not displayed on Puzzle size " + dim);
        }
    }


    //Test that the board displays all cells on horizontal landscape
    @Test
    public void testPuzzleBoardFragmentDisplaysHorizontalAllCells() {
        try {
            ourDevice.setOrientationLeft();
            ourDevice.waitForWindowUpdate(null, 3000);
        } catch (android.os.RemoteException e) {
            fail();
        }
        for (int i = 0; i < puzzleSizes.length; i++) {
            testBoardDisplaysAllCellsHorizontalHelper(puzzleSizes[i]);
        }
    }

    public void testBoardDisplaysAllCellsHorizontalHelper(int dim) {

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

        ourDevice.pressBack();

        UiObject noButton = ourDevice.findObject(new UiSelector().resourceId("android:id/button2").className("android.widget.Button"));
        try {
            noButton.click();
        } catch (UiObjectNotFoundException e) {
            fail("No button was not displayed on Puzzle size " + dim);
        }
    }




}


