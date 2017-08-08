package com.intertec.paperanalyzer.parsers;

import com.intertec.paperanalyzer.commons.FileReader;
import com.intertec.paperanalyzer.domainmodels.Officer;
import com.intertec.paperanalyzer.factories.PersonFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public class OfficerParser implements LinesFileParser<List<Officer>> {
    @Override
    public List<Officer> parseLines(InputStream is) throws IOException {
        return FileReader.getLinesFromResource(is)
                .parallelStream()
                .map(PersonFactory::parseOfficer)
                .filter(Objects::nonNull)
                .collect(toList());
    }
}
