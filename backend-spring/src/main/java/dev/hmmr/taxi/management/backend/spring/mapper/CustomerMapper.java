package dev.hmmr.taxi.management.backend.spring.mapper;

import dev.hmmr.taxi.management.backend.spring.model.CustomerEntity;
import dev.hmmr.taxi.management.openapi.model.Customer;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {
  public CustomerEntity toEntity(Customer customer) {
    return new CustomerEntity().setName(customer.getName());
  }

  public Customer fromEntity(CustomerEntity customerEntity) {
    if (customerEntity == null) {
      return null;
    }
    return new Customer().id(customerEntity.getId()).name(customerEntity.getName());
  }
}
