package com.exercise.common;

import java.util.ArrayList;
import java.util.List;

public class Intermediary {
    private String name;
    private int nodeId;
    private String internalId;
    private String countryCode;
    private String country;
    private String status;
    private String address;

    public Intermediary() {}

    public Intermediary(String name, String countryCode, String country, String status, String address, int nodeId, String internalId) {
        this.name = name;
        this.nodeId = nodeId;
        this.internalId = internalId;
        this.countryCode = countryCode;
        this.country = country;
        this.status = status;
        this.address = address;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public String getCountry() {
        return this.country;
    }


    public List<Intermediary> mapToObject (List<String> dataSet) {
        List<Intermediary> intermediaries = new ArrayList<Intermediary>();

        for (String line : dataSet) {
            String[] rawData = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); // it doesn't split cell's values with commas inside.

            if (rawData.length >= 8) {
                String name = rawData[0] != null ? rawData[0] : "";
                String countryCode = rawData[4] != null ? rawData[4] : "";
                String country = rawData[5] != null ? rawData[5] : "";
                String status = rawData[6] != null ? rawData[6] : "";
                String address = rawData[2] != null ? rawData[2] : "";
                int nodeId = rawData[7] != null ? Integer.parseInt(rawData[7]) : 0;
                String internalId = rawData[1] != null ? rawData[1] : "";

                intermediaries.add(new Intermediary(name, countryCode, country, status, address, nodeId, internalId));
            }
        }

        return intermediaries;
    }

    public List<Intermediary> getIntermediariesFromFile(String filePath) throws Exception {
        List<String> dataSet = FileReader.readFile(filePath);
        List<Intermediary> intermediaries = mapToObject(dataSet);

        return intermediaries;
    }
}
