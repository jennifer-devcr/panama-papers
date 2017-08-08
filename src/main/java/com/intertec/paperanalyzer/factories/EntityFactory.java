package com.intertec.paperanalyzer.factories;

import com.intertec.paperanalyzer.domainmodels.Entity;

import java.util.Arrays;
import java.util.Set;
import static java.util.stream.Collectors.toSet;

public class EntityFactory {
    public static final String CSV_SEPARATOR = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
    public static final String COMMA_SEMICOLON_SEPARATOR = ",|;";


    public static Entity parseEntity (String line) {
        String[] data = line.split(CSV_SEPARATOR); // it doesn't split cell's values with commas inside.

        if (data.length >= 20) {
            String name = data[0] != null ? data[0] : "";
            String status = data[12] != null ? data[12] : "";
            String countryCodes = data[15] != null ? data[15] : "";
            String countries = data[16] != null ? data[16] : "";
            int nodeId = data[19] != null ? Integer.parseInt(data[19]) : 0;

            // Separating countries.
            Set<String> countryCodeSet = Arrays.stream(countryCodes.split(COMMA_SEMICOLON_SEPARATOR))
                    .filter(code -> !code.isEmpty())
                    .collect(toSet());

            Set<String> countrySet = Arrays.stream(countries.split(COMMA_SEMICOLON_SEPARATOR))
                    .filter(country -> !country.isEmpty())
                    .collect(toSet());

            return new Entity(name, status, countryCodeSet, countrySet, nodeId);
        }

        return null;
    }
}
