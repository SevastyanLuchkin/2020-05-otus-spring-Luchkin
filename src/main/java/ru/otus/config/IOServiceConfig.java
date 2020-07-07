package ru.otus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.service.io.IOService;
import ru.otus.service.io.impl.IOServiceImpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

@Configuration
public class IOServiceConfig {

    @Bean
    public IOService ioService() {
        new Scanner(System.in);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        return new IOServiceImpl(bufferedReader, System.out);
    }
}
