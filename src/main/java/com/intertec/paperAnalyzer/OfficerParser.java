package com.intertec.paperAnalyzer;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public class OfficerParser implements LinesFileParser<List<Officer>>{
    @Override
    public List<Officer> parseLines(InputStream is) throws IOException {
        return FileReader.getLinesFromResource(is)
                .parallelStream()
                .map(PersonFactory::parseOfficer)
                .filter(Objects::nonNull)
                .collect(toList());
    }
}
