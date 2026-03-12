package com.example.classactivity6;

import com.example.classactivity6.config.AppConfig;
import com.example.classactivity6.service.GreetingService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TutorialsApplication {

    public static void main(String[] args) {

        System.out.println("USE CASE 4: Prototype Scope");

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        GreetingService p1 = context.getBean(GreetingService.class);
        GreetingService p2 = context.getBean(GreetingService.class);

        System.out.println("Object 1: " + p1);
        System.out.println("Object 2: " + p2);
        System.out.println("Same object? " + (p1 == p2));

        context.close();
    }
}