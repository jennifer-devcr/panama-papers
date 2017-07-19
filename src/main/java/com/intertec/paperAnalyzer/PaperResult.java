package com.intertec.paperAnalyzer;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PaperResult {
    private Map<Officer, Set<Entity>> officers;

    public PaperResult() {
        this.officers = new HashMap<Officer, Set<Entity>>();
    }

    public Map<Officer, Set<Entity>> getOfficers() {
        return officers;
    }

    public void setOfficers(Map<Officer, Set<Entity>> officers) {
        this.officers = officers;
    }
}
