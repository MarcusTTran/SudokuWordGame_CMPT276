package com.echo.wordsudoku.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.widget.ExploreByTouchHelper;

import com.echo.wordsudoku.R;

import java.util.List;


// using tutorial from https://www.youtube.com/watch?v=lYjSl_ou05Q
// they implement a sudoku solver app and I am using their code to implement the sudoku board View component

public class SudokuBoard extends View {

    private SudokuBoardTouchHelper mTouchHelper;

    // The size of the board
    // This is the number of cells in each row and column
    // We are not going to use this for now
    // TODO: Use this to make the board dynamic
    private int mBoardSize;

    private OnCellTouchListener mOnCellTouchListener;
    private int mBoxHeight;
    private int mBoxWidth;
    // This is the default size of the board
    private final int DEFAULT_BOARD_SIZE = 9;


    // The padding for each cell. This is the space between the cell and the text inside it
    private final int mCellVerticalPadding;
    // This is the default vertical padding for the cells
    private final int DEFAULT_CELL_VERTICAL_PADDING = 35;
    private final int mCellHorizontalPadding;
    // This is the default horizontal padding for the cells
    private final int DEFAULT_CELL_HORIZONTAL_PADDING = 25;

    // The maximum font size for the letters in the cells. The font size will not be bigger than this
    private final int mCellMaxFontSize;
    // This is the default maximum font size for the letters in the cells
    private final int DEFAULT_CELL_MAX_FONT_SIZE = 60;

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
    private final int mTextColor;

    // The Paint objects that will be used to draw the board
    // In the onDraw method, we will use these objects to draw the board
    // and there we set the color of the paint objects
    // here we just initialize them
    private final Paint mBoardColorPaint = new Paint();
    private final Paint mCellFillColorPaint = new Paint();
    private final Paint mCellsHighlightColorPaint = new Paint();
    private final Paint mLetterColorPaint = new Paint();


    // This is a utility object that will be used to draw the letters in the cells
    // It is only used so that we can get the height of the letters. Nothing else.
    private final Rect letterPaintBounds = new Rect();

    // The size of each cell (they are square cells)
    // This is calculated in the onMeasure method
    private int cellSize;

    private int size;

    // The current cell that is selected
    // This is used to highlight the selected cell
    // It is initialized to -1 because we don't have any cell selected at the beginning
    private int currentCellRow = -1;
    private int currentCellColumn = -1;

    // This string 2D array should later on be linked to the model Board
    // It will be used to store the letters in the cells
    // TODO: Link this to the model Board
    private String[][] board;


    // This is the constructor that is called when the view is created in the XML layout
    // It is used to load the attributes from the XML layout
    // The attributes are defined in the attrs.xml file
    // The attributes are then passed to the constructor as an AttributeSet object
    // We use the TypedArray object to get the attributes from the AttributeSet object
    // We then use the TypedArray object to get the attributes

