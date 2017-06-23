package com.exercise.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PanamaPaper implements PanamaPaperAnalyser {
    @Override
    public List<String> getPeopleCountriesList() {
        Set<String> countries = new HashSet<String>();

        try {
            String intermediariesFilePath = "data-csv\\Intermediaries.csv";
            String officersFilePath = "data-csv\\Officers.csv";
            List<Person> people = PersonFactory.getFromFile(intermediariesFilePath, PersonFactory.INTERMEDIARY);
            people.addAll(PersonFactory.getFromFile(officersFilePath, PersonFactory.OFFICER));

            countries = people.stream()
                    .map(p -> p.getCountry())
                    .filter(country -> country.length() > 0)
                    .collect(Collectors.toSet());

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return new ArrayList<String>(countries);
    }
}
