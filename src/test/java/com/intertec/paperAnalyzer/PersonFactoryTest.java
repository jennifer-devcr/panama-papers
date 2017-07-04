package com.intertec.paperAnalyzer;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PersonFactoryTest {
    @Test
    public void testParseOfficer() throws Exception {
        String str = "John,,,CRC,Costa Rica,2345";
        Person person = PersonFactory.parseOfficer(str);

        assertTrue(person != null);
    }

    @Test
    public void testParseIntermediary() throws Exception {
        String str = "John,1,345 Street,,CRC,Costa Rica,Active,2345";
        Person person = PersonFactory.parseIntermediary(str);

        assertTrue(person != null);
    }

    @Test
    public void testParseOfficerFromEmptyString() throws Exception {
        String str = "";
        Person person = PersonFactory.parseOfficer(str);

        assertTrue(person == null);
    }

    @Test
    public void testParseIntermediaryFromEmptyString() throws Exception {
        String str = "";
        Person person = PersonFactory.parseIntermediary(str);

        assertTrue(person == null);
    }

    @Test
    public void testParseLinesToOfficers() throws Exception {
        List<String> strs = new ArrayList<>();
        strs.add("John,,,CRC,Costa Rica,2345");
        strs.add("Sarah,,,US,United States,1234");
        List<Person> people = PersonFactory.parseLinesToOfficers(strs);

        assertTrue(people != null);
        assertEquals(true, people.size() > 0);
    }

    @Test
    public void testParseLinesToIntermediaries() throws Exception {
        List<String> strs = new ArrayList<>();
        strs.add("John,1,345 Street,,CRC,Costa Rica,Active,2345");
        strs.add("Sarah,2,\"Av 4, 67 Street\",,US,United States,Active,1234");
        List<Person> people = PersonFactory.parseLinesToIntermediaries(strs);

        assertTrue(people != null);
        assertEquals(true, people.size() > 0);
    }

    @Test
    public void testParseEmptyListToOfficers() throws Exception {
        List<String> strs = new ArrayList<>();
        List<Person> people = PersonFactory.parseLinesToOfficers(strs);

        assertTrue(people != null);
        assertEquals(true, people.size() == 0);
    }

    @Test
    public void testParseEmptyListToIntermediaries() throws Exception {
        List<String> strs = new ArrayList<>();
        List<Person> people = PersonFactory.parseLinesToIntermediaries(strs);

        assertTrue(people != null);
        assertEquals(true, people.size() == 0);
    }
}