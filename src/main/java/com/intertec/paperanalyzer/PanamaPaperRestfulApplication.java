package com.intertec.paperanalyzer;

import com.intertec.paperanalyzer.business.PanamaPaper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class PanamaPaperRestfulApplication {
    public static void main(String[] args) {
        SpringApplication.run(PanamaPaperRestfulApplication.class, args);
    }
}
