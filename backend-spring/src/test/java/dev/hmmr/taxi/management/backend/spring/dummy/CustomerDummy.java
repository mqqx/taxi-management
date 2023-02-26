package dev.hmmr.taxi.management.backend.spring.dummy;

import dev.hmmr.taxi.management.backend.spring.model.CustomerEntity;
import dev.hmmr.taxi.management.backend.spring.model.CustomerProjection;
import dev.hmmr.taxi.management.openapi.model.Customer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.test.util.ReflectionTestUtils;

@NoArgsConstructor(access = AccessLevel.NONE)
public class CustomerDummy {
  private static final String DESCRIPTION = "Herr Müller";
  private static final int COUNT = 2;
  public static final int ID = 1;

  public static Customer customer() {
    return new Customer().name(DESCRIPTION);
  }

  public static Customer customerWithId() {
    return customer().id(ID);
  }

  public static Customer customerWithIdAndCount() {
    return customerWithId().count(COUNT);
  }

  public static CustomerProjection customerProjection() {
    return new CustomerProjection(ID, DESCRIPTION, COUNT);
  }

  public static CustomerEntity customerEntity() {
    return new CustomerEntity().setName(DESCRIPTION);
  }

  public static CustomerEntity customerEntityWithId() {
    final CustomerEntity customerEntity = customerEntity();
    ReflectionTestUtils.setField(customerEntity, "id", ID);
    return customerEntity;
  }
}
