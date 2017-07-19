package com.intertec.paperAnalyzer;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class OfficerFromCountryPredicate implements Predicate<Person> {
    private Map<Integer, Entity> entityData;
    private Map<Integer, Map<Integer, Edge>> edgeData;
    private String countryCode;

    public OfficerFromCountryPredicate(Map<Integer, Entity> entityData, Map<Integer, Map<Integer, Edge>> edgeData, String countryCode) {
        this.entityData = entityData;
        this.edgeData = edgeData;
        this.countryCode = countryCode;
    }

    @Override
    public boolean test(Person person) {
        return isOfficerFromCountry(person);
    }

    public boolean isOfficerFromCountry(Person person) {
        Set<String> countryCodes = person.getCountryCode();
        boolean foundCountry = false;

        if (countryCodes.isEmpty()) {
            Set<Entity> officerEntities = getEntitiesOfPerson(person);
            foundCountry = officerEntities
                    .stream()
                    .anyMatch(entity -> entity.getCountryCode().contains(countryCode));
        } else {
            foundCountry = countryCodes.contains(countryCode);
        }

        return foundCountry;
    }

    public Set<Entity> getEntitiesOfPerson(Person person) {
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
}
