package com.exercise.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PanamaPaper implements PanamaPaperAnalyser {
    @Override
    public List<String> getPeopleCountriesList() {
        Set<String> countriesSet = new HashSet<String>();

        try {
            String intermediariesFilePath = "data-csv\\Intermediaries.csv";
            String officersFilePath = "data-csv\\Officers.csv";

            countriesSet.addAll(PersonFactory.getCountryList(PersonFactory.INTERMEDIARY, intermediariesFilePath));
            countriesSet.addAll(PersonFactory.getCountryList(PersonFactory.OFFICER, officersFilePath));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return new ArrayList(countriesSet);
    }
}
