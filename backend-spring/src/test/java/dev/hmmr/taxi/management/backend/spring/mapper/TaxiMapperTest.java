package dev.hmmr.taxi.management.backend.spring.mapper;

import static dev.hmmr.taxi.management.backend.spring.dummy.TaxiDummy.taxi;
import static dev.hmmr.taxi.management.backend.spring.dummy.TaxiDummy.taxiEntity;
import static dev.hmmr.taxi.management.backend.spring.dummy.TaxiDummy.taxiEntityWithId;
import static dev.hmmr.taxi.management.backend.spring.dummy.TaxiDummy.taxiWithId;
import static org.assertj.core.api.Assertions.assertThat;

import dev.hmmr.taxi.management.backend.spring.model.TaxiEntity;
import dev.hmmr.taxi.management.openapi.model.Taxi;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@FieldDefaults(level = AccessLevel.PRIVATE)
class TaxiMapperTest {
  TaxiMapper taxiMapperUnderTest;

  @BeforeEach
  void setUp() {
    taxiMapperUnderTest = new TaxiMapper();
  }

  @Test
  void testToEntity() {
    // Run the test
    final TaxiEntity result = taxiMapperUnderTest.toEntity(taxi());

    // Verify the results
    assertThat(result).isEqualTo(taxiEntity());
  }

  @Test
  void testFromEntity() {
    // Run the test
    final Taxi result = taxiMapperUnderTest.fromEntity(taxiEntityWithId());

    // Verify the results
    assertThat(result).isEqualTo(taxiWithId());
  }
}
