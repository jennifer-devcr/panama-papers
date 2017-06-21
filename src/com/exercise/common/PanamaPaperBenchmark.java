package com.exercise.common;

import com.google.caliper.Benchmark;

import java.util.ArrayList;
import java.util.List;

public class PanamaPaperBenchmark {
    @Benchmark
    List<String> getPeopleCountriesList(int reps) {
        PanamaPaper papers = new PanamaPaper();
        List<String> countries = new ArrayList<>();

        for (int i = 0; i < reps; i++) {
            countries = papers.getPeopleCountriesList();
        }

        return countries;
    }
}
