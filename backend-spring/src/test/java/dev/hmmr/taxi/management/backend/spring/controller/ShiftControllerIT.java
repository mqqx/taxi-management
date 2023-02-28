package dev.hmmr.taxi.management.backend.spring.controller;

import static dev.hmmr.taxi.management.backend.spring.dummy.CustomerDummy.customerEntity;
import static dev.hmmr.taxi.management.backend.spring.dummy.DriverDummy.driverEntity;
import static dev.hmmr.taxi.management.backend.spring.dummy.LocationDummy.destinationEntity;
import static dev.hmmr.taxi.management.backend.spring.dummy.LocationDummy.startEntity;
import static dev.hmmr.taxi.management.backend.spring.dummy.ShiftDummy.shift;
import static dev.hmmr.taxi.management.backend.spring.dummy.ShiftDummy.shiftEntity;
import static dev.hmmr.taxi.management.backend.spring.dummy.TaxiDummy.taxiEntity;
import static dev.hmmr.taxi.management.backend.spring.dummy.TripDummy.tripEntityWithAllFieldsBesidesId;
import static dev.hmmr.taxi.management.backend.spring.dummy.TripDummy.tripWithAllFields;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hmmr.taxi.management.backend.spring.model.CustomerEntity;
import dev.hmmr.taxi.management.backend.spring.model.DriverEntity;
import dev.hmmr.taxi.management.backend.spring.model.LocationEntity;
import dev.hmmr.taxi.management.backend.spring.model.ShiftEntity;
import dev.hmmr.taxi.management.backend.spring.model.TaxiEntity;
import dev.hmmr.taxi.management.backend.spring.model.TripEntity;
import dev.hmmr.taxi.management.backend.spring.repository.CustomerRepository;
import dev.hmmr.taxi.management.backend.spring.repository.DriverRepository;
import dev.hmmr.taxi.management.backend.spring.repository.LocationRepository;
import dev.hmmr.taxi.management.backend.spring.repository.ShiftRepository;
import dev.hmmr.taxi.management.backend.spring.repository.TaxiRepository;
import dev.hmmr.taxi.management.backend.spring.repository.TripRepository;
import dev.hmmr.taxi.management.openapi.model.Shift;
import dev.hmmr.taxi.management.openapi.model.Trip;
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
  private record TripDtoAndEntity(Trip dto, TripEntity entity) {}

  @Autowired MockMvc mockMvc;
  @Autowired TripRepository tripRepository;
  @Autowired ShiftRepository shiftRepository;
  @Autowired TaxiRepository taxiRepository;
  @Autowired DriverRepository driverRepository;
  @Autowired LocationRepository locationRepository;
  @Autowired CustomerRepository customerRepository;
  @Autowired ObjectMapper objectMapper;

  @AfterEach
  void tearDown() {
    tripRepository.deleteAll();
    shiftRepository.deleteAll();
    taxiRepository.deleteAll();
    driverRepository.deleteAll();
    locationRepository.deleteAll();
    customerRepository.deleteAll();
  }

  @Test
  void testAddShift() throws Exception {
    // Setup
    final Shift shift = shift();
    final ShiftEntity expected = initShiftEntity(shift);

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
  void testAddTrip() throws Exception {
    // Setup
    final Shift shift = shift();
    final ShiftEntity shiftEntity = initShiftEntity(shift);
    shiftRepository.save(shiftEntity);
    final TripDtoAndEntity tripDtoAndEntity = initTripDtoAndEntity(shiftEntity);

    assertThat(tripRepository.findAll()).isEmpty();

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                post(linkTo(methodOn(ShiftController.class).addTrip(shiftEntity.getId(), null))
                        .toUri())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(tripDtoAndEntity.dto))
                    .accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    assertThat(tripRepository.findAll())
        .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
        .containsExactlyElementsOf(singletonList(tripDtoAndEntity.entity));
  }

  @Test
  void testDeleteTrip() throws Exception {
    // Setup
    final ShiftEntity shiftEntity = initShiftEntity(shift());
    shiftRepository.save(shiftEntity);
    final TripEntity tripEntity = tripRepository.save(initTripDtoAndEntity(shiftEntity).entity);

    assertThat(tripRepository.findAll()).containsExactly(tripEntity);

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                delete(
                        linkTo(
                                methodOn(ShiftController.class)
                                    .deleteTrip(shiftEntity.getId(), tripEntity.getId()))
                            .toUri())
                    .accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    assertThat(tripRepository.findAll()).isEmpty();
  }

  @Test
  void testFindShiftById() throws Exception {
    // Setup
    final Shift expected = shift();
    final ShiftEntity shiftEntity = initShiftEntity(expected);
    shiftRepository.save(shiftEntity);
    expected.id(shiftEntity.getId());

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                get(linkTo(methodOn(ShiftController.class).getShiftById(shiftEntity.getId()))
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
    final Shift expected = shift();
    final ShiftEntity shiftEntity = initShiftEntity(expected);
    shiftRepository.save(shiftEntity);
    expected.id(shiftEntity.getId());

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                get(linkTo(methodOn(ShiftController.class).getShiftsByPeriod(null, null)).toUri())
                    .accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(
            objectMapper.readValue(
                response.getContentAsByteArray(), new TypeReference<List<Shift>>() {}))
        .containsExactlyElementsOf(singletonList(expected));
  }

  @Test
  void testGetShiftsReturnsNoItems() throws Exception {
    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                get(linkTo(methodOn(ShiftController.class).getShiftsByPeriod(null, null)).toUri())
                    .accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.getContentLength()).isZero();
  }

  @Test
  void testGetTrips() throws Exception {
    // Setup
    final Shift shift = shift();
    final ShiftEntity shiftEntity = initShiftEntity(shift);
    shiftRepository.save(shiftEntity);
    final TripDtoAndEntity tripDtoAndEntity = initTripDtoAndEntity(shiftEntity);
    tripRepository.save(tripDtoAndEntity.entity);

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                get(linkTo(methodOn(ShiftController.class).getTrips(shiftEntity.getId())).toUri())
                    .accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(
            objectMapper.readValue(
                response.getContentAsByteArray(), new TypeReference<List<Trip>>() {}))
        .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
        .containsExactlyElementsOf(singletonList(tripDtoAndEntity.dto));
  }

  @Test
  void testUpdateShift() throws Exception {
    // Setup
    final Shift shift = shift();
    final ShiftEntity shiftEntity = initShiftEntity(shift);
    shiftRepository.save(shiftEntity);
    shift.id(shiftEntity.getId());

    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                put(linkTo(methodOn(ShiftController.class).updateShift(shiftEntity.getId(), null))
                        .toUri())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(shift.endMileage(Integer.MAX_VALUE)))
                    .accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    assertThat(shiftRepository.findAll())
        .containsExactlyElementsOf(singletonList(shiftEntity.setEndMileage(Integer.MAX_VALUE)));
  }

  @Test
  void testUpdateShiftNotFound() throws Exception {
    // Run the test
    final MockHttpServletResponse response =
        mockMvc
            .perform(
                put(linkTo(methodOn(ShiftController.class).updateShift(-1, null)).toUri())
                    .content(objectMapper.writeValueAsString(shift().endMileage(Integer.MAX_VALUE)))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // Verify the results
    assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
  }

  private TripDtoAndEntity initTripDtoAndEntity(ShiftEntity createdShift) {
    final LocationEntity startEntity = locationRepository.save(startEntity());
    final LocationEntity destinationEntity = locationRepository.save(destinationEntity());
    final CustomerEntity customerEntity = customerRepository.save(customerEntity());
    final Trip tripWithSavedIds = tripWithAllFields().shiftId(createdShift.getId());
    tripWithSavedIds.getStart().id(startEntity.getId());
    tripWithSavedIds.getDestination().id(destinationEntity.getId());
    tripWithSavedIds.getCustomer().id(customerEntity.getId());

    final TripEntity expected =
        tripEntityWithAllFieldsBesidesId()
            .setShiftId(createdShift.getId())
            .setStartId(startEntity.getId())
            .setStart(startEntity)
            .setDestinationId(destinationEntity.getId())
            .setDestination(destinationEntity)
            .setCustomerId(customerEntity.getId())
            .setCustomer(customerEntity);

    return new TripDtoAndEntity(tripWithSavedIds, expected);
  }

  private ShiftEntity initShiftEntity(Shift shift) {
    final TaxiEntity taxiEntity = taxiRepository.saveAndFlush(taxiEntity());
    final DriverEntity driverEntity = driverRepository.saveAndFlush(driverEntity());
    shift.getTaxi().setId(taxiEntity.getId());
    shift.getDriver().setId(driverEntity.getId());
    return shiftEntity()
        .setTaxiId(taxiEntity.getId())
        .setDriverId(driverEntity.getId())
        .setTaxi(taxiEntity)
        .setDriver(driverEntity);
  }
}
