package dev.hmmr.taxi.management.backend.spring.repository;

import dev.hmmr.taxi.management.backend.spring.model.CustomerEntity;
import dev.hmmr.taxi.management.backend.spring.model.CustomerProjection;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

public interface CustomerRepository extends ListCrudRepository<CustomerEntity, Integer> {
  @Query(
      value =
          """
              select new dev.hmmr.taxi.management.backend.spring.model.CustomerProjection(c.id, c.name, count(*))
              from TripEntity t
              inner join t.customer c
              inner join ShiftEntity s on s.id = t.shiftId
              where s.date between :from
              and :to
              and c.name not in (' ')
              group by c.id, c.name
              order by 3 desc""")
  List<CustomerProjection> findAllByFromAndToDate(LocalDate from, LocalDate to);
}
