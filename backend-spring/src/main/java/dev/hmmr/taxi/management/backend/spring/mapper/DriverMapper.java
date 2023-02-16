package dev.hmmr.taxi.management.backend.spring.mapper;

import dev.hmmr.taxi.management.backend.spring.model.DriverEntity;
import dev.hmmr.taxi.management.openapi.model.Driver;
import org.springframework.stereotype.Service;

@Service
public class DriverMapper {
  public DriverEntity toEntity(Driver driver) {
    return toEntity(driver, new DriverEntity());
  }

  public DriverEntity toEntity(Driver driver, DriverEntity driverEntity) {
    return driverEntity
        .setLastName(driver.getLastName())
        .setFirstName(driver.getFirstName())
        .setAddress(driver.getAddress())
        .setBirthdate(driver.getBirthdate())
        .setPLicenceDate(driver.getpLicenceDate())
        .setActive(driver.getActive());
  }

  public Driver fromEntity(DriverEntity driverEntity) {
    return new Driver()
        .active(driverEntity.isActive())
        .address(driverEntity.getAddress())
        .id(driverEntity.getId())
        .firstName(driverEntity.getFirstName())
        .lastName(driverEntity.getLastName())
        .birthdate(driverEntity.getBirthdate())
        .pLicenceDate(driverEntity.getPLicenceDate());
  }
}
