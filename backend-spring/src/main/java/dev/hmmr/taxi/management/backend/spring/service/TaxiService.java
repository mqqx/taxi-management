package dev.hmmr.taxi.management.backend.spring.service;

import dev.hmmr.taxi.management.backend.spring.mapper.TaxiMapper;
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
  TaxiMapper taxiMapper;

  public void add(Taxi taxi) {
    final TaxiEntity taxiEntity = toTaxiEntity(taxi);
    taxiRepository.save(taxiEntity);
  }

  private TaxiEntity toTaxiEntity(Taxi taxi) {
    return taxiMapper.toEntity(taxi);
  }

  public List<Taxi> findAll() {
    return taxiRepository.findAll().stream().map(taxiMapper::fromEntity).toList();
  }
}
