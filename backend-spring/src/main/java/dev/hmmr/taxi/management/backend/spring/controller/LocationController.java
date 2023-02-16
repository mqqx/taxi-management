package dev.hmmr.taxi.management.backend.spring.controller;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

import dev.hmmr.taxi.management.backend.spring.service.LocationService;
import dev.hmmr.taxi.management.openapi.api.LocationsApi;
import dev.hmmr.taxi.management.openapi.model.Location;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class LocationController implements LocationsApi {
  LocationService locationService;

  @Override
  public ResponseEntity<Void> addLocation(Location location) {
    locationService.add(location);
    return noContent().build();
  }

  @Override
  public ResponseEntity<List<Location>> getLocations() {
    return ok(locationService.findAll());
  }
}
