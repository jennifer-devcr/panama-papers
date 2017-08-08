package com.intertec.paperanalyzer;

import com.intertec.paperanalyzer.commons.EntryPair;
import com.intertec.paperanalyzer.domainmodels.*;
import com.intertec.paperanalyzer.parsers.EdgeParser;
import com.intertec.paperanalyzer.parsers.EntityParser;
import com.intertec.paperanalyzer.parsers.IntermediaryParser;
import com.intertec.paperanalyzer.parsers.OfficerParser;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        try {
            ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
            PanamaPaper panamaPaper = (PanamaPaper) applicationContext.getBean("panamaPaperBean");

            PaperResult paperResult = new Main().process(panamaPaper);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PaperResult process(PanamaPaper panamaPaper) throws IOException {
        Map<String, String> filePaths = new HashMap<>();
        filePaths.put("officer", "Officers_mini.csv"); // FIXME: it should come from input.
        filePaths.put("intermediary", "Intermediaries_mini.csv"); // FIXME: it should come from input.
        filePaths.put("entity", "Entities_mini.csv"); // FIXME: it should come from input.
        filePaths.put("edge", "all_edges_mini.csv"); // FIXME: it should come from input.

        InputStream officerIs = Main.class.getResourceAsStream(filePaths.get("officer"));
        InputStream intermediaryIs = Main.class.getResourceAsStream(filePaths.get("intermediary"));
        InputStream entityIs = Main.class.getResourceAsStream(filePaths.get("entity"));
        InputStream edgeIs = Main.class.getResourceAsStream(filePaths.get("edge"));

        String countryCode = "CRI"; // FIXME: it should come from input.
        List<Officer> officerList = new OfficerParser().parseLines(officerIs);
        List<Intermediary> intermediaryList = new IntermediaryParser().parseLines(intermediaryIs);
        Map<Integer, Entity> entityMap = new EntityParser().parseLines(entityIs);
        Map<Integer, Map<Integer, Edge>> edgeMap = new EdgeParser().parseLines(edgeIs);

        return panamaPaper.analyzePapers(countryCode, officerList, intermediaryList, entityMap, edgeMap);
    }

}
