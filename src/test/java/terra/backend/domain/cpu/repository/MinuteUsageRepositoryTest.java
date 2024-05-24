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
import terra.backend.domain.cpu.entity.CpuMinuteUsage;

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
  void findTodayUsageTest() throws Exception {
    // given
    List<CpuMinuteUsage> saveEntity =
        List.of(
            new CpuMinuteUsage(20),
            new CpuMinuteUsage(30),
            new CpuMinuteUsage(22),
            new CpuMinuteUsage(23),
            new CpuMinuteUsage(42),
            new CpuMinuteUsage(22));
    // when
    repository.saveAll(saveEntity);

    List<CpuMinuteUsage> result =
        repository.findTodayUsage(LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0)));
    // then
    Assertions.assertThat(result)
        .hasSize(saveEntity.size())
        .extracting(CpuMinuteUsage::getUsage)
        .containsExactlyInAnyOrderElementsOf(
            saveEntity.stream().map(CpuMinuteUsage::getUsage).toList());
  }

  @Test
  @DisplayName("findTodayUsage() 메서드 테스트 : 오늘 저장된 데이터가 존재 하지 않을 때")
  void findTodayUsageFindFailTest() throws Exception {
    // given
    List<CpuMinuteUsage> saveEntity =
        List.of(
            new CpuMinuteUsage(20, LocalDateTime.of(2022, 12, 3, 12, 1)),
            new CpuMinuteUsage(30, LocalDateTime.of(2022, 12, 3, 12, 2)),
            new CpuMinuteUsage(22, LocalDateTime.of(2022, 12, 3, 12, 3)),
            new CpuMinuteUsage(23, LocalDateTime.of(2022, 12, 3, 12, 4)),
            new CpuMinuteUsage(42, LocalDateTime.of(2022, 12, 3, 12, 5)),
            new CpuMinuteUsage(22, LocalDateTime.of(2022, 12, 3, 12, 6)));
    // when
    repository.saveAll(saveEntity);

    List<CpuMinuteUsage> result =
        repository.findTodayUsage(LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0)));
    // then
    Assertions.assertThat(result).isEmpty();
  }
}
