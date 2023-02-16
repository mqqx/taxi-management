package dev.hmmr.taxi.management.backend.spring.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "kunden")
@FieldDefaults(level = AccessLevel.PRIVATE)
// As no lazy loaded fields are used its fine to use @Data for this JPA entity
@SuppressWarnings("com.haulmont.jpb.LombokDataInspection")
public class CustomerEntity {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "kunden_id", nullable = false)
  int id;

  @Basic
  @Column(name = "Beschreibung", nullable = false, length = 200)
  String name;
}
