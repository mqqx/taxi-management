package dev.hmmr.taxi.management.backend.spring.service;

import dev.hmmr.taxi.management.backend.spring.exception.ResourceNotFoundException;
import dev.hmmr.taxi.management.backend.spring.mapper.DriverMapper;
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
  DriverMapper driverMapper;

  public void add(Driver driver) {
    final DriverEntity driverEntity = driverMapper.toEntity(driver);
    driverRepository.save(driverEntity);
  }

  public List<Driver> findAll() {
    return driverRepository.findAllByOrderByActiveDescLastName().stream()
        .map(driverMapper::fromEntity)
        .toList();
  }

  @Transactional
  public void update(int id, Driver driver) {
    driverRepository
        .findById(id)
        .ifPresentOrElse(
            driverEntity -> driverMapper.toEntity(driver, driverEntity),
            () -> {
              throw new ResourceNotFoundException();
            });
  }
}
