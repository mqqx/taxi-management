package dev.hmmr.taxi.management.backend.spring.service;

import static dev.hmmr.taxi.management.backend.spring.dummy.TaxiDummy.taxi;
import static dev.hmmr.taxi.management.backend.spring.dummy.TaxiDummy.taxiEntity;
import static dev.hmmr.taxi.management.backend.spring.dummy.TaxiDummy.taxiEntityWithId;
import static dev.hmmr.taxi.management.backend.spring.dummy.TaxiDummy.taxiWithId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.hmmr.taxi.management.backend.spring.mapper.TaxiMapper;
import dev.hmmr.taxi.management.backend.spring.repository.TaxiRepository;
import dev.hmmr.taxi.management.openapi.model.Taxi;
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
class TaxiServiceTest {
  @Mock TaxiRepository mockTaxiRepository;
  @Mock TaxiMapper mockTaxiMapper;
  TaxiService taxiServiceUnderTest;

  @BeforeEach
  void setUp() {
    taxiServiceUnderTest = new TaxiService(mockTaxiRepository, mockTaxiMapper);
  }

  @Test
  void testAdd() {
    // Setup
    when(mockTaxiMapper.toEntity(taxi())).thenReturn(taxiEntity());

    // Run the test
    taxiServiceUnderTest.add(taxi());

    // Verify the results
    verify(mockTaxiRepository).save(taxiEntity());
  }

  @Test
  void testFindAll() {
    // Setup
    when(mockTaxiMapper.fromEntity(taxiEntityWithId())).thenReturn(taxiWithId());

    // Configure TaxiRepository.findAll(...).
    when(mockTaxiRepository.findAll()).thenReturn(List.of(taxiEntityWithId()));

    // Run the test
    final List<Taxi> result = taxiServiceUnderTest.findAll();

    // Verify the results
    assertThat(result).isEqualTo(List.of(taxiWithId()));
  }

  @Test
  void testFindAll_TaxiRepositoryReturnsNoItems() {
    // Setup
    when(mockTaxiRepository.findAll()).thenReturn(Collections.emptyList());

    // Run the test
    final List<Taxi> result = taxiServiceUnderTest.findAll();

    // Verify the results
    assertThat(result).isEqualTo(Collections.emptyList());
  }
}
