package com.intertec.paperAnalyzer;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PersonFactoryTest {
    @Test
    public void createFromStringOfficer() throws Exception {
        String str = "John,,,CRC,Costa Rica,2345";
        Person person = PersonFactory.createFromString(str, PersonFactory.OFFICER);

        assertTrue(person != null);
    }

    @Test
    public void createFromStringIntermediary() throws Exception {
        String str = "John,1,345 Street,,CRC,Costa Rica,Active,2345";
        Person person = PersonFactory.createFromString(str, PersonFactory.INTERMEDIARY);

        assertTrue(person != null);
    }

    @Test
    public void createFromStringIsNull() throws Exception {
        String str = "";
        Person person = PersonFactory.createFromString(str, PersonFactory.OFFICER);

        assertTrue(person == null);
    }

    @Test
    public void testMapToObjectOfficer() throws Exception {
        List<String> strs = new ArrayList<>();
        strs.add("John,,,CRC,Costa Rica,2345");
        strs.add("Sarah,,,US,United States,1234");
        List<Person> people = PersonFactory.mapToObject(strs, PersonFactory.OFFICER);

        assertTrue(people != null);
        assertEquals(true, people.size() > 0);
    }

    @Test
    public void testMapToObjectIntermediary() throws Exception {
        List<String> strs = new ArrayList<>();
        strs.add("John,1,345 Street,,CRC,Costa Rica,Active,2345");
        strs.add("Sarah,2,\"Av 4, 67 Street\",,US,United States,Active,1234");
        List<Person> people = PersonFactory.mapToObject(strs, PersonFactory.INTERMEDIARY);

        assertTrue(people != null);
        assertEquals(true, people.size() > 0);
    }

    @Test
    public void testMapToObjectEmptyList() throws Exception {
        List<String> strs = new ArrayList<>();
        List<Person> people = PersonFactory.mapToObject(strs, PersonFactory.INTERMEDIARY);

        assertTrue(people != null);
        assertEquals(true, people.size() == 0);
    }
}