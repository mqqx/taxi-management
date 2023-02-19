package dev.hmmr.taxi.management.backend.spring.mapper;

import static dev.hmmr.taxi.management.backend.spring.dummy.DriverDummy.driverEntityWithId;
import static dev.hmmr.taxi.management.backend.spring.dummy.DriverDummy.driverWithId;
import static dev.hmmr.taxi.management.backend.spring.dummy.ShiftDummy.shift;
import static dev.hmmr.taxi.management.backend.spring.dummy.ShiftDummy.shiftEntity;
import static dev.hmmr.taxi.management.backend.spring.dummy.ShiftDummy.shiftEntityWithTaxiAndDriver;
import static dev.hmmr.taxi.management.backend.spring.dummy.ShiftDummy.shiftWithId;
import static dev.hmmr.taxi.management.backend.spring.dummy.TaxiDummy.taxiEntityWithId;
import static dev.hmmr.taxi.management.backend.spring.dummy.TaxiDummy.taxiWithId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import dev.hmmr.taxi.management.backend.spring.model.ShiftEntity;
import dev.hmmr.taxi.management.openapi.model.Shift;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class ShiftMapperTest {
  @Mock TaxiMapper mockTaxiMapper;
  @Mock DriverMapper mockDriverMapper;

  ShiftMapper shiftMapperUnderTest;

  @BeforeEach
  void setUp() {
    shiftMapperUnderTest = new ShiftMapper(mockTaxiMapper, mockDriverMapper);
  }

  @Test
  void testToEntity() {
    // Run the test
    final ShiftEntity result = shiftMapperUnderTest.toEntity(shift());

    // Verify the results
    assertThat(result).isEqualTo(shiftEntity());
  }

  @Test
  void testFromEntity() {
    // Setup
    // Configure DriverMapper.fromEntity(...).
    when(mockDriverMapper.fromEntity(driverEntityWithId())).thenReturn(driverWithId());

    // Configure TaxiMapper.fromEntity(...).
    when(mockTaxiMapper.fromEntity(taxiEntityWithId())).thenReturn(taxiWithId());

    // Run the test
    final Shift result = shiftMapperUnderTest.fromEntity(shiftEntityWithTaxiAndDriver());

    // Verify the results
    assertThat(result).isEqualTo(shiftWithId());
  }
}
