package terra.backend.domain.cpu.service;

import static terra.backend.common.utils.DateUtils.getCustomHour;

import java.time.LocalDate;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import terra.backend.common.utils.IntSummaryStatisticsUtils;
import terra.backend.domain.cpu.cache.dto.CpuUsage;
import terra.backend.domain.cpu.entity.CpuDailyUsage;
import terra.backend.domain.cpu.entity.CpuHourlyUsage;
import terra.backend.domain.cpu.entity.CpuMinuteUsage;
import terra.backend.domain.cpu.repository.DailyUsageRepository;
import terra.backend.domain.cpu.repository.HourlyUsageRepository;
import terra.backend.domain.cpu.repository.MinuteUsageRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CpuServiceImpl implements CpuService {
  private final DailyUsageRepository dailyUsageRepository;
  private final MinuteUsageRepository minuteUsageRepository;
  private final HourlyUsageRepository hourlyUsageRepository;

  @Override
  @Transactional
  public CpuMinuteUsage saveMinuteUsage(int cpuLoad) {
    return minuteUsageRepository.save(new CpuMinuteUsage(cpuLoad));
  }

  @Override
  @Transactional
  public List<CpuHourlyUsage> saveHourlyUsage(List<CpuUsage> list) {
    Map<Integer, IntSummaryStatistics> mapByHour = groupCpuUsageByHour(list);
    List<CpuHourlyUsage> cpuHourlyUsages = transToCpuHourUsageList(mapByHour);
    hourlyUsageRepository.saveAll(cpuHourlyUsages);
    return cpuHourlyUsages;
  }

  @Override
  @Transactional
  public CpuDailyUsage saveDailyUsage(List<CpuHourlyUsage> list) {
    CpuDailyUsage cpuDailyUsage = transToCpuDailyUsage(list);
    return dailyUsageRepository.save(cpuDailyUsage);
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

  private CpuDailyUsage transToCpuDailyUsage(List<CpuHourlyUsage> list) {
    IntSummaryStatistics summary = IntSummaryStatisticsUtils.of(list);
    return new CpuDailyUsage(
        summary.getMax(), summary.getMin(), summary.getAverage(), LocalDate.now());
  }
}
