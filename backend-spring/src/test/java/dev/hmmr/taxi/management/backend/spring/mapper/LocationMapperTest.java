package dev.hmmr.taxi.management.backend.spring.mapper;

import static dev.hmmr.taxi.management.backend.spring.dummy.LocationDummy.start;
import static dev.hmmr.taxi.management.backend.spring.dummy.LocationDummy.startEntity;
import static dev.hmmr.taxi.management.backend.spring.dummy.LocationDummy.startEntityWithId;
import static dev.hmmr.taxi.management.backend.spring.dummy.LocationDummy.startWithId;
import static org.assertj.core.api.Assertions.assertThat;

import dev.hmmr.taxi.management.backend.spring.model.LocationEntity;
import dev.hmmr.taxi.management.openapi.model.Location;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@FieldDefaults(level = AccessLevel.PRIVATE)
class LocationMapperTest {
  LocationMapper locationMapperUnderTest;

  @BeforeEach
  void setUp() {
    locationMapperUnderTest = new LocationMapper();
  }

  @Test
  void testToEntity() {
    // Run the test
    final LocationEntity result = locationMapperUnderTest.toEntity(start());

    // Verify the results
    assertThat(result).isEqualTo(startEntity());
  }

  @Test
  void testFromEntity() {
    // Run the test
    final Location result = locationMapperUnderTest.fromEntity(startEntityWithId());

    // Verify the results
    assertThat(result).isEqualTo(startWithId());
  }
}
