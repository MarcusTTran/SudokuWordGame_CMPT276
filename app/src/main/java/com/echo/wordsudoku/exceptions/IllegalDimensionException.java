package com.echo.wordsudoku.exceptions;

public class IllegalDimensionException extends Exception{
    public IllegalDimensionException(String message) {
        super(message);
    }

    public IllegalDimensionException(){
        super("Illegal dimension");
    }
}
