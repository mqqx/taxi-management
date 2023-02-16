package dev.hmmr.taxi.management.backend.spring.service;

import dev.hmmr.taxi.management.backend.spring.model.CustomerEntity;
import dev.hmmr.taxi.management.backend.spring.repository.CustomerRepository;
import dev.hmmr.taxi.management.openapi.model.Customer;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CustomerService {
  CustomerRepository customerRepository;

  public void add(Customer customer) {
    final CustomerEntity customerEntity = new CustomerEntity().setName(customer.getName());

    customerRepository.save(customerEntity);
  }

  public List<Customer> findAll() {
    return customerRepository.findAll().stream()
        .map(
            customerEntity ->
                new Customer().id(customerEntity.getId()).name(customerEntity.getName()))
        .toList();
  }
}
