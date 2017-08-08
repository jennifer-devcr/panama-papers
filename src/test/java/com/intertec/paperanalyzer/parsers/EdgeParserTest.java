package com.intertec.paperanalyzer.parsers;

import com.intertec.paperanalyzer.domainmodels.Edge;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.util.Map;

import static org.testng.Assert.*;

public class EdgeParserTest {
    @DataProvider(name = "testParseLinesData")
    public Object[][] testParseLinesData(){
        return new Object[][]{
            {"all_edges_mini.csv", 62}
        };
    }

    @Test(dataProvider = "testParseLinesData")
    public void testParseLines(String docPath, int expectedLinesAmount) throws Exception {
        InputStream is = EdgeParser.class.getResourceAsStream(docPath);
        Map<Integer, Map<Integer, Edge>> edges = new EdgeParser().parseLines(is);

        assertNotNull(docPath);
        assertEquals(edges.size(), expectedLinesAmount);
    }
}