package com.intertec.paperAnalyzer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EdgeParser implements LinesFileParser<Map<Integer, Map<Integer, Edge>>>{

    @Override
    public Map<Integer, Map<Integer, Edge>> parseLines(InputStream is) throws IOException {
        return FileReader.getLinesFromResource(is)
                .parallelStream() // todo add caliper to test with or without threads
                .map(EdgeFactory::parseEdge)
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(e -> e.getPersonNodeId(),
                        Collectors.toMap(e -> e.getEntityNodeId(),
                                Function.identity())));
    }
}
