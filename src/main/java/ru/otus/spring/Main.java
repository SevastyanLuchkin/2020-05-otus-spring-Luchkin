package ru.otus.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.service.csv.ResourceReader;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        ResourceReader reader = context.getBean(ResourceReader.class);
        System.out.println(reader.read());
    }
}