package dev.hmmr.taxi.management.backend.spring.controller;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

import dev.hmmr.taxi.management.backend.spring.service.DriverService;
import dev.hmmr.taxi.management.openapi.api.DriversApi;
import dev.hmmr.taxi.management.openapi.model.Driver;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class DriverController implements DriversApi {
  DriverService driverService;

  @Override
  public ResponseEntity<Void> addDriver(Driver driver) {
    driverService.add(driver);
    return noContent().build();
  }

  @Override
  public ResponseEntity<List<Driver>> getDrivers() {
    final List<Driver> drivers = driverService.findAll();
    return ok(drivers);
  }

  @Override
  public ResponseEntity<Void> updateDriver(Integer id, Driver driver) {
    driverService.update(id, driver);
    return noContent().build();
  }
}
