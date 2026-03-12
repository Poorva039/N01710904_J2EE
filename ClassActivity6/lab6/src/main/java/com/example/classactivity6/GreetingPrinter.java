package com.example.classactivity6;

import com.example.classactivity6.service.GreetingService;

public class GreetingPrinter {

    private final GreetingService greetingService;

    public GreetingPrinter(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    public void printMessage() {
        System.out.println(greetingService.sayHello());
    }
}