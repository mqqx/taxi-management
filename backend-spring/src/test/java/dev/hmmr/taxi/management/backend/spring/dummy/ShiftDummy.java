package dev.hmmr.taxi.management.backend.spring.dummy;

import static dev.hmmr.taxi.management.backend.spring.dummy.DriverDummy.driverEntityWithId;
import static dev.hmmr.taxi.management.backend.spring.dummy.DriverDummy.driverWithId;
import static dev.hmmr.taxi.management.backend.spring.dummy.TaxiDummy.taxiEntityWithId;
import static dev.hmmr.taxi.management.backend.spring.dummy.TaxiDummy.taxiWithId;

import dev.hmmr.taxi.management.backend.spring.model.ShiftEntity;
import dev.hmmr.taxi.management.openapi.model.Shift;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.test.util.ReflectionTestUtils;

@NoArgsConstructor(access = AccessLevel.NONE)
public class ShiftDummy {
  private static final LocalDate DATE = LocalDate.of(2023, 1, 1);
  private static final int START_MILEAGE = 153546;
  private static final int END_MILEAGE = 153891;
  private static final int DURATION = 8;
  private static final int ID = 1;

  public static Shift shift() {
    return shiftBase().taxi(taxiWithId()).driver(driverWithId());
  }

  public static Shift shift(int taxiId, int driverId) {
    return shiftBase().taxi(taxiWithId(taxiId)).driver(driverWithId(driverId));
  }

  private static Shift shiftBase() {
    return new Shift()
        .date(DATE)
        .startMileage(START_MILEAGE)
        .endMileage(END_MILEAGE)
        .duration(DURATION);
  }

  public static Shift shiftWithId() {
    return shift().id(ID);
  }

  public static ShiftEntity shiftEntity() {
    return new ShiftEntity()
        .setDate(DATE)
        .setStartMileage(START_MILEAGE)
        .setEndMileage(END_MILEAGE)
        .setDuration(DURATION)
        .setTaxiId(TaxiDummy.ID)
        .setDriverId(DriverDummy.ID);
  }

  public static ShiftEntity shiftEntityWithTaxiAndDriver() {
    return shiftEntityWithId().setTaxi(taxiEntityWithId()).setDriver(driverEntityWithId());
  }

  public static ShiftEntity shiftEntityWithId() {
    final ShiftEntity shiftEntity = shiftEntity();
    ReflectionTestUtils.setField(shiftEntity, "id", ID);
    return shiftEntity;
  }
}
