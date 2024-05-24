package terra.backend.common.utils;

import java.time.LocalDateTime;
import java.util.IntSummaryStatistics;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import terra.backend.domain.cpu.entity.CpuHourlyUsage;

class IntSummaryStatisticsUtilsTest {
  @Test
  @DisplayName("IntSummaryStatics 생성 메서드 테스트")
  void createIntSummaryStaticsByListTest() throws Exception {
    // given
    List<CpuHourlyUsage> cpuHourlyUsages =
        List.of(
            new CpuHourlyUsage(100, 10, 10.0, 1000, 10, LocalDateTime.now()),
            new CpuHourlyUsage(10, 5, 30.0, 400, 10, LocalDateTime.now()),
            new CpuHourlyUsage(50, 2, 20.0, 100, 4, LocalDateTime.now()));
    int maxExpect = cpuHourlyUsages.stream().mapToInt(CpuHourlyUsage::getMaxUsage).max().getAsInt();
    int minExpect = cpuHourlyUsages.stream().mapToInt(CpuHourlyUsage::getMinUsage).min().getAsInt();
    int count = cpuHourlyUsages.stream().mapToInt(CpuHourlyUsage::getSamplingCount).sum();
    int sum = cpuHourlyUsages.stream().mapToInt(CpuHourlyUsage::getTotalUsage).sum();
    String avgExpect = String.valueOf((double) sum / count);
    // when
    IntSummaryStatistics result = IntSummaryStatisticsUtils.of(cpuHourlyUsages);

    // then
    Assertions.assertThat(String.valueOf(result.getAverage())).isEqualTo(avgExpect);
    Assertions.assertThat(result.getMax()).isEqualTo(maxExpect);
    Assertions.assertThat(result.getMin()).isEqualTo(minExpect);
    Assertions.assertThat(result.getCount()).isEqualTo(count);
    Assertions.assertThat(result.getSum()).isEqualTo(sum);
  }
}
