package com.exercise.common;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class PersonFactory {
    public static final String OFFICER = "OFFICER";
    public static final String INTERMEDIARY = "INTERMEDIARY";

    public static List<Person> getFromFile(String filePath, String type) throws Exception {
        BufferedReader dataSet = FileReader.readFile(filePath);

        return mapToObject(dataSet, type);
    }

    public static List<Person> mapToObject(BufferedReader dataSet, String type) {
        List<Person> people = new ArrayList<Person>();

        dataSet.lines()
                .skip(1) // Skipping CSV header.
                .forEach((line) -> {
                    Person person = createFromString(line, type);
                    if (person != null) {
                        people.add(person);
                    }
                });

        return people;
    }

    public static Person createFromString(String line, String type) {
        String[] data = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); // it doesn't split cell's values with commas inside.

        if (OFFICER == type && data.length >= 6) {
            String name = data[0] != null ? data[0] : "";
            String countryCode = data[3] != null ? data[3] : "";
            String country = data[4] != null ? data[4] : "";
            int nodeId = data[5] != null ? Integer.parseInt(data[5]) : 0;

            return new Officer(name, nodeId, countryCode, country);

        } else if (INTERMEDIARY == type && data.length >= 8) {
            String name = data[0] != null ? data[0] : "";
            String countryCode = data[4] != null ? data[4] : "";
            String country = data[5] != null ? data[5] : "";
            String status = data[6] != null ? data[6] : "";
            String address = data[2] != null ? data[2] : "";
            int nodeId = data[7] != null ? Integer.parseInt(data[7]) : 0;
            String internalId = data[1] != null ? data[1] : "";

            return new Intermediary(name, countryCode, country, status, address, nodeId, internalId);
        }

        return null;
    }
}
