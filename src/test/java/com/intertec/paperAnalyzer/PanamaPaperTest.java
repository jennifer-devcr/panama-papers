package com.intertec.paperAnalyzer;

import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class PanamaPaperTest {
    @Test
    public void getPeopleCountriesList() throws Exception {
        PanamaPaper papers = new PanamaPaper();
        List<String> countries = papers.getPeopleCountriesList();

        assertNotNull(countries);
        assertFalse(0 == countries.size());
    }
}