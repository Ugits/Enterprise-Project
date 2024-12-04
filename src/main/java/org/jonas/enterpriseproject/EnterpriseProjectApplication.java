package org.jonas.enterpriseproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class EnterpriseProjectApplication implements CommandLineRunner {

    @Autowired
    private ApplicationContext appContext;

    public static void main(String[] args) {
        SpringApplication.run(EnterpriseProjectApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String[] beanNames = appContext.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        System.out.println("Beans loaded by Spring Boot:");

        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }
}