package dev.hmmr.taxi.management.backend.spring.mapper;

import static dev.hmmr.taxi.management.backend.spring.dummy.LocationDummy.location;
import static dev.hmmr.taxi.management.backend.spring.dummy.LocationDummy.locationEntity;
import static dev.hmmr.taxi.management.backend.spring.dummy.LocationDummy.locationEntityWithId;
import static dev.hmmr.taxi.management.backend.spring.dummy.LocationDummy.locationWithId;
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
    final LocationEntity result = locationMapperUnderTest.toEntity(location());

    // Verify the results
    assertThat(result).isEqualTo(locationEntity());
  }

  @Test
  void testFromEntity() {
    // Run the test
    final Location result = locationMapperUnderTest.fromEntity(locationEntityWithId());

    // Verify the results
    assertThat(result).isEqualTo(locationWithId());
  }
}
