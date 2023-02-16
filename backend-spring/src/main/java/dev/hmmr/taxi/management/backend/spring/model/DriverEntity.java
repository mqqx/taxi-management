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
@Accessors(chain = true)
@Table(name = "fahrer")
@FieldDefaults(level = AccessLevel.PRIVATE)
// As no lazy loaded fields are used its fine to use @Data for this JPA entity
@SuppressWarnings("com.haulmont.jpb.LombokDataInspection")
public class DriverEntity {
  @Id
  @Setter(AccessLevel.NONE)
  @Column(name = "fahrer_id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  int id;

  @Basic
  @Column(name = "Name", nullable = false, length = 30)
  String lastName;

  @Basic
  @Column(name = "Vorname", nullable = false, length = 30)
  String firstName;

  @Basic
  @Column(name = "P-Schein")
  LocalDate pLicenceDate;

  @Basic
  @Column(name = "Geburtstag")
  LocalDate birthdate;

  @Basic
  @Column(name = "Adresse", length = 200)
  String address;

  @Basic
  @Column(name = "active", nullable = false)
  boolean active;
}
