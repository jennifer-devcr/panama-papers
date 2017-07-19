package com.intertec.paperAnalyzer;

import java.util.Set;

public class Intermediary implements Person {
    private String name;
    private int nodeId;
    private String internalId;
    private Set<String> countryCode;
    private Set<String> country;
    private String status;
    private String address;

    public Intermediary() {}

    public Intermediary(String name, Set<String> countryCode, Set<String> country, String status, String address, int nodeId, String internalId) {
        this.name = name;
        this.nodeId = nodeId;
        this.internalId = internalId;
        this.countryCode = countryCode;
        this.country = country;
        this.status = status;
        this.address = address;
    }

    @Override
    public Set<String> getCountryCode() {
        return this.countryCode;
    }

    @Override
    public Set<String> getCountry() {
        return this.country;
    }

    @Override
    public int getNodeId() {
        return nodeId;
    }
}
