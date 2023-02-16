package dev.hmmr.taxi.management.backend.spring.service;

import dev.hmmr.taxi.management.backend.spring.exception.ResourceNotFoundException;
import dev.hmmr.taxi.management.backend.spring.model.DriverEntity;
import dev.hmmr.taxi.management.backend.spring.repository.DriverRepository;
import dev.hmmr.taxi.management.openapi.model.Driver;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class DriverService {
  DriverRepository driverRepository;

  public void add(Driver driver) {
    final DriverEntity driverEntity = toDriverEntity(driver, new DriverEntity());

    driverRepository.save(driverEntity);
  }

  public List<Driver> findAll() {
    return driverRepository.findAll().stream()
        .map(
            driverEntity ->
                new Driver()
                    .active(driverEntity.isActive())
                    .address(driverEntity.getAddress())
                    .id(driverEntity.getId())
                    .firstName(driverEntity.getFirstName())
                    .lastName(driverEntity.getLastName())
                    .birthdate(driverEntity.getBirthdate())
                    .pLicenceDate(driverEntity.getPLicenceDate()))
        .toList();
  }

  @Transactional
  public void update(Integer id, Driver driver) {
    driverRepository
        .findById(id)
        .ifPresentOrElse(
            driverEntity -> toDriverEntity(driver, driverEntity),
            () -> {
              throw new ResourceNotFoundException();
            });
  }

  private static DriverEntity toDriverEntity(Driver driver, DriverEntity driverEntity) {
    return driverEntity
        .setLastName(driver.getLastName())
        .setFirstName(driver.getFirstName())
        .setAddress(driver.getAddress())
        .setBirthdate(driver.getBirthdate())
        .setPLicenceDate(driver.getpLicenceDate())
        .setActive(driver.getActive());
  }
}
