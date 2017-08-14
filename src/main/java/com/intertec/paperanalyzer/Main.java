package com.intertec.paperanalyzer;

import com.intertec.paperanalyzer.builders.ScenarioBuilder;
import com.intertec.paperanalyzer.business.PanamaPaper;
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
        String countryCode = "CRI"; // FIXME: it should come from input.
        return panamaPaper.analyzePapers(countryCode);
    }

}
