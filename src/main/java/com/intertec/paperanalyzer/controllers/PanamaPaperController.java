package com.intertec.paperanalyzer.controllers;

import com.intertec.paperanalyzer.business.PanamaPaper;
import com.intertec.paperanalyzer.domainmodels.AnalysisResult;
import com.intertec.paperanalyzer.domainmodels.OfficerInfo;
import com.intertec.paperanalyzer.domainmodels.PaperResult;
import com.intertec.paperanalyzer.services.PanamaPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class PanamaPaperController {
    @Autowired
    private PanamaPaperService panamaPaperService;

    @RequestMapping(path = "analyzePapersByCountry", method = RequestMethod.GET)
    AnalysisResult analyzePapersByCountry(@RequestParam(value = "countryCode", required = true) String countryCode) {
        PaperResult panamaResult = panamaPaperService.analyzePapersByCountry(countryCode);
        List<OfficerInfo> officers = panamaResult.getOfficers()
                .entrySet()
                .stream()
                .map(set -> new OfficerInfo(set.getKey(), set.getValue()))
                .collect(Collectors.toList());

        return new AnalysisResult(officers, panamaResult.getStatistic());
    }
}
