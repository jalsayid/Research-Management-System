package com.example.l;


import java.io.BufferedReader;
import java.io.*;
import java.io.IOException;

public class FileUtilAdmin {
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

    public static void writeToFile(String data, String filename) throws IOException {
        try (FileWriter fw = new FileWriter(filename, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(data);
        }
    }
}
