package dev.hmmr.taxi.management.backend.spring.service;

import dev.hmmr.taxi.management.backend.spring.exception.ResourceNotFoundException;
import dev.hmmr.taxi.management.backend.spring.mapper.TripMapper;
import dev.hmmr.taxi.management.backend.spring.repository.ShiftRepository;
import dev.hmmr.taxi.management.backend.spring.repository.TripRepository;
import dev.hmmr.taxi.management.openapi.model.Trip;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TripService {
  TripRepository tripRepository;
  TripMapper tripMapper;
  ShiftRepository shiftRepository;

  public void add(int shiftId, Trip trip) {
    shiftRepository
        .findById(shiftId)
        .ifPresentOrElse(
            shiftEntity -> tripRepository.save(tripMapper.toEntity(shiftId, trip)),
            () -> {
              throw new ResourceNotFoundException();
            });
  }

  public List<Trip> findByShiftId(int shiftId) {
    return tripRepository.findByShiftId(shiftId).stream().map(tripMapper::fromEntity).toList();
  }

  public void delete(Integer tripId) {
    tripRepository.deleteById(tripId);
  }
}
