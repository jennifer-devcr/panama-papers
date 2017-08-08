package com.intertec.paperanalyzer.factories;

import com.intertec.paperanalyzer.domainmodels.Entity;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;


public class EntityFactoryTest {

    @Test
    public void testParseEntity() throws Exception {
        Entity entityMock = mock(Entity.class);
        when(entityMock.getNodeId()).thenReturn(12);
        String line = ",,,,,,,,,,,,,,,,,,,12";

        Entity result = EntityFactory.parseEntity(line);

        assertNotNull(result);
        assertEquals(result.getNodeId(), 12);
    }

    @Test
    public void testParseEntityNull() throws Exception {
        String line = "";
        Entity result = EntityFactory.parseEntity(line);

        assertNull(result);
    }

}