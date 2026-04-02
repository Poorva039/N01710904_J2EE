package com.example.coffeeshop.controllers;

import com.example.coffeeshop.models.Coffee;
import com.example.coffeeshop.services.CoffeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/coffees")
public class CoffeeController {

    private final CoffeeService coffeeService;

    public CoffeeController(CoffeeService coffeeService) {
        this.coffeeService = coffeeService;
    }

    @GetMapping
    public String getAllCoffees(Model model) {
        model.addAttribute("coffees", coffeeService.getAllCoffees());
        return "coffee-list";
    }

    @GetMapping("/add")
    public String showAddCoffeeForm(Model model) {
        model.addAttribute("coffee", new Coffee());
        return "coffee-add";
    }

    @PostMapping("/add")
    public String addCoffee(@ModelAttribute Coffee coffee) {
        coffeeService.addCoffee(coffee);
        return "redirect:/coffees";
    }

    @GetMapping("/{id}")
    public String getCoffeeDetails(@PathVariable Long id, Model model) {
        coffeeService.getCoffeeById(id).ifPresent(coffee -> model.addAttribute("coffee", coffee));
        return "coffee-details";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        coffeeService.getCoffeeById(id).ifPresent(coffee -> model.addAttribute("coffee", coffee));
        return "coffee-edit";
    }

    @PutMapping("/update/{id}")
    public String updateCoffee(@PathVariable Long id, @ModelAttribute Coffee coffee) {
        coffeeService.updateCoffee(id, coffee);
        return "redirect:/coffees";
    }

    @GetMapping("/delete/{id}")
    public String deleteCoffee(@PathVariable Long id) {
        coffeeService.deleteCoffee(id);
        return "redirect:/coffees";
    }
}