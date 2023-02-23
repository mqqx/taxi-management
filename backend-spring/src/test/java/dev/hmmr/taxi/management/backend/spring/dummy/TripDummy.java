package dev.hmmr.taxi.management.backend.spring.dummy;

import static dev.hmmr.taxi.management.backend.spring.dummy.CustomerDummy.customerEntityWithId;
import static dev.hmmr.taxi.management.backend.spring.dummy.CustomerDummy.customerWithId;
import static dev.hmmr.taxi.management.backend.spring.dummy.LocationDummy.destinationEntityWithId;
import static dev.hmmr.taxi.management.backend.spring.dummy.LocationDummy.destinationWithId;
import static dev.hmmr.taxi.management.backend.spring.dummy.LocationDummy.startEntityWithId;
import static dev.hmmr.taxi.management.backend.spring.dummy.LocationDummy.startWithId;
import static dev.hmmr.taxi.management.openapi.model.Trip.TaxEnum.SEVEN;

import dev.hmmr.taxi.management.backend.spring.model.TripEntity;
import dev.hmmr.taxi.management.openapi.model.Trip;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.test.util.ReflectionTestUtils;

@NoArgsConstructor(access = AccessLevel.NONE)
public class TripDummy {
  private static final String DESCRIPTION = "fahrt";
  private static final double BILL = 22.75;
  private static final double CASH = 17.25;
  private static final double WAITING_TIME = 0.125;
  public static final int ID = 1;

  public static Trip trip() {
    return new Trip()
        .shiftId(ShiftDummy.ID)
        .start(startWithId())
        .destination(destinationWithId())
        .tax(SEVEN);
  }

  public static Trip tripWithId() {
    return trip().id(ID);
  }

  public static Trip tripWithAllFields() {
    return tripWithId()
        .customer(customerWithId())
        .bill(BigDecimal.valueOf(BILL))
        .cash(BigDecimal.valueOf(CASH))
        .waitingTime(BigDecimal.valueOf(WAITING_TIME))
        .description(DESCRIPTION);
  }

  public static Trip tripWithAllFieldsBesidesId() {
    return trip()
        .customer(customerWithId())
        .bill(BigDecimal.valueOf(BILL))
        .cash(BigDecimal.valueOf(CASH))
        .waitingTime(BigDecimal.valueOf(WAITING_TIME))
        .description(DESCRIPTION);
  }

  public static TripEntity tripEntity() {
    return new TripEntity()
        .setShiftId(ShiftDummy.ID)
        .setStartId(LocationDummy.START_ID)
        .setDestinationId(LocationDummy.DESTINATION_ID)
        .setTax(-1);
  }

  public static TripEntity tripEntityWithId() {
    final TripEntity tripEntity = tripEntity();
    ReflectionTestUtils.setField(tripEntity, "id", ID);
    return tripEntity;
  }

  public static TripEntity tripEntityWithAllFields() {
    return setOptionalFields(true);
  }

  public static TripEntity tripEntityWithAllFieldsAndChilds() {
    return setOptionalFields(true)
        .setStart(startEntityWithId())
        .setDestination(destinationEntityWithId())
        .setCustomer(customerEntityWithId());
  }

  public static TripEntity tripEntityWithAllFieldsBesidesId() {
    return setOptionalFields(false);
  }

  private static TripEntity setOptionalFields(boolean shouldSetId) {
    return (shouldSetId ? tripEntityWithId() : tripEntity())
        .setCustomerId(CustomerDummy.ID)
        .setBill(BILL)
        .setCash(CASH)
        .setWaitingTime(WAITING_TIME)
        .setDescription(DESCRIPTION);
  }
}
