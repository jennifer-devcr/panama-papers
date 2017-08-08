package com.intertec.paperanalyzer.builders;

import com.intertec.paperanalyzer.domainmodels.Edge;
import com.intertec.paperanalyzer.domainmodels.Entity;
import com.intertec.paperanalyzer.domainmodels.Intermediary;
import com.intertec.paperanalyzer.domainmodels.Officer;
import com.intertec.paperanalyzer.parsers.LinesFileParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class ScenarioBuilder {
    private LinesFileParser<List<Officer>> officerLinesParser;
    private LinesFileParser<List<Intermediary>> intermediaryLinesParser;
    private LinesFileParser<Map<Integer, Entity>> entityLinesParser;
    private LinesFileParser<Map<Integer, Map<Integer, Edge>>> edgeLinesParser;

    public ScenarioBuilder(LinesFileParser<List<Officer>> officerLinesParser, LinesFileParser<List<Intermediary>> intermediaryLinesParser, LinesFileParser<Map<Integer, Entity>> entityLinesParser, LinesFileParser<Map<Integer, Map<Integer, Edge>>> edgeLinesParser) {
        this.officerLinesParser = officerLinesParser;
        this.intermediaryLinesParser = intermediaryLinesParser;
        this.entityLinesParser = entityLinesParser;
        this.edgeLinesParser = edgeLinesParser;
    }

    public LinesFileParser<List<Officer>> getOfficerLinesParser() {
        return officerLinesParser;
    }

    public void setOfficerLinesParser(LinesFileParser<List<Officer>> officerLinesParser) {
        this.officerLinesParser = officerLinesParser;
    }

    public LinesFileParser<List<Intermediary>> getIntermediaryLinesParser() {
        return intermediaryLinesParser;
    }

    public void setIntermediaryLinesParser(LinesFileParser<List<Intermediary>> intermediaryLinesParser) {
        this.intermediaryLinesParser = intermediaryLinesParser;
    }

    public LinesFileParser<Map<Integer, Entity>> getEntityLinesParser() {
        return entityLinesParser;
    }

    public void setEntityLinesParser(LinesFileParser<Map<Integer, Entity>> entityLinesParser) {
        this.entityLinesParser = entityLinesParser;
    }

    public LinesFileParser<Map<Integer, Map<Integer, Edge>>> getEdgeLinesParser() {
        return edgeLinesParser;
    }

    public void setEdgeLinesParser(LinesFileParser<Map<Integer, Map<Integer, Edge>>> edgeLinesParser) {
        this.edgeLinesParser = edgeLinesParser;
    }


    public List<Officer> buildOfficers(InputStream is) throws IOException {
        return officerLinesParser.parseLines(is);
    }

    public List<Intermediary> buildIntermediaries(InputStream is) throws IOException {
        return intermediaryLinesParser.parseLines(is);
    }

    public Map<Integer, Entity> buildEntities(InputStream is) throws IOException {
        return entityLinesParser.parseLines(is);
    }

    public Map<Integer, Map<Integer, Edge>> buildEdged(InputStream is) throws IOException {
        return edgeLinesParser.parseLines(is);
    }
}
