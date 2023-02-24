package dev.hmmr.taxi.management.backend.spring.service;

import dev.hmmr.taxi.management.backend.spring.exception.ResourceNotFoundException;
import dev.hmmr.taxi.management.backend.spring.mapper.TaxiMapper;
import dev.hmmr.taxi.management.backend.spring.model.TaxiEntity;
import dev.hmmr.taxi.management.backend.spring.repository.TaxiRepository;
import dev.hmmr.taxi.management.openapi.model.Taxi;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TaxiService {
  TaxiRepository taxiRepository;
  TaxiMapper taxiMapper;

  public void add(Taxi taxi) {
    final TaxiEntity taxiEntity = taxiMapper.toEntity(taxi);
    taxiRepository.save(taxiEntity);
  }

  public List<Taxi> findAll() {
    return taxiRepository.findAll().stream().map(taxiMapper::fromEntity).toList();
  }

  @Transactional
  public void update(int id, Taxi taxi) {
    taxiRepository
        .findById(id)
        .ifPresentOrElse(
            taxiEntity -> taxiMapper.toEntity(taxi, taxiEntity),
            () -> {
              throw new ResourceNotFoundException();
            });
  }
}
