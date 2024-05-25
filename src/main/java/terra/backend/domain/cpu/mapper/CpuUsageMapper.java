package terra.backend.domain.cpu.mapper;

import static terra.backend.common.utils.DateUtils.getCustomHour;

import java.time.LocalDate;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import terra.backend.common.utils.IntSummaryStatisticsUtils;
import terra.backend.domain.cpu.cache.dto.CpuUsage;
import terra.backend.domain.cpu.entity.CpuDailyUsage;
import terra.backend.domain.cpu.entity.CpuHourlyUsage;

public class CpuUsageMapper {
  public <T, D> List<D> transToDto(List<T> findEntityList, Function<T, D> mapper) {
    return findEntityList.stream().map(mapper).toList();
  }

  public List<CpuHourlyUsage> transToCpuHourUsageList(List<CpuUsage> list) {
    var mapByHour = groupCpuUsageByHour(list);
    return transToCpuHourUsageList(mapByHour);
  }

  private List<CpuHourlyUsage> transToCpuHourUsageList(
      Map<Integer, IntSummaryStatistics> mapByHour) {
    return mapByHour.entrySet().stream()
        .map(entry -> new CpuHourlyUsage(entry.getValue(), getCustomHour(entry.getKey())))
        .toList();
  }

  private Map<Integer, IntSummaryStatistics> groupCpuUsageByHour(List<CpuUsage> list) {
    return list.stream()
        .collect(
            Collectors.groupingBy(
                CpuUsage::getHour, Collectors.summarizingInt(CpuUsage::getUsage)));
  }

  public CpuDailyUsage transToCpuDailyUsage(List<CpuHourlyUsage> list) {
    IntSummaryStatistics summary = IntSummaryStatisticsUtils.of(list);
    return new CpuDailyUsage(
        summary.getMax(), summary.getMin(), summary.getAverage(), LocalDate.now());
  }
}
