package com.intertec.paperanalyzer.commons;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class FileReader {
    public static List<String> getLinesFromResource(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is)); // Buffering data from reader.
        return br.lines().skip(1).collect(Collectors.toList());
    }
}
