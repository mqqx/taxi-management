package dev.hmmr.taxi.management.backend.spring.controller;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

import dev.hmmr.taxi.management.backend.spring.service.TaxiService;
import dev.hmmr.taxi.management.openapi.api.TaxisApi;
import dev.hmmr.taxi.management.openapi.model.Taxi;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TaxiController implements TaxisApi {
  TaxiService taxiService;

  @Override
  public ResponseEntity<Void> addTaxi(Taxi taxi) {
    taxiService.add(taxi);
    return noContent().build();
  }

  @Override
  public ResponseEntity<List<Taxi>> getTaxis() {
    return ok(taxiService.findAll());
  }
}
