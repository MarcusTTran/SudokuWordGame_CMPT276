package com.echo.wordsudoku;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class SudokuBoard extends View {

    // The size of the board
    private final int mBoardSize;
    private final int mBoardColor;
    private final int mCellFillColor;
    private final int mCellsHighlightColor;


    private final Paint mBoardPaint = new Paint();
    private final Paint mCellFillColorPaint = new Paint();
    private final Paint mCellsHighlightColorPaint = new Paint();


    // The width of each cell
    private int cellWidth;

    // The current cell that is selected
    private int currentCellRow = -1;
    private int currentCellColumn = -1;

    public SudokuBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.SudokuBoard,
                0, 0);

        try {
            mBoardSize = a.getInteger(R.styleable.SudokuBoard_boardSize, 9);
            mBoardColor = a.getInteger(R.styleable.SudokuBoard_boardColor, 0);
            mCellFillColor = a.getInteger(R.styleable.SudokuBoard_cellFillColor, 0);
            mCellsHighlightColor = a.getInteger(R.styleable.SudokuBoard_cellsHighlightColor, 0);
        } finally {
            a.recycle();
        }
    }


    @Override
    protected void onMeasure(int width, int height) {
        super.onMeasure(width, height);

        // Make the view a square
        int size = Math.min(this.getMeasuredWidth(), this.getMeasuredHeight());
        cellWidth = size / 9;

        // Set the measured dimensions
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mBoardPaint.setStyle(Paint.Style.STROKE);
        mBoardPaint.setStrokeWidth(16);
        mBoardPaint.setColor(mBoardColor);
        mBoardPaint.setAntiAlias(true);

        mCellFillColorPaint.setStyle(Paint.Style.FILL);
        mBoardPaint.setAntiAlias(true);
        mCellFillColorPaint.setColor(mCellFillColor);

        mCellsHighlightColorPaint.setStyle(Paint.Style.FILL);
        mBoardPaint.setAntiAlias(true);
        mCellsHighlightColorPaint.setColor(mCellsHighlightColor);

        colorCell(canvas, currentCellRow, currentCellColumn);
        canvas.drawRect(0, 0, getWidth(), getHeight(), mBoardPaint);
        drawBoard(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean isValid;
        float x = event.getX();
        float y = event.getY();

        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN) {
            currentCellColumn = (int) Math.ceil(x / cellWidth);
            currentCellRow = (int) Math.ceil(y / cellWidth);
            isValid = true;
        } else {
            isValid = false;
        }

        return isValid;
    }

    private void colorCell(Canvas canvas, int r, int c) {
        if (currentCellColumn != -1 && currentCellRow != -1) {
            // Highlight the selected column
            canvas.drawRect((c-1)*cellWidth,0,c*cellWidth,cellWidth*9,mCellsHighlightColorPaint);
            // Highlight the selected row
            canvas.drawRect(0,(r-1)*cellWidth,9*cellWidth,r*cellWidth,mCellsHighlightColorPaint);
            // Highlight the selected cell
            canvas.drawRect((c-1)*cellWidth,(r-1)*cellWidth,c*cellWidth,r*cellWidth,mCellFillColorPaint);
        }
        invalidate();
    }

    private void drawThickLines() {
        mBoardPaint.setStyle(Paint.Style.STROKE);
        mBoardPaint.setStrokeWidth(10);
        mBoardPaint.setColor(mBoardColor);
    }

    private void drawThinLines() {
        mBoardPaint.setStyle(Paint.Style.STROKE);
        mBoardPaint.setStrokeWidth(4);
        mBoardPaint.setColor(mBoardColor);
    }

    //TODO: Draw the board with custom sizes. Currently only draws a 9x9 board

    private void drawBoard(Canvas canvas) {
        // Draw the vertical lines
        for (int c = 0; c < 10; c++) {
            if (c % 3 == 0) {
                drawThickLines();
            } else {
                drawThinLines();
            }
            canvas.drawLine(c * cellWidth, 0, c * cellWidth, getHeight(), mBoardPaint);
        }

        for (int r = 0; r < 10; r++) {
            if (r % 3 == 0) {
                drawThickLines();
            } else {
                drawThinLines();
            }
            canvas.drawLine(0, r*cellWidth, getWidth(), r*cellWidth, mBoardPaint);
        }
    }


}
