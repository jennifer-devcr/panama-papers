package com.exercise.common;

import java.util.ArrayList;
import java.util.List;

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

    public String getCountryCode() {
        return this.countryCode;
    }

    public String getCountry() {
        return this.country;
    }


    public List<Officer> mapToObject(List<String> dataSet) {
        List<Officer> officers = new ArrayList<Officer>();

        for (String line : dataSet) {
            String[] rawData = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); // it doesn't split cell's values with commas inside.

            if (rawData.length >= 8) {
                String name = rawData[0] != null ? rawData[0] : "";
                String countryCode = rawData[3] != null ? rawData[3] : "";
                String country = rawData[4] != null ? rawData[4] : "";
                int nodeId = rawData[5] != null ? Integer.parseInt(rawData[5]) : 0;

                officers.add(new Officer(name, nodeId, countryCode, country));
            }
        }

        return officers;
    }

    public List<Officer> getOfficersFromFile(String filePath) throws Exception {
        List<String> dataSet = FileReader.readFile(filePath);
        List<Officer> officers = mapToObject(dataSet);

        return officers;
    }
}
