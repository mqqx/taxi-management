package dev.hmmr.taxi.management.backend.spring.mapper;

import dev.hmmr.taxi.management.backend.spring.model.TaxiEntity;
import dev.hmmr.taxi.management.openapi.model.Taxi;
import org.springframework.stereotype.Service;

@Service
public class TaxiMapper {
  public TaxiEntity toEntity(Taxi taxi) {
    return toEntity(taxi, new TaxiEntity());
  }

  public TaxiEntity toEntity(Taxi taxi, TaxiEntity taxiEntity) {
    return taxiEntity
        .setFin(taxi.getFin())
        .setMileage(taxi.getMileage())
        .setDescription(taxi.getDescription())
        .setNumberPlate(taxi.getNumberPlate())
        .setRegistrationDate(taxi.getRegistrationDate())
        .setConcessionNumber(taxi.getConcessionNumber())
        .setConcessionDate(taxi.getConcessionDate())
        .setActive(taxi.getActive());
  }

  public Taxi fromEntity(TaxiEntity taxiEntity) {
    return new Taxi()
        .id(taxiEntity.getId())
        .concessionDate(taxiEntity.getConcessionDate())
        .concessionNumber(taxiEntity.getConcessionNumber())
        .numberPlate(taxiEntity.getNumberPlate())
        .registrationDate(taxiEntity.getRegistrationDate())
        .description(taxiEntity.getDescription())
        .mileage(taxiEntity.getMileage())
        .active(taxiEntity.isActive())
        .fin(taxiEntity.getFin());
  }
}
