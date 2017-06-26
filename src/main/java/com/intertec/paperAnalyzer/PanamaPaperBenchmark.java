package com.intertec.paperAnalyzer;

import com.google.caliper.Benchmark;
import com.google.caliper.api.VmOptions;
import java.util.ArrayList;
import java.util.List;

@VmOptions("-XX:-TieredCompilation")
public class PanamaPaperBenchmark {
    @Benchmark
    public void getPeopleCountriesList(int reps) {
        PanamaPaper papers = new PanamaPaper();
        List<String> countries = new ArrayList<>();

        for (int i = 0; i < reps; i++) {
            countries = papers.getPeopleCountriesList();
        }
    }
}