package dev.hmmr.taxi.management.backend.spring.repository;

import dev.hmmr.taxi.management.backend.spring.model.TripEntity;
import java.util.List;
import org.springframework.data.repository.ListCrudRepository;

public interface TripRepository extends ListCrudRepository<TripEntity, Integer> {
  List<TripEntity> findAllByShiftId(int shiftId);
}
