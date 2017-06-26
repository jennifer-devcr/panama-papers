package com.intertec.paperAnalyzer;

public class Officer implements Person {
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

    @Override
    public String getCountry() {
        return this.country;
    }
}
