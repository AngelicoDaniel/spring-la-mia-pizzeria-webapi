package org.lessons.springpizzeria.api;


import jakarta.validation.Valid;
import org.lessons.springpizzeria.exceptions.PizzaNotFoundException;
import org.lessons.springpizzeria.model.Pizza;
import org.lessons.springpizzeria.repository.PizzaRepository;
import org.lessons.springpizzeria.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/pizzas")
public class PizzaRestController {

    @Autowired
    private PizzaRepository pizzaRepository;
    @Autowired
    private PizzaService pizzaService;


    @GetMapping
    public List<Pizza> index(
            @RequestParam Optional<String> keyword
    ) {
        return pizzaService.getAll(keyword);
    }

    @GetMapping("/{id}")
    public Pizza show(
            @PathVariable Integer id
    ) {
        try {
            return pizzaService.getById(id);
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public Pizza create(
            @Valid @RequestBody Pizza pizza
    ) {
        return pizzaService.create(pizza);
    }

    @PutMapping("/{id}")
    public Pizza update(
            @PathVariable Integer id,
            @Valid @RequestBody Pizza pizza
    ) {
        return pizzaService.update(id, pizza);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Integer id
    ) {
        pizzaService.deleteById(id);
    }
}