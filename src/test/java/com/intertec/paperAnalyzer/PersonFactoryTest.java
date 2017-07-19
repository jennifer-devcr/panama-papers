package com.intertec.paperAnalyzer;


import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

public class PersonFactoryTest {
    @DataProvider(name = "testParseOfficerData")
    public Object[][] testParseOfficerData() {
        return new Object[][] {
                {"John,,,CRC,Costa Rica,2345", 2345}
        };
    }

    @Test(dataProvider = "testParseOfficerData")
    public void testParseOfficer(String dataLine, int nodeId) throws Exception {
        Officer officer = PersonFactory.parseOfficer(dataLine);

        assertNotNull(officer);
        assertEquals(officer.getNodeId(), nodeId);
    }


    @DataProvider(name = "testParseIntermediaryData")
    public Object[][] testParseIntermediaryData() {
        return new Object[][] {
                {"John,1,345 Street,,CRC,Costa Rica,Active,2345", 2345}
        };
    }

    @Test(dataProvider = "testParseIntermediaryData")
    public void testParseIntermediary(String dataLine, int nodeId) throws Exception {
        Intermediary intermediary = PersonFactory.parseIntermediary(dataLine);

        assertNotNull(intermediary);
        assertEquals(intermediary.getNodeId(), nodeId);
    }


    @DataProvider(name = "testEmptyLineData")
    public Object[][] testEmptyLineData() {
        return new Object[][]{
                {""}
        };
    }

    @Test(dataProvider = "testEmptyLineData")
    public void testParseOfficerEmptyLine(String dataLine) throws Exception {
        Officer officer = PersonFactory.parseOfficer(dataLine);

        assertNull(officer);
    }

    @Test(dataProvider = "testEmptyLineData")
    public void testParseIntermediaryEmptyLine(String dataLine) throws Exception {
        Intermediary intermediary = PersonFactory.parseIntermediary(dataLine);

        assertNull(intermediary);
    }
}