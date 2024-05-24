package terra.backend.domain.cpu.repository;

import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import terra.backend.common.exception.exception.BusinessLogicException;
import terra.backend.domain.cpu.entity.CpuDailyUsage;
import terra.backend.domain.cpu.exception.CpuExceptionCode;

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
}
