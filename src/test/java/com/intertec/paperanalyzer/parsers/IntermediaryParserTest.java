package com.intertec.paperanalyzer.parsers;

import com.intertec.paperanalyzer.domainmodels.Intermediary;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.util.List;

import static org.testng.Assert.*;

public class IntermediaryParserTest {
    @DataProvider(name = "testParseIntermediaryLinesData")
    public Object[][] testParseIntermediaryLinesData(){
        return new Object[][]{
                {"Intermediaries_mini.csv", 31}
        };
    }

    @Test(dataProvider = "testParseIntermediaryLinesData")
    public void testParseIntermediaryLines(String docPath, int expectedLinesAmount) throws Exception {
        InputStream is = IntermediaryParser.class.getResourceAsStream(docPath);
        List<Intermediary> intermediaries = new IntermediaryParser().parseLines(is);

        assertNotNull(docPath);
        assertEquals(intermediaries.size(), expectedLinesAmount);
    }
}