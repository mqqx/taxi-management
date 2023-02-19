package dev.hmmr.taxi.management.backend.spring.controller;

import static dev.hmmr.taxi.management.backend.spring.dummy.DriverDummy.driverEntity;
import static dev.hmmr.taxi.management.backend.spring.dummy.ShiftDummy.shift;
import static dev.hmmr.taxi.management.backend.spring.dummy.ShiftDummy.shiftEntity;
import static dev.hmmr.taxi.management.backend.spring.dummy.TaxiDummy.taxiEntity;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hmmr.taxi.management.backend.spring.model.DriverEntity;
import dev.hmmr.taxi.management.backend.spring.model.ShiftEntity;
import dev.hmmr.taxi.management.backend.spring.model.TaxiEntity;
import dev.hmmr.taxi.management.backend.spring.repository.DriverRepository;
import dev.hmmr.taxi.management.backend.spring.repository.ShiftRepository;
import dev.hmmr.taxi.management.backend.spring.repository.TaxiRepository;
import dev.hmmr.taxi.management.openapi.model.Shift;
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
class ShiftControllerIT {
  @Autowired MockMvc mockMvc;
  @Autowired ShiftRepository shiftRepository;
  @Autowired TaxiRepository taxiRepository;
  @Autowired DriverRepository driverRepository;
  @Autowired ObjectMapper objectMapper;

  @AfterEach
  void tearDown() {
    shiftRepository.deleteAll();
    taxiRepository.deleteAll();
    driverRepository.deleteAll();
  }

  @Test
  void testAddShift() throws Exception {
    // Setup
    final Shift shift = shift();
    final TaxiEntity taxiEntity = taxiRepository.saveAndFlush(taxiEntity());
    final DriverEntity driverEntity = driverRepository.saveAndFlush(driverEntity());
    shift.getTaxi().setId(taxiEntity.getId());
    shift.getDriver().setId(driverEntity.getId());
    final ShiftEntity expected = initShiftEntity(taxiEntity, driverEntity);

    assertThat(shiftRepository.findAll()).isEmpty();

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                post(linkTo(methodOn(ShiftController.class).addShift(null)).toUri())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(shift))
                    .accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    assertThat(shiftRepository.findAll())
        .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
        .containsExactlyElementsOf(singletonList(expected));
  }

  @Test
  void testFindShiftById() throws Exception {
    // Setup
    final ShiftEntity shiftEntity = initShiftEntity();
    shiftRepository.save(shiftEntity);

    final Shift expected = shift();
    expected.getTaxi().setId(shiftEntity.getTaxiId());
    expected.getDriver().setId(shiftEntity.getDriverId());
    expected.setId(shiftEntity.getId());

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                get(linkTo(methodOn(ShiftController.class).findShiftById(shiftEntity.getId()))
                        .toUri())
                    .accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(objectMapper.readValue(response.getContentAsByteArray(), Shift.class))
        .isEqualTo(expected);
  }

  @Test
  void testGetShifts() throws Exception {
    // Setup
    final ShiftEntity shiftEntity = initShiftEntity();
    shiftRepository.save(shiftEntity);

    final Shift expected = shift();
    expected.getTaxi().setId(shiftEntity.getTaxiId());
    expected.getDriver().setId(shiftEntity.getDriverId());

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                get(linkTo(methodOn(ShiftController.class).getShifts()).toUri())
                    .accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(
            objectMapper.readValue(
                response.getContentAsByteArray(), new TypeReference<List<Shift>>() {}))
        .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
        .containsExactlyElementsOf(singletonList(expected));
  }

  @Test
  void testGetShiftsReturnsNoItems() throws Exception {
    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                get(linkTo(methodOn(ShiftController.class).getShifts()).toUri())
                    .accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.getContentLength()).isZero();
  }

  private ShiftEntity initShiftEntity() {
    return initShiftEntity(null, null);
  }

  private ShiftEntity initShiftEntity(TaxiEntity taxiEntity, DriverEntity driverEntity) {
    if (taxiEntity == null) {
      taxiEntity = taxiRepository.saveAndFlush(taxiEntity());
    }
    if (driverEntity == null) {
      driverEntity = driverRepository.saveAndFlush(driverEntity());
    }
    return shiftEntity()
        .setTaxiId(taxiEntity.getId())
        .setDriverId(driverEntity.getId())
        .setTaxi(taxiEntity)
        .setDriver(driverEntity);
  }
}
