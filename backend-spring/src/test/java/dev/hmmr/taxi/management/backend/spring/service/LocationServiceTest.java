package dev.hmmr.taxi.management.backend.spring.service;

import static dev.hmmr.taxi.management.backend.spring.dummy.LocationDummy.location;
import static dev.hmmr.taxi.management.backend.spring.dummy.LocationDummy.locationEntity;
import static dev.hmmr.taxi.management.backend.spring.dummy.LocationDummy.locationEntityWithId;
import static dev.hmmr.taxi.management.backend.spring.dummy.LocationDummy.locationWithId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.hmmr.taxi.management.backend.spring.model.LocationEntity;
import dev.hmmr.taxi.management.backend.spring.repository.LocationRepository;
import dev.hmmr.taxi.management.openapi.model.Location;
import java.util.Collections;
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
class LocationServiceTest {
  @Mock LocationRepository mockLocationRepository;

  LocationService locationServiceUnderTest;

  @BeforeEach
  void setUp() {
    locationServiceUnderTest = new LocationService(mockLocationRepository);
  }

  @Test
  void testAdd() {
    // Run the test
    locationServiceUnderTest.add(location());

    // Verify the results
    verify(mockLocationRepository).save(locationEntity());
  }

  @Test
  void testFindAll() {
    // Configure DriverRepository.findAll(...).
    final List<LocationEntity> locationEntities = List.of(locationEntityWithId());
    when(mockLocationRepository.findAll()).thenReturn(locationEntities);

    // Run the test
    final List<Location> result = locationServiceUnderTest.findAll();

    // Verify the results
    assertThat(result).isEqualTo(List.of(locationWithId()));
  }

  @Test
  void testFindAll_LocationRepositoryReturnsNoItems() {
    // Setup
    when(mockLocationRepository.findAll()).thenReturn(Collections.emptyList());

    // Run the test
    final List<Location> result = locationServiceUnderTest.findAll();

    // Verify the results
    assertThat(result).isEqualTo(Collections.emptyList());
  }
}
