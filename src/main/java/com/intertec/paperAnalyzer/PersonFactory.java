package com.intertec.paperAnalyzer;

import java.util.List;
import java.util.Objects;
import static java.util.stream.Collectors.toList;

public class PersonFactory {
    public static final String SEPARATOR = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";

    public static List<Person> parseLinesToOfficers(List<String> lines) throws Exception {
        return lines.parallelStream()
                .map(PersonFactory::parseOfficer)
                .filter(Objects::nonNull)
                .collect(toList());
    }

    public static List<Person> parseLinesToIntermediaries(List<String> lines) throws Exception {
        // ForkJoinTask<List<Person>> task = new ForkJoinPerson(lines, PersonFactory::parseIntermediary);
        // return new ForkJoinPool().invoke(task);

        return lines.parallelStream()
                .map(PersonFactory::parseIntermediary)
                .filter(Objects::nonNull)
                .collect(toList());
    }

    public static Person parseOfficer (String line) {
        String[] data = line.split(SEPARATOR); // it doesn't split cell's values with commas inside.

        if (data.length >= 6) {
            String name = data[0] != null ? data[0] : "";
            String countryCode = data[3] != null ? data[3] : "";
            String country = data[4] != null ? data[4] : "";
            int nodeId = data[5] != null ? Integer.parseInt(data[5]) : 0;

            return new Officer(name, nodeId, countryCode, country);

        }

        return null;
    }

    public static Person parseIntermediary (String line) {
        String[] data = line.split(SEPARATOR); // it doesn't split cell's values with commas inside.

        if (data.length >= 8) {
            String name = data[0] != null ? data[0] : "";
            String countryCode = data[4] != null ? data[4] : "";
            String country = data[5] != null ? data[5] : "";
            String status = data[6] != null ? data[6] : "";
            String address = data[2] != null ? data[2] : "";
            int nodeId = data[7] != null ? Integer.parseInt(data[7]) : 0;
            String internalId = data[1] != null ? data[1] : "";

            return new Intermediary(name, countryCode, country, status, address, nodeId, internalId);
        }

        return null;
    }
}
