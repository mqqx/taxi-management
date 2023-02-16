package dev.hmmr.taxi.management.backend.spring.repository;

import dev.hmmr.taxi.management.backend.spring.model.TaxiEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface TaxiRepository extends ListCrudRepository<TaxiEntity, Integer> {}
