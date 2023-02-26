package dev.hmmr.taxi.management.backend.spring.service;

import static dev.hmmr.taxi.management.backend.spring.dummy.CustomerDummy.customer;
import static dev.hmmr.taxi.management.backend.spring.dummy.CustomerDummy.customerEntity;
import static dev.hmmr.taxi.management.backend.spring.dummy.CustomerDummy.customerProjection;
import static dev.hmmr.taxi.management.backend.spring.dummy.CustomerDummy.customerWithId;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.hmmr.taxi.management.backend.spring.mapper.CustomerMapper;
import dev.hmmr.taxi.management.backend.spring.repository.CustomerRepository;
import dev.hmmr.taxi.management.openapi.model.Customer;
import java.time.LocalDate;
import java.time.Month;
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
  void testFindAllByPeriod() {
    // Setup
    when(mockCustomerRepository.findAllByFromAndToDate(
            LocalDate.of(2000, Month.JANUARY, 1), LocalDate.now()))
        .thenReturn(List.of(customerProjection()));
    when(mockCustomerMapper.fromProjection(customerProjection())).thenReturn(customerWithId());

    // Run the test
    final List<Customer> result = customerServiceUnderTest.findAllByPeriod(null, null);

    // Verify the results
    assertThat(result).isEqualTo(List.of(customerWithId()));
  }

  @Test
  void testFindAllByPeriodReturnsNoItems() {
    // Setup
    when(mockCustomerRepository.findAllByFromAndToDate(
            LocalDate.of(2000, Month.JANUARY, 1), LocalDate.now()))
        .thenReturn(emptyList());

    // Run the test
    final List<Customer> result = customerServiceUnderTest.findAllByPeriod(null, null);

    // Verify the results
    assertThat(result).isEqualTo(emptyList());
  }
}
