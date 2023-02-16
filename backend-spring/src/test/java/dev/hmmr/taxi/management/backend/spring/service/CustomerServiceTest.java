package dev.hmmr.taxi.management.backend.spring.service;

import static dev.hmmr.taxi.management.backend.spring.dummy.CustomerDummy.customer;
import static dev.hmmr.taxi.management.backend.spring.dummy.CustomerDummy.customerEntity;
import static dev.hmmr.taxi.management.backend.spring.dummy.CustomerDummy.customerEntityWithId;
import static dev.hmmr.taxi.management.backend.spring.dummy.CustomerDummy.customerWithId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.hmmr.taxi.management.backend.spring.model.CustomerEntity;
import dev.hmmr.taxi.management.backend.spring.repository.CustomerRepository;
import dev.hmmr.taxi.management.openapi.model.Customer;
import java.util.Collections;
import java.util.List;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class CustomerServiceTest {
  @Mock CustomerRepository mockCustomerRepository;

  CustomerService customerServiceUnderTest;

  @BeforeEach
  void setUp() {
    customerServiceUnderTest = new CustomerService(mockCustomerRepository);
  }

  @Test
  void testAdd() {
    // Run the test
    customerServiceUnderTest.add(customer());

    // Verify the results
    verify(mockCustomerRepository).save(customerEntity());
  }

  @Test
  void testFindAll() {
    // Configure CustomerRepository.findAll(...).
    final List<CustomerEntity> customerEntities = List.of(customerEntityWithId());
    when(mockCustomerRepository.findAll()).thenReturn(customerEntities);

    // Run the test
    final List<Customer> result = customerServiceUnderTest.findAll();

    // Verify the results
    assertThat(result).isEqualTo(List.of(customerWithId()));
  }

  @Test
  void testFindAll_CustomerRepositoryReturnsNoItems() {
    // Setup
    when(mockCustomerRepository.findAll()).thenReturn(Collections.emptyList());

    // Run the test
    final List<Customer> result = customerServiceUnderTest.findAll();

    // Verify the results
    assertThat(result).isEqualTo(Collections.emptyList());
  }
}
