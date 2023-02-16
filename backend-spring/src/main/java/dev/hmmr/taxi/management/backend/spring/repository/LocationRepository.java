package dev.hmmr.taxi.management.backend.spring.repository;

import dev.hmmr.taxi.management.backend.spring.model.LocationEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface LocationRepository extends ListCrudRepository<LocationEntity, Integer> {}
