package dev.hmmr.taxi.management.backend.spring.service;

import static dev.hmmr.taxi.management.backend.spring.dummy.ShiftDummy.shift;
import static dev.hmmr.taxi.management.backend.spring.dummy.ShiftDummy.shiftEntity;
import static dev.hmmr.taxi.management.backend.spring.dummy.ShiftDummy.shiftEntityWithId;
import static dev.hmmr.taxi.management.backend.spring.dummy.ShiftDummy.shiftWithId;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatRuntimeException;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.data.domain.PageRequest.ofSize;

import dev.hmmr.taxi.management.backend.spring.mapper.ShiftMapper;
import dev.hmmr.taxi.management.backend.spring.model.ShiftEntity;
import dev.hmmr.taxi.management.backend.spring.repository.ShiftRepository;
import dev.hmmr.taxi.management.openapi.model.Shift;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class ShiftServiceTest {
  @Mock ShiftRepository mockShiftRepository;
  @Mock ShiftMapper mockShiftMapper;

  ShiftService shiftServiceUnderTest;

  @BeforeEach
  void setUp() {
    shiftServiceUnderTest = new ShiftService(mockShiftRepository, mockShiftMapper);
  }

  @Test
  void testAdd() {
    // Setup
    when(mockShiftMapper.toEntity(shift())).thenReturn(shiftEntity());

    // Run the test
    shiftServiceUnderTest.add(shift());

    // Verify the results
    verify(mockShiftRepository).save(shiftEntity());
  }

  @Test
  void testFindById() {
    // Setup
    // Configure ShiftRepository.findById(...).
    when(mockShiftRepository.findById(1)).thenReturn(Optional.of(shiftEntityWithId()));

    // Configure ShiftMapper.fromEntity(...).
    when(mockShiftMapper.fromEntity(shiftEntityWithId())).thenReturn(shiftWithId());

    // Run the test
    final Shift result = shiftServiceUnderTest.findById(1);

    // Verify the results
    assertThat(result).isEqualTo(shiftWithId());
  }

  @Test
  void testFindAll() {
    // Setup
    // Configure ShiftRepository.findTop20ByOrderByIdDesc(...).
    when(mockShiftRepository.findByDateBetweenOrderByIdDesc(
            LocalDate.of(2000, Month.JANUARY, 1), LocalDate.now(), ofSize(50)))
        .thenReturn(List.of(shiftEntityWithId()));

    // Configure ShiftMapper.fromEntity(...).
    when(mockShiftMapper.fromEntity(shiftEntityWithId())).thenReturn(shiftWithId());

    // Run the test
    final List<Shift> result = shiftServiceUnderTest.findAllByPeriod(null, null);

    // Verify the results
    assertThat(result).isEqualTo(List.of(shiftWithId()));
  }

  @Test
  void testFindAllReturnsNoItems() {
    // Setup
    when(mockShiftRepository.findByDateBetweenOrderByIdDesc(
            LocalDate.of(2000, Month.JANUARY, 1), LocalDate.now(), ofSize(50)))
        .thenReturn(emptyList());

    // Run the test
    final List<Shift> result = shiftServiceUnderTest.findAllByPeriod(null, null);

    // Verify the results
    assertThat(result).isEmpty();
  }

  @Test
  void testUpdate() {
    // Configure ShiftRepository.findById(...).
    final ShiftEntity shiftEntity = shiftEntityWithId();
    when(mockShiftRepository.findById(shiftEntity.getId())).thenReturn(Optional.of(shiftEntity));

    // Run the test
    shiftServiceUnderTest.update(shiftEntity.getId(), shift());

    // Verify the results
    verify(mockShiftMapper).toEntity(shift(), shiftEntity);
  }

  @Test
  void testUpdateWithUnknownIdThrowsException() {
    // Run the test
    // Verify the results
    assertThatRuntimeException().isThrownBy(() -> shiftServiceUnderTest.update(-1, shift()));
  }
}
