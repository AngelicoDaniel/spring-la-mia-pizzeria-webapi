package org.lessons.springpizzeria.controller;

import jakarta.validation.Valid;
import org.lessons.springpizzeria.model.Offer;
import org.lessons.springpizzeria.model.Pizza;
import org.lessons.springpizzeria.repository.PizzaRepository;
import org.lessons.springpizzeria.repository.SpecialOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/offers")
public class OfferController {
    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private SpecialOfferRepository specialOfferRepository;

    @GetMapping("/create")
    public String create(@RequestParam("pizzaId") Integer pizzaId, Model model) {
        Offer offer = new Offer();
        offer.setStartDate(LocalDate.now());
        Optional<Pizza> pizza = pizzaRepository.findById(pizzaId);
        if (pizza.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "pizza with " + pizzaId + " not found");
        }
        offer.setPizza(pizza.get());
        model.addAttribute("offer", offer);
        return "/offers/form";
    }

    @PostMapping("/create")
    public String doCreate(@Valid @ModelAttribute("offer") Offer formOffer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/offers/form";
        }
        specialOfferRepository.save(formOffer);
        return "redirect:/pizzas/" + formOffer.getPizza().getId();
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Optional<Offer> offer = specialOfferRepository.findById(id);
        if (offer.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("offer", offer.get());
        return "/offers/form";
    }

    @PostMapping("/edit/{id}")
    public String doEdit(@PathVariable Integer id,
                         @Valid @ModelAttribute("offer") Offer formOffer, BindingResult bindingResult) {
        Optional<Offer> offerToEdit = specialOfferRepository.findById(id);
        if (offerToEdit.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        formOffer.setId(id);
        specialOfferRepository.save(formOffer);
        return "redirect:/pizzas/" + formOffer.getPizza().getId();
    }

    @PostMapping("delete/{id}")
    public String delete(@PathVariable Integer id) {
        Optional<Offer> offerToDelete = specialOfferRepository.findById(id);
        if (offerToDelete.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        specialOfferRepository.delete(offerToDelete.get());
        return "redirect:/pizzas/" + offerToDelete.get().getPizza().getId();
    }
}

