package dev.hmmr.taxi.management.backend.spring.controller;

import static dev.hmmr.taxi.management.backend.spring.dummy.TaxiDummy.taxi;
import static dev.hmmr.taxi.management.backend.spring.dummy.TaxiDummy.taxiEntity;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hmmr.taxi.management.backend.spring.repository.TaxiRepository;
import dev.hmmr.taxi.management.openapi.model.Taxi;
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
class TaxiControllerIT {
  @Autowired MockMvc mockMvc;
  @Autowired TaxiRepository taxiRepository;

  @Autowired ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    taxiRepository.deleteAll();
  }

  @Test
  void testAddTaxi() throws Exception {
    // Setup
    assertThat(taxiRepository.findAll()).isEmpty();

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                post(linkTo(methodOn(TaxiController.class).addTaxi(null)).toUri())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(taxi()))
                    .accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    assertThat(taxiRepository.findAll())
        .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
        .containsExactlyElementsOf(singletonList(taxiEntity()));
  }

  @Test
  void testGetTaxis() throws Exception {
    // Setup
    taxiRepository.save(taxiEntity());

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                get(linkTo(methodOn(TaxiController.class).getTaxis()).toUri())
                    .accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(
            objectMapper.readValue(
                response.getContentAsByteArray(), new TypeReference<List<Taxi>>() {}))
        .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
        .containsExactlyElementsOf(singletonList(taxi()));
  }

  @Test
  void testGetTaxis_TaxiServiceReturnsNoItems() throws Exception {
    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                get(linkTo(methodOn(TaxiController.class).getTaxis()).toUri())
                    .accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.getContentLength()).isZero();
  }
}
