package com.intertec.paperAnalyzer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

public class PaperStatistic {
    private EntryPair<Officer, Integer> officerWithMoreEntities;
    private EntryPair<String, Integer> countryWithMoreEntities;
    private Map<String, Integer> amountOfEntitiesByCountry;


    private EntryPair<Intermediary, Integer> intermediaryAssistedMoreOfficers;
    private Map<Intermediary, Integer> amountOfOfficersByIntermediary;


    public PaperStatistic() {
        this.amountOfEntitiesByCountry = new HashMap<>();
        this.amountOfOfficersByIntermediary = new HashMap<>();
        this.countryWithMoreEntities = new EntryPair<String, Integer>("", -1);
        this.intermediaryAssistedMoreOfficers = new EntryPair<Intermediary, Integer>(null, -1);
        this.officerWithMoreEntities = new EntryPair<Officer, Integer>(null, -1);
    }

    public EntryPair<Officer, Integer> getOfficerWithMoreEntities() {
        return officerWithMoreEntities;
    }

    public void setOfficerWithMoreEntities(EntryPair<Officer, Integer> officerWithMoreEntities) {
        this.officerWithMoreEntities = officerWithMoreEntities;
    }

    public EntryPair<String, Integer> getCountryWithMoreEntities() {
        return countryWithMoreEntities;
    }

    public void setCountryWithMoreEntities(EntryPair<String, Integer> countryWithMoreEntities) {
        this.countryWithMoreEntities = countryWithMoreEntities;
    }



    private void checkEntitiesAmount(Officer officer, Set<Entity> entities) {
        int amount = entities.size();

        if (this.officerWithMoreEntities.getValue() < amount) {
            this.officerWithMoreEntities.setEntry(officer);
            this.officerWithMoreEntities.setValue(amount);
        }
    }

    private void checkEntitiesInCountryAmount() {
        EntryPair<String, Integer> countryEntry = amountOfEntitiesByCountry
            .entrySet()
            .stream()
            .reduce((country1, country2) -> country1.getValue() < country2.getValue() ? country2 : country1)
            .map(entry -> new EntryPair<String, Integer>(entry.getKey(), entry.getValue()))
            .orElse(new EntryPair<String, Integer>("", 0));

        if (this.countryWithMoreEntities.getValue() < countryEntry.getValue()) {
            this.countryWithMoreEntities.setEntry(countryEntry.getEntry());
            this.countryWithMoreEntities.setValue(countryEntry.getValue());
        }
    }

    private void checkIntermediariesAmount() {
        EntryPair<Intermediary, Integer> intermediaryEntry = amountOfOfficersByIntermediary
                .entrySet()
                .stream()
                .reduce((intermediary1, intermediary2) -> intermediary1.getValue() < intermediary2.getValue() ? intermediary2 : intermediary1)
                .map(entry -> new EntryPair<Intermediary, Integer>(entry.getKey(), entry.getValue()))
                .orElse(new EntryPair<Intermediary, Integer>(null, 0));

        if (this.intermediaryAssistedMoreOfficers.getValue() < intermediaryEntry.getValue()) {
            this.intermediaryAssistedMoreOfficers.setEntry(intermediaryEntry.getEntry());
            this.intermediaryAssistedMoreOfficers.setValue(intermediaryEntry.getValue());
        }
    }







    // Map<CountryCode, Integer>
    private void sumAmountOfEntitiesInCountry(Set<Entity> entities) {
        this.amountOfEntitiesByCountry = entities.stream()
                .map(this::getCountriesOfEntity)
                .reduce(new HashMap<>(), (accumulatedMap, map) -> {
                    accumulatedMap.putAll(map);
                    return accumulatedMap;
                });
    }
            //  country code, amount
    private Map<String, Integer> getCountriesOfEntity(Entity entity) {
        return entity.getCountryCode()
                .stream()
                .map(this::sumEntityInCountry)
                .collect(Collectors.toMap(
                        entry -> entry.getEntry(),
                        entry -> entry.getValue(),
                        (entry1, entry2) -> entry1
                ));
    }

    private EntryPair<String, Integer> sumEntityInCountry(String countryCode) {
        Integer amount = this.amountOfEntitiesByCountry.get(countryCode);
        return new EntryPair<String, Integer>(countryCode, (amount != null ? ++amount : 1));
    }












    private void sumOfficersAssistedByIntermediary(Officer officer, Set<Entity> entities, Map<Integer, Set<Intermediary>> intermediaries) {
        this.amountOfOfficersByIntermediary = entities
                .stream()
                .map(entity -> intermediaries.get(entity.getNodeId()))
                .filter(set -> !set.isEmpty())
                .map(this::getAmountOfIntermediary)
                .reduce(new HashMap<>(), (accumulatedMap, map) -> {
                    accumulatedMap.putAll(map);
                    return accumulatedMap;
                });
    }

    private Map<Intermediary, Integer> getAmountOfIntermediary(Set<Intermediary> intermediaries) {
        return intermediaries
                .stream()
                .map(this::sumIntermediary)
                .collect(Collectors.toMap(
                        entry -> entry.getEntry(),
                        entry -> entry.getValue(),
                        (entry1, entry2) -> entry1
                ));
    }

    private EntryPair<Intermediary, Integer> sumIntermediary(Intermediary intermediary) {
        Integer amount = this.amountOfOfficersByIntermediary.get(intermediary);
        return new EntryPair<Intermediary, Integer>(intermediary, (amount != null ? ++amount : 1));
    }



    public boolean onEntitySetAdded(Officer officer, Set<Entity> entities) {
        checkEntitiesAmount(officer, entities);

        sumAmountOfEntitiesInCountry(entities);
        checkEntitiesInCountryAmount();

        // sumOfficersAssistedByIntermediary(officer, entities, intermediaries);
        // checkIntermediariesAmount();

        return true;
    }
}
