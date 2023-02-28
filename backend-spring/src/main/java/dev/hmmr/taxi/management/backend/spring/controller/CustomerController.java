package dev.hmmr.taxi.management.backend.spring.controller;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

import dev.hmmr.taxi.management.backend.spring.service.CustomerService;
import dev.hmmr.taxi.management.openapi.api.CustomersApi;
import dev.hmmr.taxi.management.openapi.model.Customer;
import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CustomerController implements CustomersApi {
  CustomerService customerService;

  @Override
  public ResponseEntity<Void> addCustomer(Customer customer) {
    customerService.add(customer);
    return noContent().build();
  }

  @Override
  public ResponseEntity<List<Customer>> getCustomersByPeriod(LocalDate from, LocalDate to) {
    return ok(customerService.findAllByPeriod(from, to));
  }
}
