package com.intertec.paperanalyzer.parsers;

import com.intertec.paperanalyzer.domainmodels.Officer;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.util.List;

import static org.testng.Assert.*;

public class OfficerParserTest {
    @DataProvider(name = "testParseOfficerLinesData")
    public Object[][] testParseOfficerLinesData(){
        return new Object[][]{
                {"Officers_mini.csv", 32}
        };
    }

    @Test(dataProvider = "testParseOfficerLinesData")
    public void testParseOfficerLines(String docPath, int expectedLinesAmount) throws Exception {
        InputStream is = OfficerParser.class.getResourceAsStream(docPath);
        List<Officer> officers = new OfficerParser().parseLines(is);

        assertNotNull(docPath);
        assertEquals(officers.size(), expectedLinesAmount);
    }
}