package dev.hmmr.taxi.management.backend.spring.mapper;

import dev.hmmr.taxi.management.backend.spring.model.LocationEntity;
import dev.hmmr.taxi.management.openapi.model.Location;
import org.springframework.stereotype.Service;

@Service
public class LocationMapper {
  public LocationEntity toEntity(Location location) {
    return new LocationEntity().setDescription(location.getDescription());
  }

  public Location fromEntity(LocationEntity locationEntity) {
    return new Location().id(locationEntity.getId()).description(locationEntity.getDescription());
  }
}
