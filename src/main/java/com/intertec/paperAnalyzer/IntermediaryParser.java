package com.intertec.paperAnalyzer;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public class IntermediaryParser implements LinesFileParser<List<Intermediary>> {

    @Override
    public List<Intermediary> parseLines(InputStream is) throws IOException {
        return FileReader.getLinesFromResource(is)
                .parallelStream()
                .map(PersonFactory::parseIntermediary)
                .filter(Objects::nonNull)
                .collect(toList());
    }
}
