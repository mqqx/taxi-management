package dev.hmmr.taxi.management.backend.spring.dummy;

import dev.hmmr.taxi.management.backend.spring.model.LocationEntity;
import dev.hmmr.taxi.management.openapi.model.Location;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.test.util.ReflectionTestUtils;

@NoArgsConstructor(access = AccessLevel.NONE)
public class LocationDummy {
  private static final String START = "München";
  private static final String DESTINATION = "Augsburg";
  public static final int START_ID = 1;
  public static final int DESTINATION_ID = 2;

  public static Location start() {
    return new Location().description(START);
  }

  public static Location destination() {
    return new Location().description(DESTINATION);
  }

  public static Location startWithId() {
    final Location location = start();
    location.setId(START_ID);
    return location;
  }

  public static Location destinationWithId() {
    final Location location = destination();
    location.setId(DESTINATION_ID);
    return location;
  }

  public static LocationEntity startEntity() {
    return new LocationEntity().setDescription(START);
  }

  public static LocationEntity destinationEntity() {
    return new LocationEntity().setDescription(DESTINATION);
  }

  public static LocationEntity startEntityWithId() {
    final LocationEntity locationEntity = startEntity();
    ReflectionTestUtils.setField(locationEntity, "id", START_ID);
    return locationEntity;
  }

  public static LocationEntity destinationEntityWithId() {
    final LocationEntity locationEntity = destinationEntity();
    ReflectionTestUtils.setField(locationEntity, "id", DESTINATION_ID);
    return locationEntity;
  }
}
