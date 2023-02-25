package dev.hmmr.taxi.management.backend.spring.service;

import static dev.hmmr.taxi.management.backend.spring.dummy.ShiftDummy.shiftEntityWithId;
import static dev.hmmr.taxi.management.backend.spring.dummy.TripDummy.trip;
import static dev.hmmr.taxi.management.backend.spring.dummy.TripDummy.tripEntityWithAllFields;
import static dev.hmmr.taxi.management.backend.spring.dummy.TripDummy.tripEntityWithAllFieldsBesidesId;
import static dev.hmmr.taxi.management.backend.spring.dummy.TripDummy.tripWithAllFields;
import static dev.hmmr.taxi.management.backend.spring.dummy.TripDummy.tripWithAllFieldsBesidesId;
import static java.util.Collections.emptyList;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatRuntimeException;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.hmmr.taxi.management.backend.spring.dummy.ShiftDummy;
import dev.hmmr.taxi.management.backend.spring.dummy.TripDummy;
import dev.hmmr.taxi.management.backend.spring.mapper.TripMapper;
import dev.hmmr.taxi.management.backend.spring.model.TripEntity;
import dev.hmmr.taxi.management.backend.spring.repository.ShiftRepository;
import dev.hmmr.taxi.management.backend.spring.repository.TripRepository;
import dev.hmmr.taxi.management.openapi.model.Trip;
import java.util.List;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class TripServiceTest {

  @Mock TripRepository mockTripRepository;
  @Mock TripMapper mockTripMapper;
  @Mock ShiftRepository mockShiftRepository;

  TripService tripServiceUnderTest;

  @BeforeEach
  void setUp() {
    tripServiceUnderTest = new TripService(mockTripRepository, mockTripMapper, mockShiftRepository);
  }

  @Test
  void testAdd() {
    // Setup
    final Trip trip = tripWithAllFieldsBesidesId();
    final TripEntity tripEntity = tripEntityWithAllFieldsBesidesId();

    // Configure ShiftRepository.findById(...).
    when(mockShiftRepository.findById(ShiftDummy.ID)).thenReturn(of(shiftEntityWithId()));

    // Configure TripMapper.toEntity(...).
    when(mockTripMapper.toEntity(ShiftDummy.ID, trip)).thenReturn(tripEntity);

    // Configure TripRepository.save(...).
    when(mockTripRepository.save(tripEntity)).thenReturn(tripEntity);

    // Run the test
    tripServiceUnderTest.add(ShiftDummy.ID, trip);

    // Verify the results
    verify(mockTripRepository).save(tripEntity);
  }

  @Test
  void testAddWithUnknownShiftIdThrowsException() {
    // Run the test
    // Verify the results
    assertThatRuntimeException().isThrownBy(() -> tripServiceUnderTest.add(-1, trip()));
  }

  @Test
  void testFindByShiftId() {
    // Setup
    final TripEntity tripEntity = tripEntityWithAllFields();
    final Trip trip = tripWithAllFields();

    // Configure TripRepository.findByShiftId(...).
    when(mockTripRepository.findAllByShiftId(ShiftDummy.ID)).thenReturn(List.of(tripEntity));

    // Configure TripMapper.fromEntity(...).
    when(mockTripMapper.fromEntity(tripEntity)).thenReturn(trip);

    // Run the test
    final List<Trip> result = tripServiceUnderTest.findByShiftId(ShiftDummy.ID);

    // Verify the results
    assertThat(result).isEqualTo(List.of(trip));
  }

  @Test
  void testFindByShiftIdReturnsNoItems() {
    // Setup
    when(mockTripRepository.findAllByShiftId(ShiftDummy.ID)).thenReturn(emptyList());

    // Run the test
    final List<Trip> result = tripServiceUnderTest.findByShiftId(ShiftDummy.ID);

    // Verify the results
    assertThat(result).isEqualTo(emptyList());
  }

  @Test
  void testDelete() {
    // Setup
    // Run the test
    tripServiceUnderTest.delete(TripDummy.ID);

    // Verify the results
    verify(mockTripRepository).deleteById(TripDummy.ID);
  }
}
