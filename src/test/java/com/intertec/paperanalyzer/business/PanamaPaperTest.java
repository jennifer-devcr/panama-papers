package com.intertec.paperanalyzer.business;


import com.intertec.paperanalyzer.builders.ScenarioBuilder;
import com.intertec.paperanalyzer.business.PanamaPaper;
import com.intertec.paperanalyzer.commons.EntryPair;
import com.intertec.paperanalyzer.domainmodels.*;
import com.intertec.paperanalyzer.factories.PanamaPaperFactory;
import com.intertec.paperanalyzer.parsers.EdgeParser;
import com.intertec.paperanalyzer.parsers.EntityParser;
import com.intertec.paperanalyzer.parsers.IntermediaryParser;
import com.intertec.paperanalyzer.parsers.OfficerParser;
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
        String countryCode = "CRI";
        ScenarioBuilder scenarioBuilder = new ScenarioBuilder(new OfficerParser(), new IntermediaryParser(), new EntityParser(), new EdgeParser());

        PanamaPaper panamaPaper = new PanamaPaperFactory().generatePanamaPaper(scenarioBuilder);
        PaperResult paperResult = panamaPaper.analyzePapers(countryCode);

        EntryPair<String, Integer> countryWithMoreEntitiesExpected = mock(EntryPair.class);
        when(countryWithMoreEntitiesExpected.getEntry()).thenReturn("CRI");
        when(countryWithMoreEntitiesExpected.getValue()).thenReturn(8);

        EntryPair<Officer, Integer> officerWithMoreEntitiesExpected = mock(EntryPair.class);
        Officer officerExpected = mock(Officer.class);
        when(officerExpected.getNodeId()).thenReturn(12099150);
        when(officerWithMoreEntitiesExpected.getEntry()).thenReturn(officerExpected);
        when(officerWithMoreEntitiesExpected.getValue()).thenReturn(2);

        EntryPair<Intermediary, Integer> intermediaryAssistedMoreOfficersExpected = mock(EntryPair.class);
        Intermediary intermediaryExpected = mock(Intermediary.class);
        when(intermediaryExpected.getNodeId()).thenReturn(11000003);
        when(intermediaryAssistedMoreOfficersExpected.getEntry()).thenReturn(intermediaryExpected);
        when(intermediaryAssistedMoreOfficersExpected.getValue()).thenReturn(2);



        assertNotNull(paperResult);
        assertNotNull(paperResult.getOfficers());
        assertEquals(paperResult.getOfficers().size(), 8);

        PaperStatistic paperStatistic = paperResult.getStatistic();
        assertNotNull(paperStatistic);

        assertNotNull(paperStatistic.getCountryWithMoreEntities());
        assertNotNull(paperStatistic.getCountryWithMoreEntities().getEntry());
        assertEquals(paperStatistic.getCountryWithMoreEntities().getEntry(), countryWithMoreEntitiesExpected.getEntry());
        assertEquals(paperStatistic.getCountryWithMoreEntities().getValue(), countryWithMoreEntitiesExpected.getValue());

        assertNotNull(paperStatistic.getOfficerWithMoreEntities());
        assertNotNull(paperStatistic.getOfficerWithMoreEntities().getEntry());
        assertEquals(paperStatistic.getOfficerWithMoreEntities().getEntry().getNodeId(), officerWithMoreEntitiesExpected.getEntry().getNodeId());
        assertEquals(paperStatistic.getOfficerWithMoreEntities().getValue(), officerWithMoreEntitiesExpected.getValue());

        assertNotNull(paperStatistic.getIntermediaryAssistedMoreOfficers());
        assertNotNull(paperStatistic.getIntermediaryAssistedMoreOfficers().getEntry());
        assertEquals(paperStatistic.getIntermediaryAssistedMoreOfficers().getEntry().getNodeId(), intermediaryAssistedMoreOfficersExpected.getEntry().getNodeId());
        assertEquals(paperStatistic.getIntermediaryAssistedMoreOfficers().getValue(), intermediaryAssistedMoreOfficersExpected.getValue());
    }


    @Test
    public void testMapIntermediary() {
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

        PanamaPaper panamaPaper = new PanamaPaper(mock(List.class), intermediaryList, entityMap, edgeMap);

        List<EntryPair<Intermediary, Set<Integer>>> result = panamaPaper.mapIntermediariesWithEntities();

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
        PanamaPaper panamaPaper = new PanamaPaper(mock(List.class), mock(List.class), entities, edges);
        Set<Entity> entitiesOfPerson = panamaPaper.getEntitiesOfPerson(person);

        assertNotNull(entitiesOfPerson);
        assertEquals(entitiesOfPerson.size(), entityAmount);
    }
}