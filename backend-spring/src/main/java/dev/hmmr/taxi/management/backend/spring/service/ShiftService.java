package dev.hmmr.taxi.management.backend.spring.service;

import static org.springframework.data.domain.PageRequest.ofSize;

import dev.hmmr.taxi.management.backend.spring.exception.ResourceNotFoundException;
import dev.hmmr.taxi.management.backend.spring.mapper.ShiftMapper;
import dev.hmmr.taxi.management.backend.spring.model.ShiftEntity;
import dev.hmmr.taxi.management.backend.spring.repository.ShiftRepository;
import dev.hmmr.taxi.management.openapi.model.Shift;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ShiftService {
  ShiftRepository shiftRepository;
  ShiftMapper shiftMapper;

  public void add(Shift shift) {
    final ShiftEntity shiftEntity = shiftMapper.toEntity(shift);
    shiftRepository.save(shiftEntity);
  }

  public Shift findById(int id) {
    return shiftRepository
        .findById(id)
        .map(shiftMapper::fromEntity)
        .orElseThrow(ResourceNotFoundException::new);
  }

  public List<Shift> findAllByPeriod(LocalDate from, LocalDate to) {
    if (from == null) {
      from = LocalDate.of(2000, Month.JANUARY, 1);
    }

    if (to == null) {
      to = LocalDate.now();
    }

    return shiftRepository.findByDateBetweenOrderByIdDesc(from, to, ofSize(50)).stream()
        .map(shiftMapper::fromEntity)
        .toList();
  }

  @Transactional
  public void update(int id, Shift shift) {
    shiftRepository
        .findById(id)
        .ifPresentOrElse(
            shiftEntity -> shiftMapper.toEntity(shift, shiftEntity),
            () -> {
              throw new ResourceNotFoundException();
            });
  }
}
