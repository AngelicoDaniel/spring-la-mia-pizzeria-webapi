package org.lessons.springpizzeria.controller;


import jakarta.validation.Valid;
import org.lessons.springpizzeria.model.Ingredient;
import org.lessons.springpizzeria.model.Pizza;
import org.lessons.springpizzeria.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

//@Controller
//@RequestMapping("/ingredients")
//public class IngredientController {
//    @Autowired
//    private IngredientRepository ingredientRepository;
//
//    @GetMapping
//    public String index(Model model, @RequestParam("edit") Optional<Integer> ingredientId) {
//        List<Ingredient> ingredientList = ingredientRepository.findAll();
//        model.addAttribute("ingredients", ingredientList);
//
//        Ingredient ingredientObj;
//        // se ho il parametro ingredientId allora cerco l'ingrediente su database
//        if (ingredientId.isPresent()) {
//            Optional<Ingredient> ingredientDb = ingredientRepository.findById(ingredientId.get());
//            // se è presente valorizzo ingredientObj con l'ingrediente da db
//            if (ingredientDb.isPresent()) {
//                ingredientObj = ingredientDb.get();
//            } else {
//                // se non è presente valorizzo ingredientObj con un ingrediente vuoto
//                ingredientObj = new Ingredient();
//            }
//        } else {
//            // se non ho il parametro ingredientObj con un ingrediente vuoto
//            ingredientObj = new Ingredient();
//        }
//        // passo al model un attributo ingredientObj per mappare il form su un oggetto di tipo Ingredient
//        model.addAttribute("ingredientObj", ingredientObj);
//        return "/ingredients/index";
//    }
//
//    @PostMapping("/save")
//    public String save(@Valid @ModelAttribute("ingredientObj") Ingredient formIngredient,
//                       BindingResult bindingResult, Model model) {
//        // verfichiamo se ci sono errori
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("ingredients", ingredientRepository.findAll());
//            return "/ingredients/index";
//        }
//        // salvare l'ingrediente
//        ingredientRepository.save(formIngredient);
//        // fa la redirect alla index
//        return "redirect:/ingredients";
//    }
//
//
//    @PostMapping("/delete/{id}")
//    public String delete(@PathVariable Integer id) {
//        // prima di eliminare l'ingrediente lo dissocio da tutte le pizze
//        Optional<Ingredient> result = ingredientRepository.findById(id);
//        if (result.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//        }
//        // ingrediente che devo eliminare
//        Ingredient ingredientToDelete = result.get();
//        // per ogni libro associato all'ingrediente da eliminare
//        for (Pizza pizza : ingredientToDelete.getPizzas()) {
//            pizza.getIngredients().remove(ingredientToDelete);
//        }
//
//        ingredientRepository.deleteById(id);
//        return "redirect:/ingredients";
//    }
//}
@Controller
@RequestMapping("/ingredients")
public class IngredientController {
    @Autowired
    private IngredientRepository ingredientRepository;

    @GetMapping
    public String index(Model model, @RequestParam("edit") Optional<Integer> ingredientId) {
        List<Ingredient> ingredientList = ingredientRepository.findAll();
        model.addAttribute("ingredients", ingredientList);

        Ingredient ingredient;
        // Check if ingredientId parameter is present
        if (ingredientId.isPresent()) {
            Optional<Ingredient> ingredientDb = ingredientRepository.findById(ingredientId.get());
            // If present, assign the ingredient from the database
            ingredient = ingredientDb.orElse(new Ingredient());
        } else {
            // If not present, create a new ingredient object
            ingredient = new Ingredient();
        }
        model.addAttribute("ingredient", ingredient);
        return "/ingredients/index";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("ingredient") Ingredient formIngredient,
                       BindingResult bindingResult, Model model) {
        // Check for errors
        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredients", ingredientRepository.findAll());
            return "/ingredients/index";
        }
        // Save the ingredient
        ingredientRepository.save(formIngredient);
        // Redirect to the index page
        return "redirect:/ingredients";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        // Dissociate the ingredient from all pizzas before deleting
        Optional<Ingredient> result = ingredientRepository.findById(id);
        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Ingredient ingredientToDelete = result.get();
        for (Pizza pizza : ingredientToDelete.getPizzas()) {
            pizza.getIngredients().remove(ingredientToDelete);
        }

        ingredientRepository.deleteById(id);
        return "redirect:/ingredients";
    }
}

