package dev.hmmr.taxi.management.backend.spring.repository;

import dev.hmmr.taxi.management.backend.spring.model.DriverEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<DriverEntity, Integer> {

  List<DriverEntity> findAllByOrderByActiveDescLastName();
}
