package com.intertec.paperanalyzer.domainmodels;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PaperResult {
    private Map<Officer, Set<Entity>> officers;
    private PaperStatistic statistic;

    public PaperResult() {
        this.officers = new HashMap<Officer, Set<Entity>>();
        this.statistic = new PaperStatistic();
    }

    public Map<Officer, Set<Entity>> getOfficers() {
        return officers;
    }

    public void setOfficers(Map<Officer, Set<Entity>> officers) {
        this.officers = officers;
    }

    public PaperStatistic getStatistic() {
        return statistic;
    }

    public void setStatistic(PaperStatistic statistic) {
        this.statistic = statistic;
    }
}
