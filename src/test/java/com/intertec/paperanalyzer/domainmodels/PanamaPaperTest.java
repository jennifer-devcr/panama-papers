package com.intertec.paperanalyzer.domainmodels;


import com.intertec.paperanalyzer.commons.EntryPair;
import com.intertec.paperanalyzer.domainmodels.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;


public class PanamaPaperTest {
    @Test
    public void testAnalyze() throws IOException {
        PanamaPaper panamaPaper = new PanamaPaper();

        String countryCode = "CRI";
        List<Officer> officerList = mock(List.class);
        List<Intermediary> intermediaryList = mock(List.class);
        Map<Integer, Entity> entityMap = mock(Map.class);
        Map<Integer, Map<Integer, Edge>> edgeMap = mock(Map.class);

        PaperResult paperResult = panamaPaper.analyzePapers(countryCode, officerList, intermediaryList, entityMap, edgeMap);
    }


    @Test
    public void testMapIntermediary() {
        PanamaPaper panamaPaper = new PanamaPaper();

        List<Intermediary> intermediaryList = new ArrayList<>();
        Intermediary mockIntermediary = mock(Intermediary.class);
        when(mockIntermediary.getNodeId()).thenReturn(123);
        intermediaryList.add(mockIntermediary);


        Map<Integer, Entity> entityMap = new HashMap<>();
        Entity mockEntity = mock(Entity.class);
        when(mockEntity.getNodeId()).thenReturn(456);
        entityMap.put(456, mockEntity);


        Map<Integer, Map<Integer, Edge>> edgeMap = new HashMap<>();
        edgeMap.put(123, new HashMap<>());
        edgeMap.get(123).put(456, new Edge(123, 456));


        List<EntryPair<Intermediary, Set<Integer>>> result = panamaPaper.mapIntermediariesWithEntities(intermediaryList, entityMap, edgeMap);

        assertEquals(result.size(), 1);
    }


    @DataProvider(name = "testGetEntitiesOfPersonData")
    public Object[][] testGetEntitiesOfPersonData() {
        // Countries.
        String crCode = "CRI";
        String usCode = "US";

        // Officers.
        Officer officerJohn = mock(Officer.class);
        when(officerJohn.getNodeId()).thenReturn(1);

        // Intermediaries.
        Intermediary intermediarySarah = mock(Intermediary.class);
        when(intermediarySarah.getNodeId()).thenReturn(2);

        Intermediary intermediaryMark = mock(Intermediary.class);
        when(intermediarySarah.getNodeId()).thenReturn(3);

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
        Set<Entity> entitiesOfPerson = new PanamaPaper().getEntitiesOfPerson(person, edges, entities);

        assertNotNull(entitiesOfPerson);
        assertEquals(entitiesOfPerson.size(), entityAmount);
    }
}