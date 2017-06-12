package com.exercise.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class PanamaPaper implements PanamaPaperAnalyser {
    @Override
    public List<String> getPeopleCountriesList() {
        HashSet<String> countriesSet = new HashSet<String>();

        try {
            String intermediariesFilePath = "data-csv\\Intermediaries.csv";
            String officersFilePath = "data-csv\\Officers.csv";
            Intermediary intermediary = new Intermediary();
            Officer officer = new Officer();
            List<Intermediary> intermediaries = intermediary.getIntermediariesFromFile(intermediariesFilePath);
            List<Officer> officers = officer.getOfficersFromFile(officersFilePath);

            for(Intermediary person :  intermediaries) {
                countriesSet.add(person.getCountry());
            }

            for(Officer person :  officers) {
                countriesSet.add(person.getCountry());
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return new ArrayList(countriesSet);
    }
}
