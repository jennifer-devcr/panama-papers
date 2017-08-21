package com.intertec.paperanalyzer.domainmodels;


import java.util.Set;

public class OfficerInfo {
    private Officer officer;
    private Set<Entity> entities;

    public OfficerInfo(Officer officer, Set<Entity> entities) {
        this.officer = officer;
        this.entities = entities;
    }

    public Officer getOfficer() {
        return officer;
    }

    public void setOfficer(Officer officer) {
        this.officer = officer;
    }

    public Set<Entity> getEntities() {
        return entities;
    }

    public void setEntities(Set<Entity> entities) {
        this.entities = entities;
    }
}
