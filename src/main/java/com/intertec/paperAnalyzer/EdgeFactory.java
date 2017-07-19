package com.intertec.paperAnalyzer;

public class EdgeFactory {
    private static final String CSV_SEPARATOR = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";


    public static Edge parseEdge (String line) {
        String[] data = line.split(CSV_SEPARATOR); // it doesn't split cell's values with commas inside.

        if (data.length >= 3) {
            int personNodeId = data[0] != null ? Integer.parseInt(data[0]) : 0;
            int entityNodeId = data[2] != null ? Integer.parseInt(data[2]) : 0;

            return new Edge(personNodeId, entityNodeId);
        }

        return null;
    }
}
