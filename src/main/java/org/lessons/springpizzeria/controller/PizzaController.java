package org.lessons.springpizzeria.controller;

import jakarta.validation.Valid;
import org.lessons.springpizzeria.messages.AlertMessage;
import org.lessons.springpizzeria.messages.AlertMessageType;
import org.lessons.springpizzeria.model.Pizza;
import org.lessons.springpizzeria.repository.PizzaRepository;
import org.lessons.springpizzeria.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {

    @Autowired
    PizzaService pizzaService;
    @Autowired
    private PizzaRepository pizzaRepository;

    @GetMapping
    public String list(@RequestParam(name = "keyword", required = false) String searchString, Model model) {
        List<Pizza> pizzas;
        if (searchString == null || searchString.isBlank()) {
            pizzas = pizzaRepository.findAll();
        } else {
            pizzas = pizzaRepository.findByNameContainingIgnoreCase(searchString);
        }
        model.addAttribute("pizzaList", pizzas);
        model.addAttribute("searchInput", searchString);
        return "/pizzas/index";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Integer pizzaId, Model model) {
        Optional<Pizza> result = pizzaRepository.findById(pizzaId);
        if (result.isPresent()) {
            model.addAttribute("pizza", result.get());
            return "/pizzas/detail";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza with id " + pizzaId + " not found");
        }
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("pizza", new Pizza());
        return "pizzas/create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pizza", formPizza);
            return "pizzas/create";
        }

        pizzaRepository.save(formPizza);
        return "redirect:/pizzas";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Pizza pizza = getPizzaById(id);
        model.addAttribute("pizza", pizza);
        return "/pizzas/edit";
    }

    @PostMapping("/edit/{id}")
    public String doEdit(
            @PathVariable Integer id,
            @Valid @ModelAttribute("pizza") Pizza formPizza,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        Pizza pizzaToEdit = getPizzaById(id);
        if (bindingResult.hasErrors()) {
            return "/pizzas/edit";
        }

        formPizza.setId(pizzaToEdit.getId());
        pizzaRepository.save(formPizza);
        redirectAttributes.addFlashAttribute("message",
                new AlertMessage(AlertMessageType.SUCCESS, "Pizza updated!"));
        return "redirect:/pizzas";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Pizza pizzaToDelete = getPizzaById(id);
        pizzaRepository.delete(pizzaToDelete);
        redirectAttributes.addFlashAttribute("message",
                new AlertMessage(AlertMessageType.SUCCESS, "Pizza  " + pizzaToDelete.getName() + " deleted!"));
        return "redirect:/pizzas";
    }


    // Utility methods
    private Pizza getPizzaById(Integer id) {
        Optional<Pizza> result = pizzaRepository.findById(id);
        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza with id " + id + " not found");
        }
        return result.get();
    }
}