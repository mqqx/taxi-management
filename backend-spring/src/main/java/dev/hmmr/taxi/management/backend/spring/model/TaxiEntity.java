package dev.hmmr.taxi.management.backend.spring.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@NoArgsConstructor
@Table(name = "taxen")
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
// As no lazy loaded fields are used its fine to use @Data for this JPA entity
@SuppressWarnings("com.haulmont.jpb.LombokDataInspection")
public class TaxiEntity {
  @Id
  @Setter(AccessLevel.NONE)
  @Column(name = "taxen_id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  int id;

  @Basic
  @Column(name = "Bezeichnung", nullable = false, length = 20)
  String description;

  @Basic
  @Column(name = "KM-Stand", nullable = false)
  int mileage;

  @Basic
  @Column(name = "Kennzeichen", length = 12)
  String numberPlate;

  @Basic
  @Column(name = "Zulassungsdatum")
  LocalDate registrationDate;

  @Basic
  @Column(name = "FIN", length = 30)
  String fin;

  @Basic
  @Column(name = "Ordnungsnummer")
  Integer concessionNumber;

  @Basic
  @Column(name = "Konzessionsdatum")
  LocalDate concessionDate;

  @Basic
  @Column(name = "active", nullable = false)
  boolean active;
}
