package com.intertec.paperAnalyzer;


import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;


public class PanamaPaperTest {
    @DataProvider(name = "testParseOfficerLinesData")
    public Object[][] testParseOfficerLinesData(){
        return new Object[][]{
                {"Officers_mini.csv", 32}
        };
    }

    @Test(dataProvider = "testParseOfficerLinesData")
    public void testParseOfficerLines(String docPath, int expectedLinesAmount) throws Exception {
        InputStream is = PanamaPaper.class.getResourceAsStream(docPath);
        List<Officer> officers = PanamaPaper.parseOfficerLines(is);

        assertNotNull(docPath);
        assertEquals(officers.size(), expectedLinesAmount);
    }


    @DataProvider(name = "testParseIntermediaryLinesData")
    public Object[][] testParseIntermediaryLinesData(){
        return new Object[][]{
                {"Intermediaries_mini.csv", 31}
        };
    }

    @Test(dataProvider = "testParseIntermediaryLinesData")
    public void testParseIntermediaryLines(String docPath, int expectedLinesAmount) throws Exception {
        InputStream is = PanamaPaper.class.getResourceAsStream(docPath);
        List<Intermediary> intermediaries = PanamaPaper.parseIntermediaryLines(is);

        assertNotNull(docPath);
        assertEquals(intermediaries.size(), expectedLinesAmount);
    }


    @DataProvider(name = "testParseEntityLinesData")
    public Object[][] testParseEntityLinesData(){
        return new Object[][]{
                {"Entities_mini.csv", 13}
        };
    }

    @Test(dataProvider = "testParseEntityLinesData")
    public void testParseEntityLines(String docPath, int expectedLinesAmount) throws Exception {
        InputStream is = PanamaPaper.class.getResourceAsStream(docPath);
        Map<Integer, Entity> entities = PanamaPaper.parseEntityLines(is);

        assertNotNull(docPath);
        assertEquals(entities.size(), expectedLinesAmount);
    }


    @DataProvider(name = "testParseEdgeLinesData")
    public Object[][] testParseEdgeLinesData(){
        return new Object[][]{
                {"all_edges_mini.csv", 63}
        };
    }

    @Test(dataProvider = "testParseEdgeLinesData")
    public void testParseEdgeLines(String docPath, int expectedLinesAmount) throws Exception {
        InputStream is = PanamaPaper.class.getResourceAsStream(docPath);
        Map<Integer, Map<Integer, Edge>> edges = PanamaPaper.parseEdgeLines(is);

        assertNotNull(docPath);
        assertEquals(edges.size(), expectedLinesAmount);
    }


    @DataProvider(name = "testGetEntitiesOfPersonData")
    public Object[][] testGetEntitiesOfPersonData() {
        // Countries.
        String crCode = "CRI";
        Set<String> countryCRI = new HashSet<>();
        Set<String> countryCodeCRI = new HashSet<>();
        countryCodeCRI.add(crCode);

        String usCode = "US";
        Set<String> countryUS = new HashSet<>();
        Set<String> countryCodeUS = new HashSet<>();
        countryCodeUS.add(usCode);

        // Officers.
        Officer officerJohn = new Officer("John", 1, countryCodeCRI, countryCRI);

        // Intermediaries.
        Intermediary intermediarySarah = new Intermediary("Sarah", new HashSet<>(), countryUS, "Active", "", 2, "55");
        Intermediary intermediaryMark = new Intermediary("Mark", new HashSet<>(), countryUS, "Active", "", 3, "55");

        // Entities.
        Entity corpA = new Entity("Corp A", "Active", countryCodeCRI, countryCRI, 3);
        Entity corpB = new Entity("Corp B", "Active", countryCodeCRI, countryCRI, 4);
        Entity corpC = new Entity("Corp C", "Active", countryCodeUS, countryUS, 5);

        Map<Integer, Entity> entities = new HashMap<Integer, Entity>();
        entities.put(corpA.getNodeId(), corpA);
        entities.put(corpB.getNodeId(), corpB);
        entities.put(corpC.getNodeId(), corpC);

        // Edges.
        Map<Integer, Edge> edgesByEntityCR = new HashMap<>();
        edgesByEntityCR.put(corpA.getNodeId(), new Edge(officerJohn.getNodeId(), corpA.getNodeId()));
        edgesByEntityCR.put(corpB.getNodeId(), new Edge(officerJohn.getNodeId(), corpB.getNodeId()));

        Map<Integer, Edge> edgesByEntityUS = new HashMap<>();
        edgesByEntityUS.put(corpC.getNodeId(), new Edge(intermediarySarah.getNodeId(), corpC.getNodeId()));

        Map<Integer, Map<Integer, Edge>> edges = new HashMap<>();
        edges.put(officerJohn.getNodeId(), edgesByEntityCR);
        edges.put(intermediarySarah.getNodeId(), edgesByEntityUS);

        return new Object[][] {
                // Officer, entities, edges, countryCodeStr, entitiesAmount.
                {officerJohn, entities, edges, 2},
                {intermediarySarah, entities, edges, 1},
                {intermediaryMark, entities, edges, 0}
        };
    }

    @Test(dataProvider = "testGetEntitiesOfPersonData")
    public void testGetEntitiesOfPerson(Person person, Map<Integer, Entity> entities, Map<Integer, Map<Integer, Edge>> edges, int entityAmount){
        Set<Entity> entitiesOfPerson = PanamaPaper.getEntitiesOfPerson(person, edges, entities);

        assertNotNull(entitiesOfPerson);
        assertEquals(entitiesOfPerson.size(), entityAmount);
    }


    @DataProvider(name = "testAnalyzePapersData")
    public Object[][] testAnalyzePapersData(){
        return new Object[][] {
                {"CRI", "Officers_mini.csv", "Intermediaries_mini.csv", "Entities_mini.csv", "all_edges_mini.csv"}
        };
    }

    @Test(dataProvider = "testAnalyzePapersData")
    public void testAnalyzePapers(String countryCode, String officerDocPath, String intermediaryDocPath, String entityDocPath, String edgeDocPath){
        InputStream officerIs = PanamaPaper.class.getResourceAsStream(officerDocPath);
        InputStream intermediaryIs = PanamaPaper.class.getResourceAsStream(intermediaryDocPath);
        InputStream entityIs = PanamaPaper.class.getResourceAsStream(entityDocPath);
        InputStream edgeIs = PanamaPaper.class.getResourceAsStream(edgeDocPath);

        PaperResult paperResult = PanamaPaper.analyzePapers(officerIs, intermediaryIs, entityIs, edgeIs, countryCode);

        assertTrue(true);
    }

    /*
    @Test
    public void testGetPeopleCountryCodesList() throws Exception {
        PanamaPaper papers = new PanamaPaper();
        List<String> countries = papers.getPeopleCountryCodesList();

        assertNotNull(countries);
        assertFalse(0 == countries.size());
    }

    @Test
    public void testGetOfficersByCountry() throws Exception {
        PanamaPaper papers = new PanamaPaper();
        papers.getOfficersByCountry("CRI");
    }
    */
}