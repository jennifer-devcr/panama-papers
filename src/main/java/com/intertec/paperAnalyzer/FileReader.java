package com.intertec.paperAnalyzer;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class FileReader {
    public static List<String> readFile(String filePath) throws IOException {
        InputStream is = new FileInputStream(new File(filePath)); // Getting the file.
        BufferedReader br = new BufferedReader(new InputStreamReader(is)); // Buffering data from reader.
        return br.lines().skip(1).collect(Collectors.toList());
    }
}
