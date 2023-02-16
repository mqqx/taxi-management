package dev.hmmr.taxi.management.backend.spring.mapper;

import static dev.hmmr.taxi.management.backend.spring.dummy.DriverDummy.driver;
import static dev.hmmr.taxi.management.backend.spring.dummy.DriverDummy.driverEntity;
import static dev.hmmr.taxi.management.backend.spring.dummy.DriverDummy.driverEntityWithId;
import static dev.hmmr.taxi.management.backend.spring.dummy.DriverDummy.driverWithId;
import static org.assertj.core.api.Assertions.assertThat;

import dev.hmmr.taxi.management.backend.spring.model.DriverEntity;
import dev.hmmr.taxi.management.openapi.model.Driver;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@FieldDefaults(level = AccessLevel.PRIVATE)
class DriverMapperTest {

  DriverMapper driverMapperUnderTest;

  @BeforeEach
  void setUp() {
    driverMapperUnderTest = new DriverMapper();
  }

  @Test
  void testToEntity() {
    // Run the test
    final DriverEntity result = driverMapperUnderTest.toEntity(driver());

    // Verify the results
    assertThat(result).isEqualTo(driverEntity());
  }

  @Test
  void testToEntityFromExistingEntity() {
    // Run the test
    final DriverEntity result = driverMapperUnderTest.toEntity(driver(), driverEntityWithId());

    // Verify the results
    assertThat(result).isEqualTo(driverEntityWithId());
  }

  @Test
  void testFromEntity() {
    // Run the test
    final Driver result = driverMapperUnderTest.fromEntity(driverEntityWithId());

    // Verify the results
    assertThat(result).isEqualTo(driverWithId());
  }
}
