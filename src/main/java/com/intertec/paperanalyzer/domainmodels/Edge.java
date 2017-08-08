package com.intertec.paperanalyzer.domainmodels;


public class Edge {
    private int personNodeId;
    private int entityNodeId;

    public Edge(int personNodeId, int entityNodeId) {
        this.personNodeId = personNodeId;
        this.entityNodeId = entityNodeId;
    }

    public int getPersonNodeId() {
        return personNodeId;
    }

    public int getEntityNodeId() {
        return entityNodeId;
    }
}
