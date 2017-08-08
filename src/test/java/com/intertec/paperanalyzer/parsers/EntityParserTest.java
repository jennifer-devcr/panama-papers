package com.intertec.paperanalyzer.parsers;

import com.intertec.paperanalyzer.domainmodels.Entity;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.util.Map;

import static org.testng.Assert.*;

public class EntityParserTest {
    @DataProvider(name = "testParseEntityLinesData")
    public Object[][] testParseEntityLinesData(){
        return new Object[][]{
                {"Entities_mini.csv", 13}
        };
    }

    @Test(dataProvider = "testParseEntityLinesData")
    public void testParseEntityLines(String docPath, int expectedLinesAmount) throws Exception {
        InputStream is = EntityParser.class.getResourceAsStream(docPath);
        Map<Integer, Entity> entities = new EntityParser().parseLines(is);

        assertNotNull(docPath);
        assertEquals(entities.size(), expectedLinesAmount);
    }
}