package dev.hmmr.taxi.management.backend.spring.repository;

import dev.hmmr.taxi.management.backend.spring.model.CustomerEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface CustomerRepository extends ListCrudRepository<CustomerEntity, Integer> {}
