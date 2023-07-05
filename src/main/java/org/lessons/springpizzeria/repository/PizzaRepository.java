package org.lessons.springpizzeria.repository;

import org.lessons.springpizzeria.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {
    List<Pizza> findByNameContainingIgnoreCase(String name);

    // metodo per cercare pizza dal nome
    Optional<Pizza> findByName(String name);
}
