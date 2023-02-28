package dev.hmmr.taxi.management.backend.spring.controller;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

import dev.hmmr.taxi.management.backend.spring.service.ShiftService;
import dev.hmmr.taxi.management.backend.spring.service.TripService;
import dev.hmmr.taxi.management.openapi.api.ShiftsApi;
import dev.hmmr.taxi.management.openapi.model.Shift;
import dev.hmmr.taxi.management.openapi.model.Trip;
import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ShiftController implements ShiftsApi {
  ShiftService shiftService;
  TripService tripService;

  @Override
  public ResponseEntity<Void> addShift(Shift shift) {
    shiftService.add(shift);
    return noContent().build();
  }

  @Override
  public ResponseEntity<Void> addTrip(Integer shiftId, Trip trip) {
    tripService.add(shiftId, trip);
    return noContent().build();
  }

  @Override
  public ResponseEntity<Void> deleteTrip(Integer shiftId, Integer tripId) {
    tripService.delete(tripId);
    return noContent().build();
  }

  @Override
  public ResponseEntity<Shift> getShiftById(Integer shiftId) {
    return ok(shiftService.findById(shiftId));
  }

  @Override
  public ResponseEntity<List<Shift>> getShiftsByPeriod(LocalDate from, LocalDate to) {
    return ok(shiftService.findAllByPeriod(from, to));
  }

  @Override
  public ResponseEntity<List<Trip>> getTrips(Integer shiftId) {
    return ok(tripService.findByShiftId(shiftId));
  }

  @Override
  public ResponseEntity<Void> updateShift(Integer shiftId, Shift shift) {
    shiftService.update(shiftId, shift);
    return noContent().build();
  }
}
