package dev.hmmr.taxi.management.backend.spring.mapper;

import dev.hmmr.taxi.management.backend.spring.model.ShiftEntity;
import dev.hmmr.taxi.management.openapi.model.Shift;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ShiftMapper {
  TaxiMapper taxiMapper;
  DriverMapper driverMapper;

  public ShiftEntity toEntity(Shift shift) {
    return new ShiftEntity()
        .setDuration(shift.getDuration())
        .setStartMileage(shift.getStartMileage())
        .setEndMileage(shift.getEndMileage())
        .setDate(shift.getDate())
        .setTaxiId(shift.getTaxi().getId())
        .setDriverId(shift.getDriver().getId());
  }

  public Shift fromEntity(ShiftEntity shiftEntity) {
    return new Shift()
        .id(shiftEntity.getId())
        .duration(shiftEntity.getDuration())
        .startMileage(shiftEntity.getStartMileage())
        .endMileage(shiftEntity.getEndMileage())
        .date(shiftEntity.getDate())
        .taxi(taxiMapper.fromEntity(shiftEntity.getTaxi()))
        .driver(driverMapper.fromEntity(shiftEntity.getDriver()));
  }
}
