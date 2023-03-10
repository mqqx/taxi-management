package dev.hmmr.taxi.management.backend.spring.service;

import static dev.hmmr.taxi.management.backend.spring.dummy.LocationDummy.start;
import static dev.hmmr.taxi.management.backend.spring.dummy.LocationDummy.startEntity;
import static dev.hmmr.taxi.management.backend.spring.dummy.LocationDummy.startEntityWithId;
import static dev.hmmr.taxi.management.backend.spring.dummy.LocationDummy.startWithId;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.hmmr.taxi.management.backend.spring.mapper.LocationMapper;
import dev.hmmr.taxi.management.backend.spring.model.LocationEntity;
import dev.hmmr.taxi.management.backend.spring.repository.LocationRepository;
import dev.hmmr.taxi.management.openapi.model.Location;
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
  @Mock LocationMapper mockLocationMapper;

  LocationService locationServiceUnderTest;

  @BeforeEach
  void setUp() {
    locationServiceUnderTest = new LocationService(mockLocationRepository, mockLocationMapper);
  }

  @Test
  void testAdd() {
    // Setup
    when(mockLocationMapper.toEntity(start())).thenReturn(startEntity());

    // Run the test
    locationServiceUnderTest.add(start());

    // Verify the results
    verify(mockLocationRepository).save(startEntity());
  }

  @Test
  void testFindAll() {
    // Setup
    when(mockLocationMapper.fromEntity(startEntityWithId())).thenReturn(startWithId());

    // Configure LocationRepository.findAll(...).
    final List<LocationEntity> locationEntities = List.of(startEntityWithId());
    when(mockLocationRepository.findAll()).thenReturn(locationEntities);

    // Run the test
    final List<Location> result = locationServiceUnderTest.findAll();

    // Verify the results
    assertThat(result).isEqualTo(List.of(startWithId()));
  }

  @Test
  void testFindAll_LocationRepositoryReturnsNoItems() {
    // Setup
    when(mockLocationRepository.findAll()).thenReturn(emptyList());

    // Run the test
    final List<Location> result = locationServiceUnderTest.findAll();

    // Verify the results
    assertThat(result).isEqualTo(emptyList());
  }
}
