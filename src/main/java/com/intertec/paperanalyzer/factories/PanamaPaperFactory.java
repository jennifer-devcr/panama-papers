package com.intertec.paperanalyzer.factories;


import com.intertec.paperanalyzer.builders.ScenarioBuilder;
import com.intertec.paperanalyzer.business.PanamaPaper;

import java.io.IOException;
import java.io.InputStream;

public class PanamaPaperFactory {

    public PanamaPaper generatePanamaPaper (ScenarioBuilder scenarioBuilder) throws IOException {
        InputStream officerIs = PanamaPaperFactory.class.getResourceAsStream("Officers_mini.csv"); // FIXME: it should come from input.
        InputStream intermediaryIs = PanamaPaperFactory.class.getResourceAsStream("Intermediaries_mini.csv"); // FIXME: it should come from input.
        InputStream entityIs = PanamaPaperFactory.class.getResourceAsStream("Entities_mini.csv"); // FIXME: it should come from input.
        InputStream edgeIs = PanamaPaperFactory.class.getResourceAsStream("all_edges_mini.csv"); // FIXME: it should come from input.

        return new PanamaPaper(scenarioBuilder.buildOfficers(officerIs),
                scenarioBuilder.buildIntermediaries(intermediaryIs),
                scenarioBuilder.buildEntities(entityIs),
                scenarioBuilder.buildEdged(edgeIs));
    }
}
