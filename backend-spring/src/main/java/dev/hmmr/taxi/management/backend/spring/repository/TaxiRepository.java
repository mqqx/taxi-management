package dev.hmmr.taxi.management.backend.spring.repository;

import dev.hmmr.taxi.management.backend.spring.model.TaxiEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxiRepository extends JpaRepository<TaxiEntity, Integer> {}
