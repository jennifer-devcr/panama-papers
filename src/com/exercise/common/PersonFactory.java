package com.exercise.common;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PersonFactory {
    public static final String OFFICER = "OFFICER";
    public static final String INTERMEDIARY = "INTERMEDIARY";

    public static Set<String> getCountryList(String type, String filePath) throws Exception {
        Set<String> countries = new HashSet<String>();

        if (type == INTERMEDIARY) {
            List<Intermediary> intermediaries = Intermediary.getIntermediariesFromFile(filePath);

            for(Intermediary person : intermediaries) {
                countries.add(person.getCountry());
            }

        } else if (type == OFFICER) {
            List<Officer> officers = Officer.getOfficersFromFile(filePath);

            for(Officer person : officers) {
                countries.add(person.getCountry());
            }
        }

        return countries;
    }
}
