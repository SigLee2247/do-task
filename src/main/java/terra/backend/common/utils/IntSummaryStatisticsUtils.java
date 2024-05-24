package terra.backend.common.utils;

import java.util.IntSummaryStatistics;
import java.util.List;
import terra.backend.domain.cpu.entity.CpuHourlyUsage;

public abstract class IntSummaryStatisticsUtils {

  private IntSummaryStatisticsUtils() {}

  public static IntSummaryStatistics of(List<CpuHourlyUsage> list) {
    int max = Integer.MIN_VALUE;
    int min = Integer.MAX_VALUE;
    int totalUsage = 0;
    int totalCount = 0;

    for (CpuHourlyUsage usage : list) {
      max = Math.max(usage.getMaxUsage(), max);
      min = Math.min(usage.getMinUsage(), min);

      totalUsage += usage.getTotalUsage();
      totalCount += usage.getSamplingCount();
    }

    return new IntSummaryStatistics(totalCount, min, max, totalUsage);
  }
}
