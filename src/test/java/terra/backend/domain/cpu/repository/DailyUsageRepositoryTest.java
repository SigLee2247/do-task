package terra.backend.domain.cpu.repository;

import java.time.LocalDate;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import terra.backend.common.exception.exception.BusinessLogicException;
import terra.backend.domain.cpu.entity.CpuDailyUsage;
import terra.backend.domain.cpu.exception.CpuExceptionCode;

@ActiveProfiles(profiles = "total")
@DataJpaTest
class DailyUsageRepositoryTest {
  @Autowired DailyUsageRepository repository;

  @Test
  @DisplayName("save() 테스트")
  void saveTest() throws Exception {
    // given
    CpuDailyUsage requestEntity = new CpuDailyUsage(70, 20, 25.5, LocalDate.now());
    // when
    CpuDailyUsage result = repository.save(requestEntity);
    // then
    CpuDailyUsage expect =
        repository
            .findById(result.getId())
            .orElseThrow(() -> new BusinessLogicException(CpuExceptionCode.NOT_FOUND));

    Assertions.assertThat(result.getId()).isNotNull();
    Assertions.assertThat(result).isEqualTo(expect);
    Assertions.assertThat(result.getMaxUsage())
        .isEqualTo(expect.getMaxUsage())
        .isEqualTo(requestEntity.getMaxUsage());
    Assertions.assertThat(result.getMinUsage())
        .isEqualTo(expect.getMinUsage())
        .isEqualTo(requestEntity.getMinUsage());
    Assertions.assertThat(result.getAvgUsage())
        .isEqualTo(expect.getAvgUsage())
        .isEqualTo(requestEntity.getAvgUsage());
  }

  @Test
  @DisplayName("findBetweenDate() 메서드 테스트 : 조회할 데이터가 존재할 때")
  void findBetweenDateTest() {
    // given
    LocalDate startDate = LocalDate.now().minusDays(10);
    LocalDate endDate = LocalDate.now().minusDays(1);
    List<CpuDailyUsage> result = repository.findBetweenDate(startDate, endDate);
    // then
    Assertions.assertThat(result).isNotEmpty().hasSize(10);

    for (LocalDate time : result.stream().map(m -> m.getSamplingDate()).toList()) {
      Assertions.assertThat(time).isAfterOrEqualTo(startDate).isBeforeOrEqualTo(endDate);
    }
  }

  @Test
  @DisplayName("findBetweenDate() 메서드 테스트 : 조회할 데이터가 없을때")
  void findBetweenDateSearchFailTest() {
    // given
    LocalDate startDate = LocalDate.now().minusDays(30);
    LocalDate endDate = LocalDate.now().minusDays(20);
    List<CpuDailyUsage> result = repository.findBetweenDate(startDate, endDate);
    // then
    Assertions.assertThat(result).isEmpty();
  }
}
