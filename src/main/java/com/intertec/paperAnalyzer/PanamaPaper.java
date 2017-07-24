package com.intertec.paperAnalyzer;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.stream.Collectors.toList;

public class PanamaPaper implements PanamaPaperAnalyser {

    @Override
    public List<String> getPeopleCountryCodesList() {
        Set<String> countries = new HashSet<>();

        try {
            /*String intermediariesFilePath = "data-csv\\Intermediaries.csv";
            List<String> intermediaryLines = FileReader.readFile(intermediariesFilePath);
            List<Person> people = PersonFactory.parseLinesToIntermediaries(intermediaryLines);

            String officersFilePath = "data-csv\\Officers.csv";
            List<String> officerLines = FileReader.readFile(officersFilePath);
            people.addAll(PersonFactory.parseLinesToOfficers(officerLines));

            for (Person person : people) {
                List<String> personCountries = Arrays.asList(person.getCountryCode());
                countries.addAll(personCountries);
            }*/

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>(countries);
    }


    // InputStream processors.

    public static List<Officer> parseOfficerLines (InputStream is) {
        try {
            return FileReader.getLinesFromResource(is)
                    .parallelStream()
                    .map(PersonFactory::parseOfficer) // ToDo: work with Generics
                    .filter(Objects::nonNull)
                    .collect(toList());

        } catch (Exception e) {
            e.printStackTrace(); // In production we don't print stack trace.
        }

        return emptyList();
    }

    public static List<Intermediary> parseIntermediaryLines (InputStream is) {
        try {
            return FileReader.getLinesFromResource(is)
                    .parallelStream()
                    .map(PersonFactory::parseIntermediary)  // ToDo: work with Generics
                    .filter(Objects::nonNull)
                    .collect(toList());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return emptyList();
    }

    public static Map<Integer, Entity> parseEntityLines (InputStream is) {
        try {
            return FileReader.getLinesFromResource(is)
                    .parallelStream()
                    .map(EntityFactory::parseEntity)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toMap(Entity::getNodeId, entity -> entity, (entity1, entity2) -> entity1));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return emptyMap();
    }

    public static Map<Integer, Map<Integer, Edge>> parseEdgeLines (InputStream is) {
        try {
            return FileReader.getLinesFromResource(is)
                    .parallelStream() // todo add caliper to test with or without threads
                    .map(EdgeFactory::parseEdge)
                    .filter(Objects::nonNull)
                    .collect(Collectors.groupingBy(e -> e.getPersonNodeId(),
                             Collectors.toMap(e -> e.getEntityNodeId(),
                             Function.identity())));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return emptyMap();
    }


    // Analyzers.

    // todo add inputstreams as members
    public static PaperResult analyzePapers(InputStream officersData, InputStream intermediariesData, InputStream entitiesData, InputStream edgesData, String countryCode) {
        Map<String, Object> results = new HashMap<>();
        PaperResult paperResult = new PaperResult();
        PaperStatistic paperStatistic = new PaperStatistic();
        ObservableMap<Officer, Set<Entity>> observableMap = new ObservableMap<Officer, Set<Entity>>(new HashMap<>());


        // Parsing data lines.
        List<Officer> officerList = parseOfficerLines(officersData);
        List<Intermediary> intermediaryList = parseIntermediaryLines(intermediariesData);
        Map<Integer, Entity> entityMap = parseEntityLines(entitiesData);
        Map<Integer, Map<Integer, Edge>> edgeMap = parseEdgeLines(edgesData);


        // Map<Intermediary, Set<Entity>> intermediariesWithEntities = mapIntermediariesWithEntities(intermediaryList, entityMap, edgeMap);
        observableMap.registerListenersAddAction((officer, entities) -> paperStatistic.onEntitySetAdded(officer, entities));


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

    public static Set<Entity> getEntitiesOfPerson(Person person, Map<Integer, Map<Integer, Edge>> edgeData, Map<Integer, Entity> entityData) {
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

    /*public static Map<Integer, Set<Intermediary>> mapIntermediariesWithEntities(List<Intermediary> intermediaryList, Map<Integer, Entity>entityMap, Map<Integer, Map<Integer, Edge>> edgeMap) {
        return intermediaryList
                .stream()
                .map(intermediary -> getEntitiesOfPerson(intermediary, edgeMap, entityMap)
                        .stream()
                        .collect(HashMap<Integer, Set<Intermediary>>::new,
                                (map, entity) -> {
                                    Set<Intermediary> set = new HashSet<Intermediary>();
                                    set.add(intermediary);
                                    map.put(entity.getNodeId(), set);
                                 },
                                (entity1, entity2) -> { entity1.putAll(entity2); }
                        ))
                .reduce(new HashMap<>(), (map1, map2) -> {
                    map1.put(map2)
                });
    }*/
}
