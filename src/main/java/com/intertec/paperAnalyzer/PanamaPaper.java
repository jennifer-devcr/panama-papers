package com.intertec.paperAnalyzer;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PanamaPaper implements PanamaPaperAnalyser {
    public static final String COMMA_SEMICOLON_SEPARATOR = ",|;";

    @Override
    public List<String> getPeopleCountryCodesList() {
        Set<String> countries = new HashSet<>();

        try {
            Set<String> peopleCodeSet;
            String intermediariesFilePath = "data-csv\\Intermediaries.csv";
            List<String> intermediaryLines = FileReader.readFile(intermediariesFilePath);
            List<Person> people = PersonFactory.parseLinesToIntermediaries(intermediaryLines);

            String officersFilePath = "data-csv\\Officers.csv";
            List<String> officerLines = FileReader.readFile(officersFilePath);
            people.addAll(PersonFactory.parseLinesToOfficers(officerLines));

            peopleCodeSet = people.parallelStream()
                    .map(p -> p.getCountryCode())
                    .filter(codes -> codes != null)
                    .filter(codes -> !codes.isEmpty())
                    .collect(Collectors.toSet());

            for (String codeSet : peopleCodeSet) {
                countries.addAll(splitCodesString(COMMA_SEMICOLON_SEPARATOR, codeSet));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>(countries);
    }

    public List<String> splitCodesString (String regex, String codes) {
        Set<String> codeSet = new HashSet<String>();
        List<String> codeArray = Arrays.asList(codes.split(regex));

        codeSet.addAll( codeArray.stream()
                .filter(code -> code != null)
                .filter(code -> !code.isEmpty())
                .collect(Collectors.toSet()) );

        return new ArrayList<String>(codeSet);
    }
}
