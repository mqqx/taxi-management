package dev.hmmr.taxi.management.backend.spring.controller;

import static dev.hmmr.taxi.management.backend.spring.dummy.LocationDummy.location;
import static dev.hmmr.taxi.management.backend.spring.dummy.LocationDummy.locationEntity;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hmmr.taxi.management.backend.spring.repository.LocationRepository;
import dev.hmmr.taxi.management.openapi.model.Location;
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
class LocationControllerIT {
  @Autowired MockMvc mockMvc;
  @Autowired LocationRepository locationRepository;

  @Autowired ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    locationRepository.deleteAll();
  }

  @Test
  void testAddLocation() throws Exception {
    // Setup
    assertThat(locationRepository.findAll()).isEmpty();

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                post(linkTo(methodOn(LocationController.class).addLocation(null)).toUri())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(location()))
                    .accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    assertThat(locationRepository.findAll())
        .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
        .containsExactlyElementsOf(singletonList(locationEntity()));
  }

  @Test
  void testGetLocations() throws Exception {
    // Setup
    locationRepository.save(locationEntity());

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                get(linkTo(methodOn(LocationController.class).getLocations()).toUri())
                    .accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(
            objectMapper.readValue(
                response.getContentAsByteArray(), new TypeReference<List<Location>>() {}))
        .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
        .containsExactlyElementsOf(singletonList(location()));
  }

  @Test
  void testGetLocations_LocationServiceReturnsNoItems() throws Exception {
    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                get(linkTo(methodOn(LocationController.class).getLocations()).toUri())
                    .accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.getContentLength()).isZero();
  }
}