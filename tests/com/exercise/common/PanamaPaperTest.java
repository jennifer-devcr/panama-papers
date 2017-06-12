package com.exercise.common;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PanamaPaperTest {
    @Test
    void getPeopleCountriesList() {
        PanamaPaper papers = new PanamaPaper();
        List<String> countries = papers.getPeopleCountriesList();

        assertNotNull(countries);
        assertFalse(0 == countries.size());
    }
}