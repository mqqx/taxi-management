package dev.hmmr.taxi.management.backend.spring.service;

import dev.hmmr.taxi.management.backend.spring.model.TaxiEntity;
import dev.hmmr.taxi.management.backend.spring.repository.TaxiRepository;
import dev.hmmr.taxi.management.openapi.model.Taxi;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TaxiService {
  TaxiRepository taxiRepository;

  public void add(Taxi taxi) {
    final TaxiEntity taxiEntity = toTaxiEntity(taxi);

    taxiRepository.save(taxiEntity);
  }

  private static TaxiEntity toTaxiEntity(Taxi taxi) {
    return new TaxiEntity()
        .setFin(taxi.getFin())
        .setMileage(taxi.getMileage())
        .setDescription(taxi.getDescription())
        .setNumberPlate(taxi.getNumberPlate())
        .setRegistrationDate(taxi.getRegistrationDate())
        .setConcessionNumber(taxi.getConcessionNumber())
        .setConcessionDate(taxi.getConcessionDate())
        .setActive(taxi.getActive());
  }

  public List<Taxi> findAll() {
    return taxiRepository.findAll().stream()
        .map(
            taxiEntity ->
                new Taxi()
                    .id(taxiEntity.getId())
                    .concessionDate(taxiEntity.getConcessionDate())
                    .concessionNumber(taxiEntity.getConcessionNumber())
                    .numberPlate(taxiEntity.getNumberPlate())
                    .registrationDate(taxiEntity.getRegistrationDate())
                    .description(taxiEntity.getDescription())
                    .mileage(taxiEntity.getMileage())
                    .active(taxiEntity.isActive())
                    .fin(taxiEntity.getFin()))
        .toList();
  }
}
