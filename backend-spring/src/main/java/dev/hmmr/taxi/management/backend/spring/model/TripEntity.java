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
@Table(name = "fahrten")
@FieldDefaults(level = AccessLevel.PRIVATE)
// As no lazy loaded fields are used its fine to use @Data for this JPA entity
@SuppressWarnings("com.haulmont.jpb.LombokDataInspection")
public class TripEntity {
  @Id
  @Setter(AccessLevel.NONE)
  @Column(name = "fahrten_id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  int id;

  @Basic
  @Column(name = "start_id", nullable = false)
  int startId;

  @Basic
  @Column(name = "ziel_id", nullable = false)
  int destinationId;

  @Basic
  @Column(name = "kunde_id")
  Integer customerId;

  @Basic
  @Column(name = "Beschreibung", length = 200)
  String description;

  @Basic
  @Column(name = "Preis Bar", precision = 2)
  Double cash;

  @Basic
  @Column(name = "Preis Rechnung", precision = 2)
  Double bill;

  @Basic
  @Column(name = "MwSt", nullable = false)
  int tax;

  @Basic
  @Column(name = "Wartezeit", precision = 3)
  Double waitingTime;

  @Basic
  @Column(name = "Art", length = 64)
  String type;

  @Basic
  @Column(name = "schicht_id", nullable = false)
  int shiftId;

  @ManyToOne
  @JoinColumn(
      name = "start_id",
      referencedColumnName = "ort_id",
      nullable = false,
      insertable = false,
      updatable = false)
  LocationEntity start;

  @ManyToOne
  @JoinColumn(
      name = "ziel_id",
      referencedColumnName = "ort_id",
      nullable = false,
      insertable = false,
      updatable = false)
  LocationEntity destination;

  @ManyToOne
  @JoinColumn(
      name = "kunde_id",
      referencedColumnName = "kunden_id",
      insertable = false,
      updatable = false)
  CustomerEntity customer;
}
