package com.intertec.paperanalyzer.domainmodels;

import java.util.Set;

public interface Person {
    public Set<String> getCountry();
    public Set<String> getCountryCode();
    public int getNodeId();
}
