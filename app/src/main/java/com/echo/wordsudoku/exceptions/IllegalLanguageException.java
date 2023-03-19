package com.echo.wordsudoku.exceptions;

public class IllegalLanguageException extends Exception{
    public IllegalLanguageException(String message) {
        super(message);
    }

    public IllegalLanguageException(){
        super("Illegal language");
    }
}
