package com.intertec.paperanalyzer.benchmarks;

import com.google.caliper.api.VmOptions;

import java.util.ArrayList;
import java.util.List;

@VmOptions("-XX:-TieredCompilation")
public class PersonFactoryBenchmark {

    public List<String> createTestData() {
        List<String> data = new ArrayList<>();

        for (int i = 0; i < 3000; i++) {
            data.add("Sarah,2,\"Av 4, 67 Street\",,US,United States,Active,1234");
        }

        return data;
    }

   /* @Benchmark
    public void mapToObject(int reps) {
        List<String> data = createTestData();
        List<Person> people;
        try {
            for (int i = 0; i < reps; i++) {
                people = PersonFactory.parseLinesToIntermediaries(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
