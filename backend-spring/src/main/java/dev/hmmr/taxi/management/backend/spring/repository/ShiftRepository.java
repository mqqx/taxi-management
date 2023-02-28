package dev.hmmr.taxi.management.backend.spring.repository;

import dev.hmmr.taxi.management.backend.spring.model.ShiftEntity;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.repository.ListCrudRepository;

public interface ShiftRepository extends ListCrudRepository<ShiftEntity, Integer> {
  List<ShiftEntity> findTop20ByDateBetweenOrderByIdDesc(LocalDate from, LocalDate to);
}
