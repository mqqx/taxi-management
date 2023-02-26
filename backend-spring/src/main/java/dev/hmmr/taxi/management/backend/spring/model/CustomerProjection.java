package dev.hmmr.taxi.management.backend.spring.model;

public record CustomerProjection(int id, String name, int count) {
  public CustomerProjection(int id, String name, long count) {
    // cast to int should always be sufficient as count will never reach Integer.MAX_VALUE
    this(id, name, (int) count);
  }
}
