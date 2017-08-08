package com.intertec.paperanalyzer.parsers;


import java.io.IOException;
import java.io.InputStream;

public interface LinesFileParser<T> {
    T parseLines(InputStream is) throws IOException;
}
