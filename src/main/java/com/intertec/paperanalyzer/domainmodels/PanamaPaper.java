package com.intertec.paperanalyzer.domainmodels;

import com.intertec.paperanalyzer.commons.EntryPair;
import com.intertec.paperanalyzer.commons.ObservableMap;
import com.intertec.paperanalyzer.predicates.OfficerFromCountryPredicate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class PanamaPaper {

    public PaperResult analyzePapers(String countryCode, List<Officer> officerList, List<Intermediary> intermediaryList, Map<Integer, Entity> entityMap, Map<Integer, Map<Integer, Edge>> edgeMap) throws IOException {
        Map<String, Object> results = new HashMap<>();
        PaperResult paperResult = new PaperResult();
        PaperStatistic paperStatistic = new PaperStatistic();
        ObservableMap<Officer, Set<Entity>> observableMap = new ObservableMap<Officer, Set<Entity>>(new HashMap<>());


        // Parsing data lines.
        List<EntryPair<Intermediary, Set<Integer>>> intermediariesWithEntities = mapIntermediariesWithEntities(intermediaryList, entityMap, edgeMap);
        observableMap.registerListenersAddAction((officer, entities) -> paperStatistic.onEntitySetAdded(officer, entities, intermediariesWithEntities));


        paperResult.setOfficers(officerList
            .stream()
            .filter(new OfficerFromCountryPredicate(entityMap, edgeMap, countryCode))
            .collect(Collectors.toMap(
                    (officer) -> officer, // Key
                    (officer) -> getEntitiesOfPerson(officer, edgeMap, entityMap), // Value
                    (entry1, entry2) -> entry1, // Merge if key collisions.
                    () -> observableMap // New Object. This lambda is a provider, returns something with not parameters.
            )));


        paperResult.setStatistic(paperStatistic);

        return paperResult;
    }

    public Set<Entity> getEntitiesOfPerson(Person person, Map<Integer, Map<Integer, Edge>> edgeData, Map<Integer, Entity> entityData) {
        Map<Integer, Edge> entityIdsOfPerson = edgeData.get(person.getNodeId());
        Set<Entity> entities = new HashSet<>();

        if (entityIdsOfPerson != null) {
            entities = entityIdsOfPerson.keySet()
                .stream()
                .map(id -> entityData.get(id))
                .collect(Collectors.toSet());
        }

        return entities;
    }

    /**
     * @return Pairs of Intermediary and its entities' node id.
     */
    List<EntryPair<Intermediary, Set<Integer>>> mapIntermediariesWithEntities(List<Intermediary> intermediaryList, Map<Integer, Entity>entityMap, Map<Integer, Map<Integer, Edge>> edgeMap) {
        return intermediaryList
                .stream()
                .map(intermediary -> {
                    Set<Integer> entitiesIDs = getEntitiesOfPerson(intermediary, edgeMap, entityMap)
                            .stream()
                            .collect(HashSet<Integer>::new,
                                    (set, entity) -> set.add(entity.getNodeId()),
                                    (entity1, entity2) -> entity1.addAll(entity2)
                            );
                    return new EntryPair<Intermediary, Set<Integer>>(intermediary, entitiesIDs);
                })
                .collect(toList());
    }
}
