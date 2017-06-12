package com.exercise.common;

import java.io.*;
import java.util.List;
import java.util.function.Function;
import static java.util.stream.Collectors.toList;

public class FileReader {
    public static List<String> readFile(String filePath) throws IOException {
        InputStream is = new FileInputStream(new File(filePath)); // Getting the file.
        BufferedReader br = new BufferedReader(new InputStreamReader(is)); // Buffering data from reader.

        List<String> intermediaries = br.lines()
                .skip(1) // Skipping CSV header.
                .collect(toList());

        return intermediaries;
    }
}
