package terra.backend.domain.cpu.repository;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import terra.backend.common.exception.exception.BusinessLogicException;
import terra.backend.common.utils.DateUtils;
import terra.backend.domain.cpu.entity.CpuHourlyUsage;
import terra.backend.domain.cpu.exception.CpuExceptionCode;

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
}
