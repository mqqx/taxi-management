package dev.hmmr.taxi.management.backend.spring.service;

import static dev.hmmr.taxi.management.backend.spring.dummy.CustomerDummy.customer;
import static dev.hmmr.taxi.management.backend.spring.dummy.CustomerDummy.customerEntity;
import static dev.hmmr.taxi.management.backend.spring.dummy.CustomerDummy.customerEntityWithId;
import static dev.hmmr.taxi.management.backend.spring.dummy.CustomerDummy.customerWithId;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.hmmr.taxi.management.backend.spring.mapper.CustomerMapper;
import dev.hmmr.taxi.management.backend.spring.repository.CustomerRepository;
import dev.hmmr.taxi.management.openapi.model.Customer;
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
  @Mock CustomerMapper mockCustomerMapper;

  CustomerService customerServiceUnderTest;

  @BeforeEach
  void setUp() {
    customerServiceUnderTest = new CustomerService(mockCustomerRepository, mockCustomerMapper);
  }

  @Test
  void testAdd() {
    // Setup
    when(mockCustomerMapper.toEntity(customer())).thenReturn(customerEntity());

    // Run the test
    customerServiceUnderTest.add(customer());

    // Verify the results
    verify(mockCustomerRepository).save(customerEntity());
  }

  @Test
  void testFindAll() {
    // Setup
    when(mockCustomerMapper.fromEntity(customerEntityWithId())).thenReturn(customerWithId());

    // Configure CustomerRepository.findAll(...).
    when(mockCustomerRepository.findAll()).thenReturn(List.of(customerEntityWithId()));

    // Run the test
    final List<Customer> result = customerServiceUnderTest.findAll();

    // Verify the results
    assertThat(result).isEqualTo(List.of(customerWithId()));
  }

  @Test
  void testFindAll_CustomerRepositoryReturnsNoItems() {
    // Setup
    when(mockCustomerRepository.findAll()).thenReturn(emptyList());

    // Run the test
    final List<Customer> result = customerServiceUnderTest.findAll();

    // Verify the results
    assertThat(result).isEqualTo(emptyList());
  }
}
