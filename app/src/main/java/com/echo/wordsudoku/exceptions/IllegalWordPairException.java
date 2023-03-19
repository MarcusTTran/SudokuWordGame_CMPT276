package com.echo.wordsudoku.exceptions;

public class IllegalWordPairException extends Exception{
    public IllegalWordPairException(String message) {
        super(message);
    }

    public IllegalWordPairException(){
        super("Illegal word pair");
    }
}
