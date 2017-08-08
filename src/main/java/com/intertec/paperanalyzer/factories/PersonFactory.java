package com.intertec.paperanalyzer.factories;

import com.intertec.paperanalyzer.domainmodels.Intermediary;
import com.intertec.paperanalyzer.domainmodels.Officer;

import java.util.Arrays;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class PersonFactory {
    public static final String CSV_SEPARATOR = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
    public static final String COMMA_SEMICOLON_SEPARATOR = ",|;";


    public static Officer parseOfficer (String line) {
        String[] data = line.split(CSV_SEPARATOR); // it doesn't split cell's values with commas inside.

        if (data.length >= 6) {
            String name = data[0] != null ? data[0] : "";
            String countryCodes = data[3] != null ? data[3] : "";
            String countries = data[4] != null ? data[4] : "";
            int nodeId = data[5] != null ? Integer.parseInt(data[5]) : 0;

            // Separating countries.
            Set<String> countryCodeSet = Arrays.stream(countryCodes.split(COMMA_SEMICOLON_SEPARATOR)) // ToDo: map with country name, in a country object.
                    .filter(code -> !code.isEmpty())
                    .collect(toSet());

            Set<String> countrySet = Arrays.stream(countries.split(COMMA_SEMICOLON_SEPARATOR))
                    .filter(country -> !country.isEmpty())
                    .collect(toSet());

            return new Officer(name, nodeId, countryCodeSet, countrySet);
        }

        return null;
    }

    public static Intermediary parseIntermediary (String line) {
        String[] data = line.split(CSV_SEPARATOR); // it doesn't split cell's values with commas inside.

        if (data.length >= 8) {
            String name = data[0] != null ? data[0] : "";
            String countryCodes = data[4] != null ? data[4] : "";
            String countries = data[5] != null ? data[5] : "";
            String status = data[6] != null ? data[6] : "";
            String address = data[2] != null ? data[2] : "";
            int nodeId = data[7] != null ? Integer.parseInt(data[7]) : 0;
            String internalId = data[1] != null ? data[1] : "";

            // Separating countries.
            Set<String> countryCodeSet = Arrays.stream(countryCodes.split(COMMA_SEMICOLON_SEPARATOR))
                    .filter(code -> !code.isEmpty())
                    .collect(toSet());

            Set<String> countrySet = Arrays.stream(countries.split(COMMA_SEMICOLON_SEPARATOR))
                    .filter(country -> !country.isEmpty())
                    .collect(toSet());

            return new Intermediary(name, countryCodeSet, countrySet, status, address, nodeId, internalId);
        }

        return null;
    }
}
