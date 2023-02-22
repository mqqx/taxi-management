package dev.hmmr.taxi.management.backend.spring.service;

import static dev.hmmr.taxi.management.backend.spring.dummy.DriverDummy.driver;
import static dev.hmmr.taxi.management.backend.spring.dummy.DriverDummy.driverEntity;
import static dev.hmmr.taxi.management.backend.spring.dummy.DriverDummy.driverEntityWithId;
import static dev.hmmr.taxi.management.backend.spring.dummy.DriverDummy.driverWithId;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatRuntimeException;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.hmmr.taxi.management.backend.spring.mapper.DriverMapper;
import dev.hmmr.taxi.management.backend.spring.model.DriverEntity;
import dev.hmmr.taxi.management.backend.spring.repository.DriverRepository;
import dev.hmmr.taxi.management.openapi.model.Driver;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class DriverServiceTest {
  @Mock DriverRepository mockDriverRepository;
  @Mock DriverMapper mockDriverMapper;

  DriverService driverServiceUnderTest;

  @BeforeEach
  void setUp() {
    driverServiceUnderTest = new DriverService(mockDriverRepository, mockDriverMapper);
  }

  @Test
  void testAdd() {
    // Setup
    when(mockDriverMapper.toEntity(driver())).thenReturn(driverEntity());

    // Run the test
    driverServiceUnderTest.add(driver());

    // Verify the results
    verify(mockDriverRepository).save(driverEntity());
  }

  @Test
  void testFindAll() {
    // Setup
    when(mockDriverMapper.fromEntity(driverEntityWithId())).thenReturn(driverWithId());

    // Configure DriverRepository.findAll(...).
    when(mockDriverRepository.findAll()).thenReturn(List.of(driverEntityWithId()));

    // Run the test
    final List<Driver> result = driverServiceUnderTest.findAll();

    // Verify the results
    assertThat(result).isEqualTo(List.of(driverWithId()));
  }

  @Test
  void testFindAll_DriverRepositoryReturnsNoItems() {
    // Setup
    when(mockDriverRepository.findAll()).thenReturn(emptyList());

    // Run the test
    final List<Driver> result = driverServiceUnderTest.findAll();

    // Verify the results
    assertThat(result).isEmpty();
  }

  @Test
  void testUpdate() {
    // Configure DriverRepository.findById(...).
    final DriverEntity driverEntity = driverEntityWithId();
    when(mockDriverRepository.findById(driverEntity.getId())).thenReturn(Optional.of(driverEntity));

    // Run the test
    driverServiceUnderTest.update(driverEntity.getId(), driver());

    // Verify the results
    verify(mockDriverMapper).toEntity(driver(), driverEntity);
  }

  @Test
  void testUpdateWithUnknownIdThrowsException() {
    // Run the test
    // Verify the results
    assertThatRuntimeException().isThrownBy(() -> driverServiceUnderTest.update(-1, driver()));
  }
}
