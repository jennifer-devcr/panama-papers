package com.exercise.common;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Officer {
    private String name;
    private int nodeId;
    private String countryCode;
    private String country;

    public Officer() {}

    public Officer(String name, int nodeId, String countryCode, String country) {
        this.name = name;
        this.nodeId = nodeId;
        this.countryCode = countryCode;
        this.country = country;
    }

    public Officer(String[] data) {
        this.name = data[0] != null ? data[0] : "";
        this.countryCode = data[3] != null ? data[3] : "";
        this.country = data[4] != null ? data[4] : "";
        this.nodeId = data[5] != null ? Integer.parseInt(data[5]) : 0;
    }


    public String getCountryCode() {
        return this.countryCode;
    }

    public String getCountry() {
        return this.country;
    }


    public static List<Officer> mapToObject(Stream<String> dataSet) {
        List<Officer> officers = new ArrayList<Officer>();

        dataSet.forEach((line) -> {
            String[] rawData = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); // it doesn't split cell's values with commas inside.

            if (rawData.length >= 8) {
                officers.add(new Officer(rawData));
            }
        });

        return officers;
    }

    public static List<Officer> getOfficersFromFile(String filePath) throws Exception {
        Stream<String> dataSet = FileReader.readFile(filePath);

        return mapToObject(dataSet);
    }
}
