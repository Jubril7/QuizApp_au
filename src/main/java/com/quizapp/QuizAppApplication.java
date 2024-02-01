package com.quizapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.quizapp.entity")
public class QuizAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuizAppApplication.class, args);
    }

}
