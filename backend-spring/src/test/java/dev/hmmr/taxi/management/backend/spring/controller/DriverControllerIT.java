package dev.hmmr.taxi.management.backend.spring.controller;

import static dev.hmmr.taxi.management.backend.spring.dummy.DriverDummy.driver;
import static dev.hmmr.taxi.management.backend.spring.dummy.DriverDummy.driverEntity;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hmmr.taxi.management.backend.spring.model.DriverEntity;
import dev.hmmr.taxi.management.backend.spring.repository.DriverRepository;
import dev.hmmr.taxi.management.openapi.model.Driver;
import java.util.List;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
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
class DriverControllerIT {
  @Autowired MockMvc mockMvc;
  @Autowired DriverRepository driverRepository;

  static ObjectMapper objectMapper = new ObjectMapper();

  @BeforeEach
  void setUp() {
    driverRepository.deleteAll();
  }

  @Test
  void testAddDriver() throws Exception {
    // Setup
    assertThat(driverRepository.findAll()).isEmpty();

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                post(linkTo(methodOn(DriverController.class).addDriver(null)).toUri())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(driver()))
                    .accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    assertThat(driverRepository.findAll())
        .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
        .containsExactlyElementsOf(singletonList(driverEntity()));
  }

  @Test
  void testGetDrivers() throws Exception {
    // Setup
    driverRepository.save(driverEntity());

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                get(linkTo(methodOn(DriverController.class).getDrivers()).toUri())
                    .accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(
            objectMapper.readValue(
                response.getContentAsString(), new TypeReference<List<Driver>>() {}))
        .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
        .containsExactlyElementsOf(singletonList(driver()));
  }

  @Test
  void testGetDrivers_DriverServiceReturnsNoItems() throws Exception {
    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                get(linkTo(methodOn(DriverController.class).getDrivers()).toUri())
                    .accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.getContentAsString()).isEqualTo("[]");
  }

  @Test
  void testUpdateDriver() throws Exception {
    // Setup
    DriverEntity entity = driverEntity();
    entity = driverRepository.save(entity);

    assertThat(entity.isActive()).isTrue();
    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                put(linkTo(methodOn(DriverController.class).updateDriver(null)).toUri())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        objectMapper.writeValueAsString(driver().id(entity.getId()).active(false)))
                    .accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    assertThat(driverRepository.findAll())
        .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
        .containsExactlyElementsOf(singletonList(driverEntity().setActive(false)));
  }

  @Test
  void testUpdateDriverNotFound() throws Exception {
    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                put(linkTo(methodOn(DriverController.class).updateDriver(null)).toUri())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(driver().id(-1).active(false)))
                    .accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
  }
}
