package org.lessons.springpizzeria.repository;

import org.lessons.springpizzeria.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialOfferRepository extends JpaRepository<Offer, Integer> {
}
