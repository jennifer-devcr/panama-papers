package com.intertec.paperAnalyzer;

import java.util.Objects;
import java.util.Set;

public class Officer implements Person {
    private String name;
    private int nodeId;
    private Set<String> countryCode;
    private Set<String> country;

    public Officer() {}

    public Officer(String name, int nodeId, Set<String> countryCode, Set<String> country) {
        this.name = name;
        this.nodeId = nodeId;
        this.countryCode = countryCode;
        this.country = country;
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

    @Override
    public int hashCode(){
        return Objects.hash(this.name, this.nodeId);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if(!(o instanceof Officer)) {
            return false;
        }

        Officer officer = (Officer) o;

        return Objects.equals(this.name, officer.name) && Objects.equals(this.nodeId, officer.nodeId);
    }
}
