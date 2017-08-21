package com.intertec.paperanalyzer.services;

import com.intertec.paperanalyzer.business.PanamaPaper;
import com.intertec.paperanalyzer.domainmodels.PaperResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

@Service
public class PanamaPaperService {
    private PanamaPaper panamaPaper;

    @Resource(name = "panamaPaper")
    public void setPanamaPaper(PanamaPaper panamaPaper) {
        this.panamaPaper = panamaPaper;
    }

    public PanamaPaper getPanamaPaper() {
        return panamaPaper;
    }

    public PaperResult analyzePapersByCountry(String countryCode) {
        try {
            return panamaPaper.analyzePapers(countryCode.toUpperCase());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
