package com.intertec.paperAnalyzer;

import java.util.Set;

public class Entity {
    private String name;
    private String status;
    private Set<String> countryCode;
    private Set<String> country;
    private int nodeId;

    public Entity(String name, String status, Set<String> countryCode, Set<String> country, int nodeId) {
        this.name = name;
        this.status = status;
        this.countryCode = countryCode;
        this.country = country;
        this.nodeId = nodeId;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public Set<String> getCountryCode() {
        return countryCode;
    }

    public Set<String> getCountry() {
        return country;
    }

    public int getNodeId() {
        return nodeId;
    }
}
