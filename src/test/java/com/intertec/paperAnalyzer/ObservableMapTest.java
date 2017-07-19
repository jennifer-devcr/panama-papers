package com.intertec.paperAnalyzer;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.*;

import static org.testng.Assert.*;


public class ObservableMapTest {
    @DataProvider(name = "ObservableMapTestData")
    public Object[][] ObservableMapTestData() {
        // Officers.
        Officer officerJohn = new Officer("John", 1, new HashSet<>(), new HashSet<>());
        Officer officerSarah = new Officer("Sarah", 2, new HashSet<>(), new HashSet<>());

        // Entities.
        Entity corpA = new Entity("Corp A", "Active", new HashSet<>(), new HashSet<>(), 3);
        Entity corpB = new Entity("Corp B", "Active", new HashSet<>(), new HashSet<>(), 4);
        Set<Entity> entitiesA = new HashSet<Entity>();
        Set<Entity> entitiesB = new HashSet<Entity>();
        entitiesA.add(corpA);
        entitiesB.add(corpB);

        return new Object[][] {
                {officerJohn, officerSarah, entitiesA, entitiesB, 2}
        };
    }

    @Test(dataProvider = "ObservableMapTestData")
    public void testPut(Officer officerJohn, Officer officerSarah, Set<Entity> entitiesA, Set<Entity> entitiesB, int numItems) throws Exception {
        ObservableMap<Map<Officer, Set<Entity>>> observableMap = new ObservableMap<Map<Officer, Set<Entity>>>(new HashMap<>());
        observableMap.put(officerJohn, entitiesA);
        observableMap.put(officerSarah, entitiesB);

        assertEquals(observableMap.size(), numItems);
    }

    @Test(dataProvider = "ObservableMapTestData")
    public void testNotifyListenersAddAction(Officer officerJohn, Officer officerSarah, Set<Entity> entitiesA, Set<Entity> entitiesB, int numItems) throws Exception {
        List<Officer> officers = new ArrayList<>();
        ObservableMap<Map<Officer, Set<Entity>>> observableMap = new ObservableMap<Map<Officer, Set<Entity>>>(new HashMap<>());

        observableMap.registerListenersAddAction((keyOfficer, valueEntity) -> {
            officers.add(keyOfficer);
            return true;
        });

        observableMap.put(officerJohn, entitiesA);
        observableMap.put(officerSarah, entitiesB);

        assertEquals(officers.size(), numItems);
    }
}