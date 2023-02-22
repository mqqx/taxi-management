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

  public static Location start() {
    return new Location().description(START);
  }

  public static Location destination() {
    return new Location().description(DESTINATION);
  }

  public static Location startWithId() {
    final Location location = start();
    location.setId(1);
    return location;
  }

  public static Location destinationWithId() {
    final Location location = destination();
    location.setId(2);
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
    ReflectionTestUtils.setField(locationEntity, "id", 1);
    return locationEntity;
  }

  public static LocationEntity destinationEntityWithId() {
    final LocationEntity locationEntity = destinationEntity();
    ReflectionTestUtils.setField(locationEntity, "id", 2);
    return locationEntity;
  }
}
