package dev.hmmr.taxi.management.backend.spring.dummy;

import dev.hmmr.taxi.management.backend.spring.model.DriverEntity;
import dev.hmmr.taxi.management.openapi.model.Driver;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.test.util.ReflectionTestUtils;

@NoArgsConstructor(access = AccessLevel.NONE)
public class DriverDummy {
  private static final String FIRST_NAME = "Test";
  private static final String LAST_NAME = "Driver";
  private static final String ADDRESS = "address";
  private static final LocalDate P_LICENCE_DATE = LocalDate.of(2025, 1, 1);
  private static final LocalDate BIRTHDATE = LocalDate.of(1986, 12, 12);
  static final int ID = 1;

  public static Driver driver() {
    return new Driver()
        .firstName(FIRST_NAME)
        .lastName(LAST_NAME)
        .pLicenceDate(P_LICENCE_DATE)
        .birthdate(BIRTHDATE)
        .address(ADDRESS)
        .active(true);
  }

  public static Driver driverWithId() {
    return driverWithId(ID);
  }

  public static Driver driverWithId(int id) {
    final Driver driver = driver();
    driver.setId(id);
    return driver;
  }

  public static DriverEntity driverEntity() {
    return new DriverEntity()
        .setLastName(LAST_NAME)
        .setFirstName(FIRST_NAME)
        .setPLicenceDate(P_LICENCE_DATE)
        .setBirthdate(BIRTHDATE)
        .setAddress(ADDRESS)
        .setActive(true);
  }

  public static DriverEntity driverEntityWithId() {
    final DriverEntity driverEntity = driverEntity();
    ReflectionTestUtils.setField(driverEntity, "id", ID);
    return driverEntity;
  }
}
