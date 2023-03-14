package com.echo.wordsudoku.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

public class FileUtils {

    public static String inputStreamToString(InputStream inputStream) throws IOException {
        // Read the file size and create a buffer
        int size = inputStream.available();
        byte[] buffer = new byte[size];
        // Read the whole file into the buffer
        inputStream.read(buffer);
        // Close the input stream
        inputStream.close();
        // Convert the buffer into a string
        return new String(buffer, "UTF-8");
    }

    public static void stringToPrintWriter(PrintWriter printWriter,String content) {
        printWriter.print(content);
        printWriter.close();
    }
}
