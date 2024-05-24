package terra.backend.domain.cpu.cache;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import terra.backend.domain.cpu.cache.dto.CpuUsage;
import terra.backend.domain.cpu.entity.CpuMinuteUsage;

class CpuCacheImplTest {
  CpuCacheImpl cpuCache = new CpuCacheImpl();

  @BeforeEach
  void setUp() {
    for (int i = 1; i < 50; i++) {
      cpuCache.save(new CpuMinuteUsage(1234 * i % 100, LocalDateTime.now().minusMinutes(i)));
    }
  }

  @Test
  @DisplayName("getCpuUsageList 테스트")
  void getCpuUsageListTest() {
    // given
    // when
    List<CpuUsage> cpuUsageList = cpuCache.getCpuUsageList();
    // then
    Assertions.assertThat(cpuUsageList).isNotEmpty();
  }

  @Test
  @DisplayName("findBetweenLocalDateTime 테스트 : 조회 되지 않을 정보를 제공 했을 때")
  void findBetweenLocalDateTimeTest() {
    // given
    LocalDateTime end = LocalDateTime.now().minusYears(5);
    LocalDateTime start = LocalDateTime.now().minusYears(7);
    // when
    List<CpuUsage> cpuUsageList = cpuCache.findBetweenLocalDateTime(start, end);
    // then
    Assertions.assertThat(cpuUsageList).isEmpty();
  }

  @Test
  @DisplayName("findBetweenLocalDateTime 테스트 : 조회 되는 정보 제공 했을 때")
  void findBetweenLocalDateTimeFindDataTest() {
    // given
    LocalDateTime start = LocalDateTime.now().minusDays(1);
    LocalDateTime end = LocalDateTime.now();
    // when
    List<CpuUsage> cpuUsageList = cpuCache.findBetweenLocalDateTime(start, end);
    // then
    Assertions.assertThat(cpuUsageList).isNotEmpty();
  }

  @Test
  @DisplayName("findBetweenLocalDate 테스트 : 조회 되지 않을 정보를 제공 했을 때")
  void findBetweenLocalDateTest() {
    // given
    LocalDate start = LocalDate.now().minusYears(7);
    LocalDate end = LocalDate.now().minusYears(5);
    // when
    List<CpuUsage> cpuUsageList = cpuCache.findBetweenLocalDate(start, end);
    // then
    Assertions.assertThat(cpuUsageList).isEmpty();
  }

  @Test
  @DisplayName("findBetweenLocalDate 테스트 : 조회 되는 정보 제공 했을 때")
  void findBetweenLocalDateFindDataTest() {
    // given
    LocalDate start = LocalDate.now().minusDays(1);
    LocalDate end = LocalDate.now();
    // when
    List<CpuUsage> cpuUsageList = cpuCache.findBetweenLocalDate(start, end);
    // then
    Assertions.assertThat(cpuUsageList).isNotEmpty();
  }
}
