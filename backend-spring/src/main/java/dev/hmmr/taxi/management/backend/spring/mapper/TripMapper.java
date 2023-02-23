package dev.hmmr.taxi.management.backend.spring.mapper;

import dev.hmmr.taxi.management.backend.spring.model.TripEntity;
import dev.hmmr.taxi.management.openapi.model.Trip;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TripMapper {
  LocationMapper locationMapper;
  CustomerMapper customerMapper;

  public TripEntity toEntity(int shiftId, Trip trip) {
    // TODO finish mapping
    return new TripEntity()
        .setShiftId(shiftId)
        .setStartId(trip.getStart().getId())
        .setDestinationId(trip.getDestination().getId())
        .setCustomerId(trip.getCustomer().getId())
        .setDescription(trip.getDescription())
        .setCash(trip.getCash().doubleValue())
        .setBill(trip.getBill().doubleValue())
        // TODO fix
        .setTax(-1)
        .setWaitingTime(trip.getWaitingTime().doubleValue())
    // TODO add type
    ;
  }

  public Trip fromEntity(TripEntity tripEntity) {
    return new Trip()
        .id(tripEntity.getId())
        .shiftId(tripEntity.getShiftId())
        .start(locationMapper.fromEntity(tripEntity.getStart()))
        .destination(locationMapper.fromEntity(tripEntity.getDestination()))
        .customer(customerMapper.fromEntity(tripEntity.getCustomer()))
        .description(tripEntity.getDescription())
        .cash(BigDecimal.valueOf(tripEntity.getCash()))
        .bill(BigDecimal.valueOf(tripEntity.getBill()))
        // TODO fix tax
        .tax(Trip.TaxEnum.SEVEN)
        .waitingTime(BigDecimal.valueOf(tripEntity.getWaitingTime()))
    // TODO add type
    ;
  }
}
