package com.intertec.paperanalyzer.domainmodels;


import com.intertec.paperanalyzer.business.PaperStatistic;

import java.util.List;

public class AnalysisResult {
    private List<OfficerInfo> results;
    private PaperStatistic statistics;

    public AnalysisResult(List<OfficerInfo> results, PaperStatistic statistics) {
        this.results = results;
        this.statistics = statistics;
    }

    public List<OfficerInfo> getResults() {
        return results;
    }

    public void setResults(List<OfficerInfo> results) {
        this.results = results;
    }

    public PaperStatistic getStatistics() {
        return statistics;
    }

    public void setStatistics(PaperStatistic statistics) {
        this.statistics = statistics;
    }
}
