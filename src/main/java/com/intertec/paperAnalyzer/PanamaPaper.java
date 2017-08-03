package com.intertec.paperAnalyzer;

import java.io.IOException;
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
    private LinesFileParser<List<Officer>> officerLinesParser;
    private LinesFileParser<List<Intermediary>> intermediaryLinesParser;
    private LinesFileParser<Map<Integer, Entity>> entityLinesParser;
    private LinesFileParser<Map<Integer, Map<Integer, Edge>>> edgeLinesParser;

    public PanamaPaper() {}

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

    // Analyzers.

    // todo add inputstreams as members
    public PaperResult analyzePapers(InputStream officersData, InputStream intermediariesData, InputStream entitiesData, InputStream edgesData, String countryCode) throws IOException {
        Map<String, Object> results = new HashMap<>();
        PaperResult paperResult = new PaperResult();
        PaperStatistic paperStatistic = new PaperStatistic();
        ObservableMap<Officer, Set<Entity>> observableMap = new ObservableMap<Officer, Set<Entity>>(new HashMap<>());


        // Parsing data lines.
        List<Officer> officerList = getOfficerLinesParser().parseLines(officersData);
        List<Intermediary> intermediaryList = getIntermediaryLinesParser().parseLines(intermediariesData);
        Map<Integer, Entity> entityMap = getEntityLinesParser().parseLines(entitiesData);
        Map<Integer, Map<Integer, Edge>> edgeMap = getEdgeLinesParser().parseLines(edgesData);


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
    public List<EntryPair<Intermediary, Set<Integer>>> mapIntermediariesWithEntities(List<Intermediary> intermediaryList, Map<Integer, Entity>entityMap, Map<Integer, Map<Integer, Edge>> edgeMap) {
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


    public LinesFileParser<List<Officer>> getOfficerLinesParser() {
        return officerLinesParser;
    }

    public void setOfficerLinesParser(LinesFileParser<List<Officer>> officerLinesParser) {
        this.officerLinesParser = officerLinesParser;
    }

    public LinesFileParser<List<Intermediary>> getIntermediaryLinesParser() {
        return intermediaryLinesParser;
    }

    public void setIntermediaryLinesParser(LinesFileParser<List<Intermediary>> intermediaryLinesParser) {
        this.intermediaryLinesParser = intermediaryLinesParser;
    }

    public LinesFileParser<Map<Integer, Entity>> getEntityLinesParser() {
        return entityLinesParser;
    }

    public void setEntityLinesParser(LinesFileParser<Map<Integer, Entity>> entityLinesParser) {
        this.entityLinesParser = entityLinesParser;
    }

    public LinesFileParser<Map<Integer, Map<Integer, Edge>>> getEdgeLinesParser() {
        return edgeLinesParser;
    }

    public void setEdgeLinesParser(LinesFileParser<Map<Integer, Map<Integer, Edge>>> edgeLinesParser) {
        this.edgeLinesParser = edgeLinesParser;
    }
}
