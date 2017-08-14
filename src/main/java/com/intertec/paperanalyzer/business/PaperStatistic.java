package com.intertec.paperanalyzer.business;

import com.intertec.paperanalyzer.commons.EntryPair;
import com.intertec.paperanalyzer.domainmodels.Entity;
import com.intertec.paperanalyzer.domainmodels.Intermediary;
import com.intertec.paperanalyzer.domainmodels.Officer;

import java.util.*;

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

    public EntryPair<Intermediary, Integer> getIntermediaryAssistedMoreOfficers() {
        return intermediaryAssistedMoreOfficers;
    }

    public void setIntermediaryAssistedMoreOfficers(EntryPair<Intermediary, Integer> intermediaryAssistedMoreOfficers) {
        this.intermediaryAssistedMoreOfficers = intermediaryAssistedMoreOfficers;
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


    private void sumAmountOfEntitiesInCountry(Set<Entity> entities) {
        entities.forEach(entity ->
            entity.getCountryCode()
                    .forEach(countryCode ->
                            amountOfEntitiesByCountry.put(countryCode, amountOfEntitiesByCountry.getOrDefault(countryCode, 0) + 1))
        );
    }

    /**
     * @param intermediaryPairs: Pairs of Intermediary and its entities' node id.
     */
    private void sumOfficersAssistedByIntermediary(Set<Entity> entities, List<EntryPair<Intermediary, Set<Integer>>> intermediaryPairs) {
        intermediaryPairs.stream()
        .filter(pair ->
            entities
                    .stream()
                    .anyMatch(entity -> pair.getValue().contains(entity.getNodeId()))
        )
        .forEach((pair) ->
            amountOfOfficersByIntermediary.put(pair.getEntry(), amountOfOfficersByIntermediary.getOrDefault(pair.getEntry(), 0) + 1)
        );
    }


    public boolean onEntitySetAdded(Officer officer, Set<Entity> entities, List<EntryPair<Intermediary, Set<Integer>>> intermediaryPairs) {
        checkEntitiesAmount(officer, entities);

        sumAmountOfEntitiesInCountry(entities);
        checkEntitiesInCountryAmount();

        sumOfficersAssistedByIntermediary(entities, intermediaryPairs);
        checkIntermediariesAmount();

        return true;
    }
}
