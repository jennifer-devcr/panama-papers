package com.intertec.paperAnalyzer;

import java.io.InputStream;
import java.util.*;
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

    public static PaperResult analyzePapers(InputStream officersData, InputStream intermediariesData, InputStream entitiesData, InputStream edgesData, String countryCode) { // todo add inputstreams as memebers
        PaperResult paperResult = new PaperResult();
        PaperStatistic paperStatistic = new PaperStatistic();
        ObservableMap<Map<Officer, Set<Entity>>> observableMap = new ObservableMap<Map<Officer, Set<Entity>>>(new HashMap<>());

        observableMap.registerListenersAddAction((officer, entities) -> paperStatistic.onEntitySetAdded(officer, entities));

        // Parsing data lines.
        List<Officer> officerList = parseOfficerLines(officersData);
        List<Intermediary> intermediaryList = parseIntermediaryLines(intermediariesData);
        Map<Integer, Entity> entityMap = parseEntityLines(entitiesData);
        Map<Integer, Map<Integer, Edge>> edgeMap = parseEdgeLines(edgesData);

        officerList
            .stream()
            .filter(new OfficerFromCountryPredicate(entityMap, edgeMap, countryCode))
            .reduce(observableMap,
                    (map, officer) -> {
                        map.put(officer, getEntitiesOfPerson(officer, edgeMap, entityMap));
                        return map;
                    });

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








    public void getOfficersByCountry(String countryCode) throws Exception {



       /* List<Person> filteredOfficers = officerData.parallelStream()
                // sacar entidadaes
                .filter(new OfficerFromCountryPredicate(countryCode, edgeData, entityData).or())
                .peek(officer -> findRelatedIntermediaries(officer, intermediaryData, edgeData))
                .collect(toList());

        this.paperResult.setOfficers(filteredOfficers);


        // ToDo: move the following to another thread
        this.paperStatistics.setCountryMoreEntities(findCountryMoreEntities());
        this.paperStatistics.setPersonMoreEntities(findPersonWithMoreEntities());
        */
    }

   /* public boolean isOfficerFromCountry(String countryCode, Person person, Map<String, Edge> edges, List<Entity> entities) {
        String[] countryCodes = person.getCountryCode();
        List <Entity> officerEntities = getEntitiesByPerson(person, edges, entities);
        boolean result = Arrays.binarySearch(countryCodes, countryCode) > 0; // Searching in Person HashSet y se hace containsTo de tiempo contante O(1)
        this.paperResult.addToEntitiesByPerson(person.getNodeId(), officerEntities);

        if (!result && countryCodes.length == 0) {
            result = officerEntities.stream()
                    .anyMatch(entity -> Arrays.binarySearch(entity.getCountryCode(), countryCode) > 0); // Searching in Entity. HashSet y se hace containsTo
        }

        if (result) {
            this.paperResult.addToPersonWithMoreEntities(person, officerEntities);
        }

        return result;
    }

    public List<Entity> getEntitiesByPerson(Person person, Map<String, Edge> edges, List<Entity> entities) {
        List <Entity> personEntities = entities.stream() // parallelStream()
                .filter(entity -> isEntityRelatedToPerson(entity, person, edges))
                .peek(entity -> this.paperResult.sumEntityByCountry(entity)) // sacar esto porque no va con el propocito del funcion, single responsability
                .collect(toList());

        return personEntities;
    }



    public void findRelatedIntermediaries(Person officer, List<Person> intermediaries, Map<String, Edge> edges) {
        List <Person> relatedIntermediaries = intermediaries.stream() // parallelStream()
                .filter(intermediary -> hasEntitiesRelatedToOfficer(officer, intermediary, edges))
                //.peek(intermediary -> this.paperResult.sumIntermediaryRelations(intermediary))
                .collect(toList());
    }

    public boolean hasEntitiesRelatedToOfficer(Person officer, Person intermediary, Map<String, Edge> edges) {
        List<Entity> entities = paperResult.getEntitiesByPersonNodeId(officer.getNodeId());

        if (entities != null) {
            return entities.stream().anyMatch(entity -> isEntityRelatedToPerson(entity, intermediary, edges));
        }

        return false;
    }

    public boolean isEntityRelatedToPerson(Entity entity, Person person, Map<String, Edge> edges) {
        String edgeKey = Integer.toString(person.getNodeId()) + '_' + Integer.toString(entity.getNodeId());
        return edges.get(edgeKey) != null;
    }


    public String findCountryMoreEntities() {
        return this.paperResult.getCountEntityByCountry()
                .entrySet()
                .stream()
                .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
                .get()
                .getKey();
    }

    public Person findPersonWithMoreEntities() {
        return this.paperResult.getPersonWithMoreEntities()
                .entrySet()
                .stream()
                .max((entry1, entry2) -> entry1.getKey() > entry2.getKey() ? 1 : -1)
                .get()
                .getValue();
    }*/
}
