package com.exercise.common;

import java.io.*;

public class FileReader {
    public static BufferedReader readFile(String filePath) throws IOException {
        InputStream is = new FileInputStream(new File(filePath)); // Getting the file.
        BufferedReader br = new BufferedReader(new InputStreamReader(is)); // Buffering data from reader.
        return br;
    }
}
