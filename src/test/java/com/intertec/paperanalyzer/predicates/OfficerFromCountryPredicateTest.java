package com.intertec.paperanalyzer.predicates;


import com.intertec.paperanalyzer.domainmodels.Edge;
import com.intertec.paperanalyzer.domainmodels.Entity;
import com.intertec.paperanalyzer.domainmodels.Officer;
import com.intertec.paperanalyzer.predicates.OfficerFromCountryPredicate;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertNotNull;
import static org.testng.AssertJUnit.assertEquals;

public class OfficerFromCountryPredicateTest {
    @DataProvider(name = "testGetEntitiesOfPersonData")
    public Object[][] testGetEntitiesOfPersonData() {
        // Countries.
        String crCode = "CRI";
        Set<String> countryCodeCRI = new HashSet<>();
        countryCodeCRI.add(crCode);

        String usCode = "US";
        Set<String> countryCodeUS = new HashSet<>();
        countryCodeUS.add(usCode);

        // Officers.
        Officer officerJohn = mock(Officer.class);
        when(officerJohn.getCountryCode()).thenReturn(countryCodeCRI);
        when(officerJohn.getNodeId()).thenReturn(1);

        Officer officerSarah = mock(Officer.class);
        when(officerSarah.getCountryCode()).thenReturn(countryCodeUS);
        when(officerSarah.getNodeId()).thenReturn(2);

        // Entities.
        Entity corpA = mock(Entity.class);
        when(corpA.getNodeId()).thenReturn(3);

        Entity corpB = mock(Entity.class);
        when(corpB.getNodeId()).thenReturn(4);

        Entity corpC = mock(Entity.class);
        when(corpC.getNodeId()).thenReturn(5);

        Map<Integer, Entity> entities = new HashMap<Integer, Entity>();
        entities.put(corpA.getNodeId(), corpA);
        entities.put(corpB.getNodeId(), corpB);
        entities.put(corpC.getNodeId(), corpC);

        // Edges.
        Map<Integer, Edge> edgesByEntityCR = new HashMap<>();
        edgesByEntityCR.put(corpA.getNodeId(), mock(Edge.class));
        edgesByEntityCR.put(corpB.getNodeId(), mock(Edge.class));

        Map<Integer, Edge> edgesByEntityUS = new HashMap<>();
        edgesByEntityUS.put(corpC.getNodeId(), mock(Edge.class));

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