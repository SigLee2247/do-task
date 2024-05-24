package terra.backend.domain.cpu.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import terra.backend.domain.cpu.entity.CpuMinuteUsage;

@ActiveProfiles(profiles = "total")
@DataJpaTest
class MinuteUsageRepositoryTest {
  @Autowired MinuteUsageRepository repository;

  @Test
  @DisplayName("save() 메서드 테스트")
  void saveTest() {
    // given
    CpuMinuteUsage requestEntity = new CpuMinuteUsage(20);
    // when
    CpuMinuteUsage result = repository.save(requestEntity);

    // then
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result)
        .extracting(
            CpuMinuteUsage::getId, CpuMinuteUsage::getUsage, CpuMinuteUsage::getSamplingDate)
        .containsExactly(result.getId(), requestEntity.getUsage(), requestEntity.getSamplingDate());
  }

  @Test
  @DisplayName("findTodayUsage() 메서드 테스트")
  void findTodayUsageTest() {
    LocalDateTime afterDate = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0));
    List<CpuMinuteUsage> result = repository.findTodayUsage(afterDate);
    // then
    Assertions.assertThat(result).isNotEmpty().hasSize(18);

    for (LocalDateTime time : result.stream().map(m -> m.getSamplingDate()).toList()) {
      Assertions.assertThat(time).isAfterOrEqualTo(afterDate);
    }
  }

  @Test
  @DisplayName("findTodayUsage() 메서드 테스트 : 오늘 저장된 데이터가 존재 하지 않을 때")
  void findTodayUsageFindFailTest() {
    // given

    List<CpuMinuteUsage> result =
        repository.findTodayUsage(
            LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(0, 0)));
    // then
    Assertions.assertThat(result).isEmpty();
  }

  @Test
  @DisplayName("findBetweenDate() 메서드 테스트 : 조회할 데이터가 존재할 때")
  void findBetweenDateTest() {
    // given
    // 저장된 18개의 데이터 중 8 개만 조회가 된다. ( 해당 시간에 맞는 데이터들)
    LocalDateTime startDate = LocalDateTime.now().minusMinutes(30);
    LocalDateTime endDate = LocalDateTime.now().minusMinutes(10);
    List<CpuMinuteUsage> result = repository.findBetweenDate(startDate, endDate);
    // then
    Assertions.assertThat(result).isNotEmpty().hasSize(8);

    for (LocalDateTime time : result.stream().map(m -> m.getSamplingDate()).toList()) {
      Assertions.assertThat(time).isAfterOrEqualTo(startDate).isBeforeOrEqualTo(endDate);
    }
  }

  @Test
  @DisplayName("findBetweenDate() 메서드 테스트 : 조회할 데이터가 없을때")
  void findBetweenDateSearchFailTest() {
    // given
    // 저장된 18개의 데이터 중 8 개만 조회가 된다. ( 해당 시간에 맞는 데이터들)
    LocalDateTime startDate = LocalDateTime.now().minusMinutes(50);
    LocalDateTime endDate = LocalDateTime.now().minusMinutes(40);
    List<CpuMinuteUsage> result = repository.findBetweenDate(startDate, endDate);
    // then
    Assertions.assertThat(result).isEmpty();
  }
}
