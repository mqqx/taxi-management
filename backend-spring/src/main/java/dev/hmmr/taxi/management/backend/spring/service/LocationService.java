package dev.hmmr.taxi.management.backend.spring.service;

import dev.hmmr.taxi.management.backend.spring.mapper.LocationMapper;
import dev.hmmr.taxi.management.backend.spring.model.LocationEntity;
import dev.hmmr.taxi.management.backend.spring.repository.LocationRepository;
import dev.hmmr.taxi.management.openapi.model.Location;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class LocationService {
  LocationRepository locationRepository;
  LocationMapper locationMapper;

  public void add(Location location) {
    final LocationEntity locationEntity = locationMapper.toEntity(location);
    locationRepository.save(locationEntity);
  }

  public List<Location> findAll() {
    return locationRepository.findAll().stream().map(locationMapper::fromEntity).toList();
  }
}
