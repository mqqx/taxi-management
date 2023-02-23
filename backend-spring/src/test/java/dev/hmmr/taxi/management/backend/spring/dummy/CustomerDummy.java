package dev.hmmr.taxi.management.backend.spring.dummy;

import dev.hmmr.taxi.management.backend.spring.model.CustomerEntity;
import dev.hmmr.taxi.management.openapi.model.Customer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.test.util.ReflectionTestUtils;

@NoArgsConstructor(access = AccessLevel.NONE)
public class CustomerDummy {
  private static final String DESCRIPTION = "Herr Müller";
  public static final int ID = 1;

  public static Customer customer() {
    return new Customer().name(DESCRIPTION);
  }

  public static Customer customerWithId() {
    final Customer customer = customer();
    customer.setId(ID);
    return customer;
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
