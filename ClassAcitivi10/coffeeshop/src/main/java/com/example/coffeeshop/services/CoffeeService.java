package com.example.coffeeshop.services;

import com.example.coffeeshop.models.Coffee;
import com.example.coffeeshop.repository.CoffeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CoffeeService {

    private final CoffeeRepository coffeeRepository;

    public CoffeeService(CoffeeRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;
    }

    public Coffee addCoffee(Coffee coffee) {
        return coffeeRepository.save(coffee);
    }

    public List<Coffee> getAllCoffees() {
        return coffeeRepository.findAll();
    }

    public Optional<Coffee> getCoffeeById(Long id) {
        return coffeeRepository.findById(id);
    }

    public Optional<Coffee> updateCoffee(Long id, Coffee updatedCoffee) {
        return coffeeRepository.findById(id).map(existingCoffee -> {
            existingCoffee.setName(updatedCoffee.getName());
            existingCoffee.setPrice(updatedCoffee.getPrice());
            existingCoffee.setDescription(updatedCoffee.getDescription());
            return coffeeRepository.save(existingCoffee);
        });
    }

    public void deleteCoffee(Long id) {
        coffeeRepository.deleteById(id);
    }
}