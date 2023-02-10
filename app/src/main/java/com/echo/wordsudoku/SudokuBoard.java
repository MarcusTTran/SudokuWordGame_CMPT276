package com.echo.wordsudoku;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class SudokuBoard extends View {

    // The size of the board
    // This is the number of cells in each row and column
    // We are not going to use this for now
    // TODO: Use this to make the board dynamic
    private final int mBoardSize;

    // The background color of the board
    // It will be loaded from the XML layout (passed as an attribute) - custom:boardColor="#000000"
    private final int mBoardColor;
    // The color of the cells that are highlighted when a cell is selected.
    // It will be loaded from the XML layout (passed as an attribute) - custom:cellFillColor="#036507"
    private final int mCellFillColor;
    // The color of the cells that are highlighted when a cell is selected.
    // It will be loaded from the XML layout (passed as an attribute) - custom:cellsHighlightColor="#FF0000"
    private final int mCellsHighlightColor;
    // The color of the letters in the cells.
    // TODO: This will be used to display the letters in the cells that are not empty
    private final int mletterColor;
    private final int mletterColorSolve;


    // The Paint objects that will be used to draw the board
    // In the onDraw method, we will use these objects to draw the board
    // and there we set the color of the paint objects
    // here we just initialize them
    private final Paint mBoardColorPaint = new Paint();
    private final Paint mCellFillColorPaint = new Paint();
    private final Paint mCellsHighlightColorPaint = new Paint();
    private final Paint mLetterColorPaint = new Paint();
    private final Paint mLetterColorSolvePaint = new Paint();


    // This is a utility object that will be used to draw the letters in the cells
    // It is only used so that we can get the height of the letters. Nothing else.
    private final Rect letterPaintBounds = new Rect();

    // The size of each cell (they are square cells)
    // This is calculated in the onMeasure method
    private int cellSize;

    // The current cell that is selected
    // This is used to highlight the selected cell
    // It is initialized to -1 because we don't have any cell selected at the beginning
    private int currentCellRow = -1;
    private int currentCellColumn = -1;

    // This string 2D array should later on be linked to the model Board
    // It will be used to store the letters in the cells
    // TODO: Link this to the model Board
    private String[][] board = new String[9][9];


    // This is the constructor that is called when the view is created in the XML layout
    // It is used to load the attributes from the XML layout
    // The attributes are defined in the attrs.xml file
    // The attributes are then passed to the constructor as an AttributeSet object
    // We use the TypedArray object to get the attributes from the AttributeSet object
    // We then use the TypedArray object to get the attributes

    public SudokuBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.SudokuBoard,
                0, 0);

        // Try to get the attributes from the TypedArray object
        // If the attribute is not found, use the default value
        // and finally recycle the TypedArray object so we can empty the memory
        try {
            mBoardSize = a.getInteger(R.styleable.SudokuBoard_boardSize, 9);
            mBoardColor = a.getInteger(R.styleable.SudokuBoard_boardColor, 0);
            mCellFillColor = a.getInteger(R.styleable.SudokuBoard_cellFillColor, 0);
            mCellsHighlightColor = a.getInteger(R.styleable.SudokuBoard_cellsHighlightColor, 0);
            mletterColor = a.getInteger(R.styleable.SudokuBoard_letterColor, 0);
            mletterColorSolve = a.getInteger(R.styleable.SudokuBoard_letterColorSolve, 0);
        } finally {
            a.recycle();
        }
    }


    // Here we basically calculate the size of the view
    // We make the view a square and try to make it as big as possible
    @Override
    protected void onMeasure(int width, int height) {
        super.onMeasure(width, height);

        // Make the view a square by getting the minimum of the width and height
        int size = Math.min(this.getMeasuredWidth(), this.getMeasuredHeight());

        // Calculate the dimensions of each cell
        cellSize = size / 9;

        // Set the measured dimensions
        setMeasuredDimension(size, size);
    }


    // This method is called when the view is drawn
    // We use it to draw the board
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Set the color of the paint objects
        // We also set the style of the paint objects (stroke simply draws a line and fill draws a solid shape)

        // Draw the outer side of the board (the big square that contains all of the cells)
        mBoardColorPaint.setStyle(Paint.Style.STROKE);
        mBoardColorPaint.setStrokeWidth(16);
        mBoardColorPaint.setColor(mBoardColor);
        mBoardColorPaint.setAntiAlias(true);

        // Draw the outer side of the board
        canvas.drawRect(0, 0, getWidth(), getHeight(), mBoardColorPaint);

        // Set the color of the single cell that is highlighted when it is selected
        // Set the style of the paint object to fill
        mCellFillColorPaint.setStyle(Paint.Style.FILL);
        mBoardColorPaint.setAntiAlias(true);
        mCellFillColorPaint.setColor(mCellFillColor);

        // Set the color of the cells that are highlighted when a cell is selected
        // Set the style of the paint object to fill
        mCellsHighlightColorPaint.setStyle(Paint.Style.FILL);
        mBoardColorPaint.setAntiAlias(true);
        mCellsHighlightColorPaint.setColor(mCellsHighlightColor);

        // TODO: Make the letter color dynamic
        // mLetterColorPaint.setColor(mletterColor);
        // mLetterColorSolvePaint.setColor(mletterColorSolve);

        // This will draw the selected cell and the highlighted column and rows
        colorCell(canvas, currentCellRow, currentCellColumn);

        // This will draw the board, the rows and the columns' lines
        drawBoard(canvas);

        // This will draw the words in the cells
        drawWord(canvas);
    }


    // This method is called when the user touches the screen
    // We use it to get the coordinates of the touch event
    // We then use the coordinates to get the row and column of the cell that was touched
    // We then use the row and column to highlight the selected cell
    // We also use the row and column to highlight the selected column and row
    // We then invalidate the view so that it is redrawn
    // We return true if the touch event was valid and false if it was not

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean isValid;
        float x = event.getX();
        float y = event.getY();

        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN) {
            currentCellColumn = (int) Math.ceil(x / cellSize);
            currentCellRow = (int) Math.ceil(y / cellSize);
            isValid = true;
        } else {
            isValid = false;
        }

        return isValid;
    }

    // This method is used to highlight the selected cell and the selected column and row
    // r is the row of the selected cell
    // c is the column of the selected cell
    // We first check if actually a cell is selected
    private void colorCell(Canvas canvas, int r, int c) {

        // If a cell is selected
        // Highlight the selected column
        // Highlight the selected row
        // Highlight the selected cell
        // If not, nothing will be highlighted (this will happen when the user first opens the puzzle)
        if (currentCellColumn != -1 && currentCellRow != -1) {
            // Highlight the selected column
            canvas.drawRect((c-1)*cellSize,0,c*cellSize,cellSize*9,mCellsHighlightColorPaint);
            // Highlight the selected row
            canvas.drawRect(0,(r-1)*cellSize,9*cellSize,r*cellSize,mCellsHighlightColorPaint);
            // Highlight the selected cell, different color than the previous 2
            canvas.drawRect((c-1)*cellSize,(r-1)*cellSize,c*cellSize,r*cellSize,mCellFillColorPaint);
        }
        invalidate();
    }

    // This method is used to draw the thick lines that separate the 3x3 squares
    // They only set the width of the line and don't draw anything
    private void drawThickLines() {
        mBoardColorPaint.setStyle(Paint.Style.STROKE);
        // Here we set the width of the line which is 10 (thicker than the thin lines : 4)
        mBoardColorPaint.setStrokeWidth(10);
        mBoardColorPaint.setColor(mBoardColor);
    }

    // This method is used to draw the thin lines that separate the cells
    // They only set the width of the line and don't draw anything
    private void drawThinLines() {
        mBoardColorPaint.setStyle(Paint.Style.STROKE);
        // Here we set the width of the line which is 4 (thinner than the thick lines : 10)
        mBoardColorPaint.setStrokeWidth(4);
        mBoardColorPaint.setColor(mBoardColor);
    }

    // This method is used to draw the board using thick and thin lines
    // This draws all of the inner lines of the board
    //TODO: Draw the board with custom sizes. Currently only draws a 9x9 board
    private void drawBoard(Canvas canvas) {
        // Draw the column lines
        for (int c = 0; c < 10; c++) {
            if (c % 3 == 0) {
                // If the column is a multiple of 3, draw a thick line because it is in the 3x3 square
                drawThickLines();
            } else {
                drawThinLines();
            }
            // Draw the line, previous line was just setting the width
            canvas.drawLine(c * cellSize, 0, c * cellSize, getHeight(), mBoardColorPaint);
        }

        // Draw the row lines
        for (int r = 0; r < 10; r++) {
            if (r % 3 == 0) {
                // If the column is a multiple of 3, draw a thick line because it is in the 3x3 square
                drawThickLines();
            } else {
                drawThinLines();
            }
            // Draw the line, previous line was just setting the width
            canvas.drawLine(0, r*cellSize, getWidth(), r*cellSize, mBoardColorPaint);
        }
    }

    // This method is used to draw the words in the cells
    // It uses the board array to get the words
    // It uses the cellSize to get the size of the cell
    // It calculates the width and height of the word to center it
    // It uses utility methods to set the font of the text in a way that all of the words fit in the cell
    private void drawWord(Canvas canvas) {
        for (int r=0; r<9; r++) {
            for (int c=0; c<9; c++) {
                if (board[r][c] != null){
                    String word = board[r][c];
                    float width, height;
                    setTextSizeForWidth(mLetterColorPaint, cellSize-15, word);
                    // We need to get the bounds of the word to center it
                    mLetterColorPaint.getTextBounds(word, 0, word.length(), letterPaintBounds);
                    width = mLetterColorPaint.measureText(word);
                    height = letterPaintBounds.height();
                    canvas.drawText(word,(c*cellSize)+((cellSize-width))/2,(r*cellSize+cellSize)-((cellSize-height)/2),mLetterColorPaint);
                }
            }
        }
    }

    // We have variable length words, some are short (Apple) and some are long (Pineapple)
    // Method below will set the text size for the word to fit the cell so all of the words are the same size
    // Taken from: http://stackoverflow.com/questions/2617266/how-to-adjust-text-font-size-to-fit-textview

    private static void setTextSizeForWidth(Paint paint, float desiredWidth,
                                            String text) {


        final float testTextSize = 48f;

        // Get the bounds of the text, using our testTextSize.
        paint.setTextSize(testTextSize);
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);

        // Calculate the desired size as a proportion of our testTextSize.
        float desiredTextSize = testTextSize * desiredWidth / bounds.width();

        // Set the paint for that size.
        paint.setTextSize(desiredTextSize);
    }

    // Method to fill the word in the board
    // It is called externally from PuzzleActivity
    // whenever user presses the "Enter" button,
    // this method is called to fill the word in the board
    // it uses the member variables currentCellRow and currentCellColumn
    // to get the current selected cell's position
    // returns true if the word was filled successfully
    // returns false if the word was not filled successfully (User had not selected any word at the beginning or there was any other error)
    public boolean fillWord(String s) {
        if (currentCellColumn != -1 && currentCellRow != -1) {
            board[currentCellRow - 1][currentCellColumn - 1] = s;
            invalidate();
            return true;
        }
        else {
            return false;
        }
    }

}