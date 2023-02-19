package dev.hmmr.taxi.management.backend.spring.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Accessors(chain = true)
@Table(name = "schicht")
@FieldDefaults(level = AccessLevel.PRIVATE)
// As no lazy loaded fields are used its fine to use @Data for this JPA entity
@SuppressWarnings("com.haulmont.jpb.LombokDataInspection")
public class ShiftEntity {
  @Id
  @Setter(AccessLevel.NONE)
  @Column(name = "schicht_id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  int id;

  @Basic
  @Column(name = "Datum", nullable = false)
  LocalDate date;

  @Basic
  @Column(name = "fahrer_id", nullable = false)
  int driverId;

  @Basic
  @Column(name = "taxen_id", nullable = false)
  int taxiId;

  @Basic
  @Column(name = "Anfangs-KM", nullable = false)
  int startMileage;

  @Basic
  @Column(name = "End-KM", nullable = false)
  int endMileage;

  @Basic
  @Column(name = "Schichtdauer")
  Integer duration;

  @ManyToOne
  @JoinColumn(
      name = "taxen_id",
      referencedColumnName = "taxen_id",
      nullable = false,
      insertable = false,
      updatable = false)
  TaxiEntity taxi;

  @ManyToOne
  @JoinColumn(
      name = "fahrer_id",
      referencedColumnName = "fahrer_id",
      nullable = false,
      insertable = false,
      updatable = false)
  DriverEntity driver;
}
