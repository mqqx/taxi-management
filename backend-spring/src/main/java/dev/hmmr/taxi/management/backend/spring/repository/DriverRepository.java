package dev.hmmr.taxi.management.backend.spring.repository;

import dev.hmmr.taxi.management.backend.spring.model.DriverEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface DriverRepository extends ListCrudRepository<DriverEntity, Integer> {}
