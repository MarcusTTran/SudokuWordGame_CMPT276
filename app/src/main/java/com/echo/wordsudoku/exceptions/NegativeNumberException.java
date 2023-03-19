package com.echo.wordsudoku.exceptions;

public class NegativeNumberException extends Exception{
    public NegativeNumberException(String message) {
        super(message);
    }

    public NegativeNumberException(){
        super("Negative number where positive number expected");
    }
}
