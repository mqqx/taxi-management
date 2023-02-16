package dev.hmmr.taxi.management.backend.spring.dummy;

import dev.hmmr.taxi.management.backend.spring.model.LocationEntity;
import dev.hmmr.taxi.management.openapi.model.Location;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.test.util.ReflectionTestUtils;

@NoArgsConstructor(access = AccessLevel.NONE)
public class LocationDummy {
  private static final String DESCRIPTION = "München";

  public static Location location() {
    return new Location().description(DESCRIPTION);
  }

  public static Location locationWithId() {
    final Location location = location();
    location.setId(1);
    return location;
  }

  public static LocationEntity locationEntity() {
    return new LocationEntity().setDescription(DESCRIPTION);
  }

  public static LocationEntity locationEntityWithId() {
    final LocationEntity locationEntity = locationEntity();
    ReflectionTestUtils.setField(locationEntity, "id", 1);
    return locationEntity;
  }
}
