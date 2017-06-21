package com.exercise.common;

import java.io.*;
import java.util.stream.Stream;

public class FileReader {
    public static Stream<String> readFile(String filePath) throws IOException {
        InputStream is = new FileInputStream(new File(filePath)); // Getting the file.
        BufferedReader br = new BufferedReader(new InputStreamReader(is)); // Buffering data from reader.

        Stream<String> lines = br.lines()
                .skip(1); // Skipping CSV header.

        return lines;
    }
}
