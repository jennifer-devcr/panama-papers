package com.intertec.paperAnalyzer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

import static java.util.stream.Collectors.toList;

public class PersonFactory {
    public static final String OFFICER = "OFFICER";
    public static final String INTERMEDIARY = "INTERMEDIARY";
    public static final String SEPARATOR = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";

    public static List<Person> getFromFile(String filePath, String type) throws Exception {
        List<String> dataSet = FileReader.readFile(filePath);

        // return mapToObject(dataSet, type);

        ForkJoinTask<List<Person>> task = new ForkJoinPerson(dataSet, type);
        return new ForkJoinPool().invoke(task);
    }

    public static List<Person> mapToObject(List<String> dataSet, String type) {
        return dataSet.stream()// .parallelStream()
                .map(line -> createFromString(line, type))
                .filter(Objects::nonNull)
                .collect(toList());
    }

    public static Person createFromString(String line, String type) {
        String[] data = line.split(SEPARATOR); // it doesn't split cell's values with commas inside.

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
