package dev.hmmr.taxi.management.backend.spring.mapper;

import static dev.hmmr.taxi.management.backend.spring.dummy.CustomerDummy.customerEntityWithId;
import static dev.hmmr.taxi.management.backend.spring.dummy.CustomerDummy.customerWithId;
import static dev.hmmr.taxi.management.backend.spring.dummy.LocationDummy.destinationEntityWithId;
import static dev.hmmr.taxi.management.backend.spring.dummy.LocationDummy.destinationWithId;
import static dev.hmmr.taxi.management.backend.spring.dummy.LocationDummy.startEntityWithId;
import static dev.hmmr.taxi.management.backend.spring.dummy.LocationDummy.startWithId;
import static dev.hmmr.taxi.management.backend.spring.dummy.TripDummy.tripEntityWithAllFieldsAndChilds;
import static dev.hmmr.taxi.management.backend.spring.dummy.TripDummy.tripEntityWithAllFieldsBesidesId;
import static dev.hmmr.taxi.management.backend.spring.dummy.TripDummy.tripWithAllFields;
import static dev.hmmr.taxi.management.backend.spring.dummy.TripDummy.tripWithAllFieldsBesidesId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import dev.hmmr.taxi.management.backend.spring.dummy.ShiftDummy;
import dev.hmmr.taxi.management.backend.spring.model.TripEntity;
import dev.hmmr.taxi.management.openapi.model.Trip;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class TripMapperTest {
  @Mock LocationMapper mockLocationMapper;
  @Mock CustomerMapper mockCustomerMapper;

  TripMapper tripMapperUnderTest;

  @BeforeEach
  void setUp() {
    tripMapperUnderTest = new TripMapper(mockLocationMapper, mockCustomerMapper);
  }

  @Test
  void testToEntity() {
    // Run the test
    final TripEntity result =
        tripMapperUnderTest.toEntity(ShiftDummy.ID, tripWithAllFieldsBesidesId());

    // Verify the results
    assertThat(result).isEqualTo(tripEntityWithAllFieldsBesidesId());
  }

  @Test
  void testFromEntity() {
    // Setup
    // Configure LocationMapper.fromEntity(...).
    when(mockLocationMapper.fromEntity(startEntityWithId())).thenReturn(startWithId());
    when(mockLocationMapper.fromEntity(destinationEntityWithId())).thenReturn(destinationWithId());

    // Configure CustomerMapper.fromEntity(...).
    when(mockCustomerMapper.fromEntity(customerEntityWithId())).thenReturn(customerWithId());

    // Run the test
    final Trip result = tripMapperUnderTest.fromEntity(tripEntityWithAllFieldsAndChilds());

    // Verify the results
    assertThat(result).isEqualTo(tripWithAllFields());
  }
}
