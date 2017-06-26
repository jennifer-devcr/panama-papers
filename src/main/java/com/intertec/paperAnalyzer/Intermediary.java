package com.intertec.paperAnalyzer;

public class Intermediary implements Person {
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

    @Override
    public String getCountry() {
        return this.country;
    }
}
