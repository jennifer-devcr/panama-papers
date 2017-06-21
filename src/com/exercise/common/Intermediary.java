package com.exercise.common;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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

    public Intermediary(String[] data) {
        this.name = data[0] != null ? data[0] : "";
        this.countryCode = data[4] != null ? data[4] : "";
        this.country = data[5] != null ? data[5] : "";
        this.status = data[6] != null ? data[6] : "";
        this.address = data[2] != null ? data[2] : "";
        this.nodeId = data[7] != null ? Integer.parseInt(data[7]) : 0;
        this.internalId = data[1] != null ? data[1] : "";
    }


    public String getCountryCode() {
        return this.countryCode;
    }

    public String getCountry() {
        return this.country;
    }


    public static List<Intermediary> mapToObject (Stream<String> dataSet) {
        List<Intermediary> intermediaries = new ArrayList<Intermediary>();

        dataSet.forEach((line) -> {
            String[] rawData = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); // it doesn't split cell's values with commas inside.

            if (rawData.length >= 8) {
                intermediaries.add(new Intermediary(rawData));
            }
        });

        return intermediaries;
    }

    public static List<Intermediary> getIntermediariesFromFile(String filePath) throws Exception {
        Stream<String> dataSet = FileReader.readFile(filePath);

        return mapToObject(dataSet);
    }
}
