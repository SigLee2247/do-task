package terra.backend.domain.cpu.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import terra.backend.common.exception.exception.BusinessLogicException;
import terra.backend.common.utils.DateUtils;
import terra.backend.domain.cpu.entity.CpuHourlyUsage;
import terra.backend.domain.cpu.exception.CpuExceptionCode;

@ActiveProfiles(profiles = "total")
@DataJpaTest
class HourlyUsageRepositoryTest {
  @Autowired HourlyUsageRepository repository;

  @Test
  @DisplayName("saveAll() 메서드 테스트")
  void saveAllTest() {
    // given
    List<CpuHourlyUsage> requestEntityList =
        List.of(
            new CpuHourlyUsage(49, 10, 24.5, 100, 4, DateUtils.getCustomHour(1)),
            new CpuHourlyUsage(67, 50, 25.5, 200, 10, DateUtils.getCustomHour(2)),
            new CpuHourlyUsage(23, 20, 20.5, 200, 60, DateUtils.getCustomHour(3)),
            new CpuHourlyUsage(57, 30, 30.5, 300, 60, DateUtils.getCustomHour(4)));
    // when
    List<CpuHourlyUsage> result = repository.saveAll(requestEntityList);

    // then

    Assertions.assertThat(result)
        .hasSize(requestEntityList.size())
        .allSatisfy(
            entity -> {
              CpuHourlyUsage expect =
                  repository
                      .findById(entity.getId())
                      .orElseThrow(() -> new BusinessLogicException(CpuExceptionCode.NOT_FOUND));
              Assertions.assertThat(entity).isEqualTo(expect);
            });
  }

  @Test
  @DisplayName("findBetweenDate() 메서드 테스트 : 조회할 데이터가 존재할 때")
  void findBetweenDateTest() {
    // given
    // 저장된 18개의 데이터 중 8 개만 조회가 된다. ( 해당 시간에 맞는 데이터들)
    LocalDateTime startDate = LocalDateTime.now().minusHours(10);
    LocalDateTime endDate = LocalDateTime.now().minusHours(1);
    List<CpuHourlyUsage> result = repository.findBetweenDate(startDate, endDate);
    // then
    Assertions.assertThat(result).isNotEmpty().hasSize(9);

    for (LocalDateTime time : result.stream().map(m -> m.getSamplingDate()).toList()) {
      Assertions.assertThat(time).isAfterOrEqualTo(startDate).isBeforeOrEqualTo(endDate);
    }
  }

  @Test
  @DisplayName("findBetweenDate() 메서드 테스트 : 조회할 데이터가 없을때")
  void findBetweenDateSearchFailTest() {
    // given
    // 저장된 18개의 데이터 중 8 개만 조회가 된다. ( 해당 시간에 맞는 데이터들)
    LocalDateTime startDate = LocalDateTime.now().minusDays(2);
    LocalDateTime endDate = LocalDateTime.now().minusDays(1);
    List<CpuHourlyUsage> result = repository.findBetweenDate(startDate, endDate);
    // then
    Assertions.assertThat(result).isEmpty();
  }
}
