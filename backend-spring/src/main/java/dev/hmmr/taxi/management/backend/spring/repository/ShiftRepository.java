package dev.hmmr.taxi.management.backend.spring.repository;

import dev.hmmr.taxi.management.backend.spring.model.ShiftEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface ShiftRepository extends ListCrudRepository<ShiftEntity, Integer> {}
