package com.intertec.paperanalyzer;

import com.intertec.paperanalyzer.domainmodels.Person;

import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ForkJoinPerson extends RecursiveTask<List<Person>> {
    public static final int THREAD_LIMIT = 1000; // What's a good size?
    private final List<String> dataLines;
    private final int start;
    private final int end;
    private Function<String, Person> computeCallback;

    public ForkJoinPerson(List<String> dataLines, int start, int end, Function<String, Person> computeCallback) {
        this.dataLines = dataLines;
        this.start = start;
        this.end = end;
        this.computeCallback = computeCallback;
    }

    public ForkJoinPerson(List<String> dataLines, Function<String, Person> computeCallback) {
        this.dataLines = dataLines;
        this.start = 0;
        this.end = dataLines.size();
        this.computeCallback = computeCallback;
    }

    @Override
    protected List<Person> compute() {
        int size = this.end - this.start;

        if (size <= THREAD_LIMIT) {
            List<String> subList = this.dataLines.subList(this.start, this.end);
            return computeSequentially(subList);
        }

        ForkJoinPerson leftTask = new ForkJoinPerson(this.dataLines, this.start, this.start + size / 2, this.computeCallback);
        leftTask.fork();

        ForkJoinPerson rightTask = new ForkJoinPerson(this.dataLines, this.start + size / 2, this.end, this.computeCallback);

        List<Person> result = rightTask.compute();
        result.addAll(leftTask.join());

        return result;
    }

    private List<Person> computeSequentially(List<String> subList) {
        return subList.stream().map(this.computeCallback).collect(Collectors.toList());
    }
}
