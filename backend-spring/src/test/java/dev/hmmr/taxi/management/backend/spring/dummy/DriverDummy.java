package dev.hmmr.taxi.management.backend.spring.dummy;

import dev.hmmr.taxi.management.backend.spring.model.DriverEntity;
import dev.hmmr.taxi.management.openapi.model.Driver;

public class DriverDummy {
  private static final String FIRST_NAME = "Test";
  private static final String LAST_NAME = "Driver";

  public static Driver driver() {
    return new Driver().firstName(FIRST_NAME).lastName(LAST_NAME).active(true);
  }

  public static DriverEntity driverEntity() {
    return new DriverEntity().setFirstName(FIRST_NAME).setLastName(LAST_NAME).setActive(true);
  }
}
