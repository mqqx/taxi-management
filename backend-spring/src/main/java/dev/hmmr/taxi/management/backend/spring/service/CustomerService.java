package dev.hmmr.taxi.management.backend.spring.service;

import dev.hmmr.taxi.management.backend.spring.mapper.CustomerMapper;
import dev.hmmr.taxi.management.backend.spring.model.CustomerEntity;
import dev.hmmr.taxi.management.backend.spring.repository.CustomerRepository;
import dev.hmmr.taxi.management.openapi.model.Customer;
import java.time.LocalDate;
import java.time.Month;
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
  CustomerMapper customerMapper;

  public void add(Customer customer) {
    final CustomerEntity customerEntity = customerMapper.toEntity(customer);
    customerRepository.save(customerEntity);
  }

  public List<Customer> findAllByPeriod(LocalDate from, LocalDate to) {
    if (from == null) {
      from = LocalDate.of(2000, Month.JANUARY, 1);
    }

    if (to == null) {
      to = LocalDate.now();
    }

    return customerRepository.findAllByFromAndToDate(from, to).stream()
        .map(customerMapper::fromProjection)
        .toList();
  }
}
