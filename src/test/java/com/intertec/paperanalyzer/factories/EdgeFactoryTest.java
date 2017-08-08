package com.intertec.paperanalyzer.factories;

import com.intertec.paperanalyzer.domainmodels.Edge;
import org.testng.annotations.Test;

import static org.testng.Assert.*;


public class EdgeFactoryTest {
    @Test
    public void testParseEdge() throws Exception {
        String line = "1,,3";
        Edge result = EdgeFactory.parseEdge(line);

        assertNotNull(result);
        assertEquals(result.getPersonNodeId(), 1);
        assertEquals(result.getEntityNodeId(), 3);
    }

    @Test
    public void testParseEdgeNull() throws Exception {
        String line = "";
        Edge result = EdgeFactory.parseEdge(line);

        assertNull(result);
    }

}