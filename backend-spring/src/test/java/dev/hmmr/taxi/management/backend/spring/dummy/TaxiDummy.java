package dev.hmmr.taxi.management.backend.spring.dummy;

import dev.hmmr.taxi.management.backend.spring.model.TaxiEntity;
import dev.hmmr.taxi.management.openapi.model.Taxi;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.test.util.ReflectionTestUtils;

@NoArgsConstructor(access = AccessLevel.NONE)
public class TaxiDummy {
  private static final String FIN = "WVWZZZ7NZCV004904";
  private static final String DESCRIPTION = "Volvo 9999";
  private static final String NUMBER_PLATE = "NEW-X 1800";
  private static final int MILEAGE = 157864;
  private static final int CONCESSION_NUMBER = 9005;
  private static final LocalDate REGISTRATION_DATE = LocalDate.of(2021, 1, 1);
  private static final LocalDate CONCESSION_DATE = LocalDate.of(2021, 2, 10);

  public static Taxi taxi() {
    return new Taxi()
        .concessionDate(CONCESSION_DATE)
        .concessionNumber(CONCESSION_NUMBER)
        .numberPlate(NUMBER_PLATE)
        .registrationDate(REGISTRATION_DATE)
        .description(DESCRIPTION)
        .mileage(MILEAGE)
        .active(true)
        .fin(FIN);
  }

  public static Taxi taxiWithId() {
    final Taxi taxi = taxi();
    taxi.setId(1);
    return taxi;
  }

  public static TaxiEntity taxiEntity() {
    return new TaxiEntity()
        .setFin(FIN)
        .setMileage(MILEAGE)
        .setDescription(DESCRIPTION)
        .setNumberPlate(NUMBER_PLATE)
        .setRegistrationDate(REGISTRATION_DATE)
        .setConcessionNumber(CONCESSION_NUMBER)
        .setConcessionDate(CONCESSION_DATE)
        .setActive(true);
  }

  public static TaxiEntity taxiEntityWithId() {
    final TaxiEntity taxiEntity = taxiEntity();
    ReflectionTestUtils.setField(taxiEntity, "id", 1);
    return taxiEntity;
  }
}
