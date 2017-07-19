package com.intertec.paperAnalyzer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

public class PaperStatistic implements ResultListener {
    private StatisticEntry officerWithMoreEntities;
    private StatisticEntry countryWithMoreEntities;
    private HashMap<String, Integer> amountOfEntitiesByCountry;

    public PaperStatistic() {}

    private void checkEntitiesAmount(Officer officer, Set<Entity> entities) {
        int amount = entities.size();

        if (this.officerWithMoreEntities == null) {
            this.officerWithMoreEntities = new StatisticEntry(officer, amount);

        } else if (this.officerWithMoreEntities.getAmount() < amount) {
            this.officerWithMoreEntities.setPerson(officer);
            this.officerWithMoreEntities.setAmount(amount);
        }
    }

    /*private void sumAmountOfEntitiesByCountry(Set<Entity> entities) {
        entities.stream()
                .forEach(entity -> {
                    //amountOfEntitiesByCountry.putAll(entity.getCountryCode());

                    HashMap<String, Integer> entityCountries = entity.getCountryCode()
                            .stream()
                            .collect(toMap(code -> code.get, country -> 1, Integer::sum));
                })
                .map(entity -> entity.getCountryCode().stream().collect(c country -> entity)))
                .collect(toMap(
                        entry -> entry.getKey(), // The key
                        entry -> entry.getValue() // The value
                );

    }*/

    @Override
    public boolean onEntitySetAdded(Officer officer, Set<Entity> entities) {
        checkEntitiesAmount(officer, entities);
        return true;
    }
}
