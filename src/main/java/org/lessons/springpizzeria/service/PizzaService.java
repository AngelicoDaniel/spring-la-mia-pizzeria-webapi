package org.lessons.springpizzeria.service;


import org.lessons.springpizzeria.exceptions.PizzaNotFoundException;
import org.lessons.springpizzeria.model.Pizza;
import org.lessons.springpizzeria.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PizzaService {

    @Autowired
    PizzaRepository pizzaRepository;


//    ------------------------------------

    public List<Pizza> getAll(Optional<String> keyword) {
        if (keyword.isEmpty()) {
            return pizzaRepository.findAll();
        } else {
            return pizzaRepository.findByNameContainingIgnoreCase(keyword.get());
        }
    }


    public Pizza getById(Integer id) throws PizzaNotFoundException {
        Optional<Pizza> pizza = pizzaRepository.findById(id);
        if (pizza.isPresent()) {
            return pizza.get();
        } else {
            throw new PizzaNotFoundException("Pizza with id " + id + " not found!");
        }
    }

    public Pizza create(Pizza pizza) {
        return pizzaRepository.save(pizza);
    }

    public Pizza update(Integer id, Pizza pizza) {
        pizza.setId(id);
        return pizzaRepository.save(pizza);
    }

    public void deleteById(Integer id) {
        pizzaRepository.deleteById(id);
    }
}