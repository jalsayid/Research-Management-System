package com.example.l;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileUtil_user {
    public static int countLines(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            int lines = 0;
            while (reader.readLine() != null) {
                lines++;
            }
            return lines;
        } catch (IOException e) {
            e.printStackTrace();
            return -1; // Return -1 to indicate an error occurred
        }
    }
}