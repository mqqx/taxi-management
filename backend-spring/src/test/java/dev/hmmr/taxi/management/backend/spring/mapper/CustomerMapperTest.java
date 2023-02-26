package dev.hmmr.taxi.management.backend.spring.mapper;

import static dev.hmmr.taxi.management.backend.spring.dummy.CustomerDummy.customer;
import static dev.hmmr.taxi.management.backend.spring.dummy.CustomerDummy.customerEntity;
import static dev.hmmr.taxi.management.backend.spring.dummy.CustomerDummy.customerEntityWithId;
import static dev.hmmr.taxi.management.backend.spring.dummy.CustomerDummy.customerProjection;
import static dev.hmmr.taxi.management.backend.spring.dummy.CustomerDummy.customerWithId;
import static dev.hmmr.taxi.management.backend.spring.dummy.CustomerDummy.customerWithIdAndCount;
import static org.assertj.core.api.Assertions.assertThat;

import dev.hmmr.taxi.management.backend.spring.model.CustomerEntity;
import dev.hmmr.taxi.management.openapi.model.Customer;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@FieldDefaults(level = AccessLevel.PRIVATE)
class CustomerMapperTest {

  CustomerMapper customerMapperUnderTest;

  @BeforeEach
  void setUp() {
    customerMapperUnderTest = new CustomerMapper();
  }

  @Test
  void testToEntity() {
    // Run the test
    final CustomerEntity result = customerMapperUnderTest.toEntity(customer());

    // Verify the results
    assertThat(result).isEqualTo(customerEntity());
  }

  @Test
  void testFromEntity() {
    // Run the test
    final Customer result = customerMapperUnderTest.fromEntity(customerEntityWithId());

    // Verify the results
    assertThat(result).isEqualTo(customerWithId());
  }

  @Test
  void testFromEntityNull() {
    // Run the test
    final Customer result = customerMapperUnderTest.fromEntity(null);

    // Verify the results
    assertThat(result).isNull();
  }

  @Test
  void testFromProjection() {
    // Run the test
    final Customer result = customerMapperUnderTest.fromProjection(customerProjection());

    // Verify the results
    assertThat(result).isEqualTo(customerWithIdAndCount());
  }
}
