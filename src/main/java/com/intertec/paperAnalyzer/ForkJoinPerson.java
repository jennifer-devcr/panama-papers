package com.intertec.paperAnalyzer;

import java.util.List;
import java.util.concurrent.RecursiveTask;

public class ForkJoinPerson extends RecursiveTask<List<Person>> {
    public static final int THREAD_LIMIT = 1000; // What's a good size?
    private final List<String> dataLines;
    private final int start;
    private final int end;
    private final String personType;

    public ForkJoinPerson(List<String> dataLines, int start, int end, String personType) {
        this.dataLines = dataLines;
        this.start = start;
        this.end = end;
        this.personType = personType;
    }

    public ForkJoinPerson(List<String> dataLines, String personType) {
        this.dataLines = dataLines;
        this.start = 0;
        this.end = dataLines.size();
        this.personType = personType;
    }

    @Override
    protected List<Person> compute() {
        int size = this.end - this.start;

        if (size <= THREAD_LIMIT) {
            return computeSequentially();
        }

        ForkJoinPerson leftTask = new ForkJoinPerson(this.dataLines, this.start, this.start + size / 2, this.personType);
        leftTask.fork();

        ForkJoinPerson rightTask = new ForkJoinPerson(this.dataLines, this.start + size / 2, this.end, this.personType);

        List<Person> result = rightTask.compute();
        result.addAll(leftTask.join());

        return result;
    }

    private List<Person> computeSequentially() {
        List<String> subList = this.dataLines.subList(this.start, this.end);
        return PersonFactory.mapToObject(subList, this.personType);
    }
}
