package com.example.ejbtutorial;

import com.example.ejb.CalculatorBean;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/calculator")
public class CalculatorServlet extends HttpServlet {

    @EJB
    private CalculatorBean calculatorBean;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int add_result = calculatorBean.add(10, 5);
        int multiplication_result = calculatorBean.multiplication(10, 5);
        int division_result = calculatorBean.division(10, 5);
        int subtraction_result = calculatorBean.subtraction(10, 5);
        response.getWriter().println("Addition Result = " + add_result);
        response.getWriter().println("Multiplication Result = " + multiplication_result);
        response.getWriter().println("Division Result = " + division_result);
        response.getWriter().println("Subtraction Result = " + subtraction_result);
    }
}
