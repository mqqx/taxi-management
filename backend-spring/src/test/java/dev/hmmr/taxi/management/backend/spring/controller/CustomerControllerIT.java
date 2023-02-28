package dev.hmmr.taxi.management.backend.spring.controller;

import static dev.hmmr.taxi.management.backend.spring.dummy.CustomerDummy.customer;
import static dev.hmmr.taxi.management.backend.spring.dummy.CustomerDummy.customerEntity;
import static dev.hmmr.taxi.management.backend.spring.dummy.CustomerDummy.customerWithIdAndCount;
import static dev.hmmr.taxi.management.backend.spring.dummy.DriverDummy.driverEntity;
import static dev.hmmr.taxi.management.backend.spring.dummy.LocationDummy.destinationEntity;
import static dev.hmmr.taxi.management.backend.spring.dummy.LocationDummy.startEntity;
import static dev.hmmr.taxi.management.backend.spring.dummy.ShiftDummy.shiftEntity;
import static dev.hmmr.taxi.management.backend.spring.dummy.TaxiDummy.taxiEntity;
import static dev.hmmr.taxi.management.backend.spring.dummy.TripDummy.tripEntity;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hmmr.taxi.management.backend.spring.model.CustomerEntity;
import dev.hmmr.taxi.management.backend.spring.model.DriverEntity;
import dev.hmmr.taxi.management.backend.spring.model.LocationEntity;
import dev.hmmr.taxi.management.backend.spring.model.ShiftEntity;
import dev.hmmr.taxi.management.backend.spring.model.TaxiEntity;
import dev.hmmr.taxi.management.backend.spring.repository.CustomerRepository;
import dev.hmmr.taxi.management.backend.spring.repository.DriverRepository;
import dev.hmmr.taxi.management.backend.spring.repository.LocationRepository;
import dev.hmmr.taxi.management.backend.spring.repository.ShiftRepository;
import dev.hmmr.taxi.management.backend.spring.repository.TaxiRepository;
import dev.hmmr.taxi.management.backend.spring.repository.TripRepository;
import dev.hmmr.taxi.management.openapi.model.Customer;
import java.util.List;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@FieldDefaults(level = AccessLevel.PRIVATE)
class CustomerControllerIT {
  @Autowired MockMvc mockMvc;
  @Autowired CustomerRepository customerRepository;
  @Autowired ObjectMapper objectMapper;
  @Autowired DriverRepository driverRepository;
  @Autowired TaxiRepository taxiRepository;
  @Autowired ShiftRepository shiftRepository;
  @Autowired LocationRepository locationRepository;
  @Autowired TripRepository tripRepository;

  @AfterEach
  void setUp() {
    tripRepository.deleteAll();
    shiftRepository.deleteAll();
    driverRepository.deleteAll();
    taxiRepository.deleteAll();
    locationRepository.deleteAll();
    customerRepository.deleteAll();
  }

  @Test
  void testAddCustomer() throws Exception {
    // Setup
    assertThat(customerRepository.findAll()).isEmpty();

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                post(linkTo(methodOn(CustomerController.class).addCustomer(null)).toUri())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(customer()))
                    .accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    assertThat(customerRepository.findAll())
        .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
        .containsExactlyElementsOf(singletonList(customerEntity()));
  }

  @Test
  void testGetCustomers() throws Exception {
    // Setup
    setupDummyTripsWithCustomer();

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                get(linkTo(methodOn(CustomerController.class).getCustomersByPeriod(null, null))
                        .toUri())
                    .accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(
            objectMapper.readValue(
                response.getContentAsByteArray(), new TypeReference<List<Customer>>() {}))
        .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
        .containsExactlyElementsOf(singletonList(customerWithIdAndCount()));
  }

  @Test
  void testGetCustomersReturnsNoItems() throws Exception {
    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                get(linkTo(methodOn(CustomerController.class).getCustomersByPeriod(null, null))
                        .toUri())
                    .accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.getContentLength()).isZero();
  }

  private void setupDummyTripsWithCustomer() {
    // save driver
    final DriverEntity driverEntity = driverRepository.save(driverEntity());
    // save taxi
    final TaxiEntity taxiEntity = taxiRepository.save(taxiEntity());
    // save shift
    final ShiftEntity shiftEntity =
        shiftRepository.save(
            shiftEntity().setDriverId(driverEntity.getId()).setTaxiId(taxiEntity.getId()));
    // save location
    final LocationEntity startEntity = locationRepository.save(startEntity());
    final LocationEntity destinationEntity = locationRepository.save(destinationEntity());
    // save 2 trips with customer?
    final CustomerEntity customerEntity = customerRepository.save(customerEntity());
    tripRepository.save(
        tripRepository.save(
            tripEntity()
                .setCustomerId(customerEntity.getId())
                .setShiftId(shiftEntity.getId())
                .setStartId(startEntity.getId())
                .setDestinationId(destinationEntity.getId())));
    tripRepository.save(
        tripRepository.save(
            tripEntity()
                .setCustomerId(customerEntity.getId())
                .setShiftId(shiftEntity.getId())
                .setStartId(destinationEntity.getId())
                .setDestinationId(startEntity.getId())));
  }
}
