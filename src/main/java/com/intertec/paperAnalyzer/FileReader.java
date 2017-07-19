package com.intertec.paperAnalyzer;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class FileReader {
    public static List<String> getLinesFromResource(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is)); // Buffering data from reader.
        return br.lines().skip(1).collect(Collectors.toList());
    }
}
