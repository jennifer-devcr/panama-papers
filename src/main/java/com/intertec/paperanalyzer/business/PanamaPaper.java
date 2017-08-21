package com.intertec.paperanalyzer.business;

import com.intertec.paperanalyzer.commons.EntryPair;
import com.intertec.paperanalyzer.commons.ObservableMap;
import com.intertec.paperanalyzer.domainmodels.*;
import com.intertec.paperanalyzer.predicates.OfficerFromCountryPredicate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class PanamaPaper {
    private List<Officer> officerList;
    private List<Intermediary> intermediaryList;
    private Map<Integer, Entity> entityMap;
    private Map<Integer, Map<Integer, Edge>> edgeMap;


    public PanamaPaper(List<Officer> officerList, List<Intermediary> intermediaryList, Map<Integer, Entity> entityMap, Map<Integer, Map<Integer, Edge>> edgeMap) {
        this.officerList = officerList;
        this.intermediaryList = intermediaryList;
        this.entityMap = entityMap;
        this.edgeMap = edgeMap;
    }

    public PaperResult analyzePapers(String countryCode) throws IOException {
        Map<String, Object> results = new HashMap<>();
        PaperResult paperResult = new PaperResult();
        PaperStatistic paperStatistic = new PaperStatistic();
        ObservableMap<Officer, Set<Entity>> observableMap = new ObservableMap<Officer, Set<Entity>>(new HashMap<>());


        // Parsing data lines.
        List<EntryPair<Intermediary, Set<Integer>>> intermediariesWithEntities = mapIntermediariesWithEntities();
        observableMap.registerListenersAddAction((officer, entities) -> paperStatistic.onEntitySetAdded(officer, entities, intermediariesWithEntities));


        paperResult.setOfficers(officerList
            .stream()
            .filter(new OfficerFromCountryPredicate(entityMap, edgeMap, countryCode))
            .collect(Collectors.toMap(
                    (officer) -> officer, // Key
                    (officer) -> getEntitiesOfPerson(officer), // Value
                    (entry1, entry2) -> entry1, // Merge if key collisions.
                    () -> observableMap // New Object. This lambda is a provider, returns something with not parameters.
            )));


        paperResult.setStatistic(paperStatistic);

        return paperResult;
    }

    public Set<Entity> getEntitiesOfPerson(Person person) {
        Map<Integer, Edge> entityIdsOfPerson = edgeMap.get(person.getNodeId());
        Set<Entity> entities = new HashSet<>();

        if (entityIdsOfPerson != null) {
            entities = entityIdsOfPerson.keySet()
                .stream()
                .map(id -> entityMap.get(id))
                .collect(Collectors.toSet());
        }

        return entities;
    }

    /**
     * @return Pairs of Intermediary and its entities' node id.
     */
    List<EntryPair<Intermediary, Set<Integer>>> mapIntermediariesWithEntities() {
        return intermediaryList
                .stream()
                .map(intermediary -> {
                    Set<Integer> entitiesIDs = getEntitiesOfPerson(intermediary)
                            .stream()
                            .collect(HashSet<Integer>::new,
                                    (set, entity) -> set.add(entity.getNodeId()),
                                    (entity1, entity2) -> entity1.addAll(entity2)
                            );
                    return new EntryPair<Intermediary, Set<Integer>>(intermediary, entitiesIDs);
                })
                .collect(toList());
    }


    public void setOfficerList(List<Officer> officerList) {
        this.officerList = officerList;
    }

    public void setIntermediaryList(List<Intermediary> intermediaryList) {
        this.intermediaryList = intermediaryList;
    }

    public void setEntityMap(Map<Integer, Entity> entityMap) {
        this.entityMap = entityMap;
    }

    public void setEdgeMap(Map<Integer, Map<Integer, Edge>> edgeMap) {
        this.edgeMap = edgeMap;
    }
}