    public SudokuBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mTouchHelper = new SudokuBoardTouchHelper(this);
        ViewCompat.setAccessibilityDelegate(this, mTouchHelper);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.SudokuBoard,
                0, 0);

        // Try to get the attributes from the TypedArray object
        // If the attribute is not found, use the default value
        // and finally recycle the TypedArray object so we can empty the memory
        try {
            mBoardSize = a.getInteger(R.styleable.SudokuBoard_boardSize, DEFAULT_BOARD_SIZE);
            mBoxHeight = a.getInteger(R.styleable.SudokuBoard_boxHeight, 3);
            mBoxWidth = a.getInteger(R.styleable.SudokuBoard_boxWidth, 3);
            mBoardColor = a.getInteger(R.styleable.SudokuBoard_boardColor, 0);
            mCellFillColor = a.getInteger(R.styleable.SudokuBoard_cellFillColor, 0);
            mCellsHighlightColor = a.getInteger(R.styleable.SudokuBoard_cellsHighlightColor, 0);
            mTextColor = a.getInteger(R.styleable.SudokuBoard_textColor, 0);
            mCellVerticalPadding = a.getInteger(R.styleable.SudokuBoard_cellVerticalPadding, DEFAULT_CELL_VERTICAL_PADDING);
            mCellHorizontalPadding = a.getInteger(R.styleable.SudokuBoard_cellHorizontalPadding, DEFAULT_CELL_HORIZONTAL_PADDING);
            mCellMaxFontSize = a.getInteger(R.styleable.SudokuBoard_cellMaxFontSize, DEFAULT_CELL_MAX_FONT_SIZE);
        } finally {
            a.recycle();
        }
        board = new String[mBoardSize][mBoardSize];
    }

    // With this method we can set the board size and the box size to different values
    // This is used to make the board dynamic
    // @param boardSize - the size of the board (the number of rows and columns)
    // @param boxHeight - the height of the box (the number of rows in the box)
    // @param boxWidth - the width of the box (the number of columns in the box)
    public void setNewPuzzleDimensions(int boardSize, int boxHeight, int boxWidth) {
        this.mBoardSize = boardSize;
        this.mBoxHeight = boxHeight;
        this.mBoxWidth = boxWidth;
        this.cellSize = this.size / mBoardSize;
        this.setBoard(new String[mBoardSize][mBoardSize]);
        this.currentCellColumn = this.currentCellRow = -1;
    }


    @Override
    public boolean dispatchHoverEvent(MotionEvent event) {
        // Always attempt to dispatch hover events to accessibility first.
        if (mTouchHelper.dispatchHoverEvent(event)) {
            return true;
        }

        return super.dispatchHoverEvent(event);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return mTouchHelper.dispatchKeyEvent(event)
                || super.dispatchKeyEvent(event);
    }

    @Override
    public void onFocusChanged(boolean gainFocus, int direction,
                               Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        mTouchHelper.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
    }

    // Here we basically calculate the size of the view
    // We make the view a square and try to make it as big as possible
    @Override
    protected void onMeasure(int width, int height) {
        super.onMeasure(width, height);

        // Make the view a square by getting the minimum of the width and height
        int size = Math.min(this.getMeasuredWidth(), this.getMeasuredHeight());

        // Calculate the dimensions of each cell
        cellSize = size / mBoardSize;

        this.size = size;

        // Set the measured dimensions
        setMeasuredDimension(size, size);
    }


    // This method is called when the view is drawn
    // We use it to draw the board
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawOutsideBorder(canvas);
        // This will draw the selected cell and the highlighted column and row
        colorCell(canvas, this.currentCellRow, this.currentCellColumn);

        drawInnerLinesBoard(canvas);

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

    private void drawOutsideBorder(Canvas canvas) {
        // Draw the outer side of the board (the big square that contains all of the cells)
        mBoardColorPaint.setStyle(Paint.Style.STROKE);
        mBoardColorPaint.setStrokeWidth(16);
        mBoardColorPaint.setColor(mBoardColor);
        mBoardColorPaint.setAntiAlias(true);
        // Draw the outer side of the board
        canvas.drawRect(0, 0, getWidth(), getHeight(), mBoardColorPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean isValid;
        float x = event.getX();
        float y = event.getY();

        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN) {
            onCellTouched((int) Math.ceil(y / cellSize),(int) Math.ceil(x / cellSize));
            isValid = true;
        } else {
            isValid = false;
        }

        return isValid;
    }

    private void onCellTouched(int row, int column) {
        currentCellRow = row;
        currentCellColumn = column;
        if (mOnCellTouchListener != null)
            mOnCellTouchListener.onCellTouched(board[row-1][column-1],row, column);
        mTouchHelper.sendEventForVirtualView((row-1)*mBoardSize+column-1, AccessibilityEvent.TYPE_VIEW_CLICKED);
    }

    // This method is used to highlight the selected cell and the selected column and row
    // r is the row of the selected cell
    // c is the column of the selected cell
    // We first check if actually a cell is selected
    private void colorCell(Canvas canvas, int r, int c) {


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

        // If a cell is selected
        // Highlight the selected column
        // Highlight the selected row
        // Highlight the selected cell
        // If not, nothing will be highlighted (this will happen when the user first opens the puzzle)
        if (currentCellColumn != -1 && currentCellRow != -1) {
            // Highlight the selected column
            canvas.drawRect((c-1)*cellSize,0,c*cellSize,cellSize*mBoardSize,mCellsHighlightColorPaint);
            // Highlight the selected row
            canvas.drawRect(0,(r-1)*cellSize,mBoardSize*cellSize,r*cellSize,mCellsHighlightColorPaint);
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
    // @param: canvas is the canvas on which the board is drawn
    //TODO: Draw the board with custom sizes. Currently only draws a 9x9 board
    private void drawInnerLinesBoard(Canvas canvas) {
        // Draw the column lines
        for (int c = 0; c < mBoardSize+1; c++) {
            if (c % mBoxWidth == 0) {
                // If the column is a multiple of 3, draw a thick line because it is in the 3x3 square
                drawThickLines();
            } else {
                drawThinLines();
            }
            // Draw the line, previous line was just setting the width
            canvas.drawLine(c * cellSize, 0, c * cellSize, getHeight(), mBoardColorPaint);
        }

        // Draw the row lines
        for (int r = 0; r < mBoardSize; r++) {
            if (r % mBoxHeight == 0) {
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
        //Set the color of the letters
        mLetterColorPaint.setColor(mTextColor);


        final int desiredHeightForEachWord = cellSize-mCellVerticalPadding;
        final int desiredWidthForEachWord = cellSize-mCellHorizontalPadding;
        final int maximumLetterFontSize = mCellMaxFontSize;
        for (int r=0; r<mBoardSize; r++) {
            for (int c=0; c<mBoardSize; c++) {
                if (board[r][c] != null){
                    String word = board[r][c];
                    float width, height;
                    setTextSize(mLetterColorPaint, desiredHeightForEachWord, desiredWidthForEachWord, word,maximumLetterFontSize);
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
    // @param: paint is the paint object that will be used to draw the word
    // @param: desiredHeight is the height constraint of the cell
    // @param: desiredWidth is the width constraint of the cell
    // @param: text is the word that will be drawn
    // @param: maxTextSize is the maximum size of the text (60)

    private static void setTextSize(Paint paint, float desiredHeight, float desiredWidth,
                                    String text, int maxTextSize) {


        final float testTextSize = 20f;

        // Get the bounds of the text, using our testTextSize.
        paint.setTextSize(testTextSize);
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);

        // Calculate the desired size as a proportion of our testTextSize.
        float desiredTextSizeHeightConstraint = testTextSize * desiredHeight / bounds.height();
        float desiredTextSizeWidthConstraint = testTextSize * desiredWidth / bounds.width();

        // Set the paint for that size.
        paint.setTextSize(Math.min(desiredTextSizeHeightConstraint, Math.min(maxTextSize,desiredTextSizeWidthConstraint)));
    }


    // Method to set the board layout of the view to a custom board
    // It is called externally from PuzzleActivity
    // It will load the board initially when the user opens the puzzle using the unsolved puzzle from Board model.
    // Also used for testing purposes
    public void setBoard(String[][] board) {
        if (board.length != mBoardSize || board[0].length != mBoardSize) {
            throw new IllegalArgumentException("Board size must be " + mBoardSize + "x" + mBoardSize);
        }
        for (int r=0; r<mBoardSize; r++) {
            for (int c=0; c<mBoardSize; c++) {
                this.board[r][c] = board[r][c];
            }
        }
    }

    // Rows and columns are 1 indexed
    public void setWord(int row, int column, String word) {
        if (row < 1 || row > mBoardSize || column < 1 || column > mBoardSize)
            throw new IllegalArgumentException("Invalid row or column. Valid range for this board is 1-" + mBoardSize);
        board[row][column] = word;
    }

    public void setOnCellTouchListener(OnCellTouchListener listener) {
        this.mOnCellTouchListener = listener;
    }


    // Methods to get the current selected cell's position (row and column)
    public int getCurrentCellRow() {
        return currentCellRow;
    }
    public int getCurrentCellColumn() {
        return currentCellColumn;
    }

    private class SudokuBoardTouchHelper extends ExploreByTouchHelper {

        public SudokuBoardTouchHelper(View forView) {
            super(forView);
        }

        @Override
        protected int getVirtualViewAt(float x, float y) {
            int row = (int) (y / cellSize);
            int column = (int) (x / cellSize);
            if (row >= mBoardSize || column >= mBoardSize) {
                return INVALID_ID;
            }
            return row * mBoardSize + column;
        }

        @Override
        protected void getVisibleVirtualViews(List<Integer> virtualViewIds) {
            for (int i = 0; i < mBoardSize * mBoardSize; i++) {
                virtualViewIds.add(i);
            }
        }

        @Override
        protected void onPopulateEventForVirtualView(int virtualViewId, @NonNull AccessibilityEvent event) {
            super.onPopulateEventForVirtualView(virtualViewId, event);
            if (virtualViewId >= mBoardSize * mBoardSize) {
                throw new IllegalArgumentException("Invalid virtual view id");
            }
            int row = getRowColumnForVirtualViewId(virtualViewId)[0];
            int column = getRowColumnForVirtualViewId(virtualViewId)[1];
            event.setContentDescription("Cell [" + (row+1) + "] [" + (column+1)+"] contains "+board[row][column]);
        }

        @Override
        protected void onPopulateNodeForVirtualView(int virtualViewId, AccessibilityNodeInfoCompat node) {
            if (virtualViewId >= mBoardSize * mBoardSize) {
                throw new IllegalArgumentException("Invalid virtual view id");
            }
            int row = getRowColumnForVirtualViewId(virtualViewId)[0];
            int column = getRowColumnForVirtualViewId(virtualViewId)[1];
            node.setContentDescription("Cell [" + (row+1) + "] [" + (column+1)+"] contains "+board[row][column]);
            if (currentCellRow-1 == row && currentCellColumn-1 == column) {
                node.setSelected(true);
            } else {
                node.setSelected(false);
            }
            node.setBoundsInParent(new Rect(column * cellSize, row * cellSize, (column + 1) * cellSize, (row + 1) * cellSize));
            node.addAction(AccessibilityNodeInfoCompat.ACTION_CLICK);
        }

        @Override
        protected boolean onPerformActionForVirtualView(int virtualViewId, int action, Bundle arguments) {
            if (action == AccessibilityNodeInfoCompat.ACTION_CLICK) {
                int row = virtualViewId / mBoardSize;
                int column = virtualViewId % mBoardSize;
                onCellTouched(row+1, column+1);
                return true;
            }
            return false;
        }

        private int[] getRowColumnForVirtualViewId(int virtualViewId) {
            int row = virtualViewId / mBoardSize;
            int column = virtualViewId % mBoardSize;
            return new int[]{row, column};
        }
    }
}
