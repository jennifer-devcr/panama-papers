package com.intertec.paperAnalyzer;


import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.testng.Assert.assertNotNull;
import static org.testng.AssertJUnit.assertEquals;

public class OfficerFromCountryPredicateTest {
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
        Officer officerSarah = new Officer("Sarah", 2, new HashSet<>(), countryUS);

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
        edgesByEntityUS.put(corpC.getNodeId(), new Edge(officerSarah.getNodeId(), corpC.getNodeId()));

        Map<Integer, Map<Integer, Edge>> edges = new HashMap<>();
        edges.put(officerJohn.getNodeId(), edgesByEntityCR);
        edges.put(officerSarah.getNodeId(), edgesByEntityUS);

        return new Object[][] {
                // Officer, entities, edges, countryCodeStr, entitiesAmount, isFromCountryCode.
                {officerJohn, entities, edges, crCode, 2, true},
                {officerSarah, entities, edges, usCode, 1, true},
                {officerSarah, entities, edges, crCode, 1, false}
        };
    }

    @Test(dataProvider = "testGetEntitiesOfPersonData")
    public void testGetEntitiesOfPerson(Officer officer, Map<Integer, Entity> entities, Map<Integer, Map<Integer, Edge>> edges, String countryCode, int entityAmount, boolean isFromCountryExpected) throws Exception {
        OfficerFromCountryPredicate officerPredicate = new OfficerFromCountryPredicate(entities, edges, countryCode);
        Set<Entity> entitySet= officerPredicate.getEntitiesOfPerson(officer);

        assertNotNull(entitySet);
        assertEquals(entitySet.size(), entityAmount);
    }


    @Test(dataProvider = "testGetEntitiesOfPersonData")
    public void testIsOfficerFromCountry(Officer officer, Map<Integer, Entity> entities, Map<Integer, Map<Integer, Edge>> edges, String countryCode, int entityAmount, boolean isFromCountryExpected) throws Exception {
        OfficerFromCountryPredicate officerPredicate = new OfficerFromCountryPredicate(entities, edges, countryCode);
        boolean isFromCountry = officerPredicate.isOfficerFromCountry(officer);

        assertEquals(isFromCountry, isFromCountryExpected);
    }
}