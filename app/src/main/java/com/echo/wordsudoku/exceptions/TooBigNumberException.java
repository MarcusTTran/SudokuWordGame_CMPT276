package com.echo.wordsudoku.exceptions;

public class TooBigNumberException extends Exception{
    public TooBigNumberException(String message) {
        super(message);
    }

    public TooBigNumberException(){
        super("Too big number");
    }
}
