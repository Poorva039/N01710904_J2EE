package com.example.ejb;

import jakarta.ejb.Stateless;

@Stateless
public class CalculatorBean {
    public int add(int a, int b) {
        return a + b;
    }
    public int multiplication(int a, int b) {
        return a * b;
    }
    public int division(int a, int b) {
        return a / b;
    }
    public int subtraction(int a, int b) {
        return a - b;
    }


}
