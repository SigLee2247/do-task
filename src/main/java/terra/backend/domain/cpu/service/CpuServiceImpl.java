package terra.backend.domain.cpu.service;

import static terra.backend.common.utils.DateUtils.getCustomHour;
import static terra.backend.domain.dto.response.CpuHourUsageResponse.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import terra.backend.common.utils.DateUtils;
import terra.backend.common.utils.IntSummaryStatisticsUtils;
import terra.backend.domain.cpu.cache.dto.CpuUsage;
import terra.backend.domain.cpu.entity.CpuDailyUsage;
import terra.backend.domain.cpu.entity.CpuHourlyUsage;
import terra.backend.domain.cpu.entity.CpuMinuteUsage;
import terra.backend.domain.cpu.repository.DailyUsageRepository;
import terra.backend.domain.cpu.repository.HourlyUsageRepository;
import terra.backend.domain.cpu.repository.MinuteUsageRepository;
import terra.backend.domain.dto.response.CpuDailyUsageResponse;
import terra.backend.domain.dto.response.CpuDailyUsageResponse.CpuDailyUsageDto;
import terra.backend.domain.dto.response.CpuHourUsageResponse;
import terra.backend.domain.dto.response.CpuMinuteUsageResponse;
import terra.backend.domain.dto.response.CpuMinuteUsageResponse.CpuMinuteUsageDto;

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

  @Override
  public CpuMinuteUsageResponse findUsageByMin(LocalDateTime startDate, LocalDateTime endDate) {
    List<CpuMinuteUsage> findEntityList = minuteUsageRepository.findBetweenDate(startDate, endDate);
    return new CpuMinuteUsageResponse(transToDto(findEntityList, CpuMinuteUsageDto::of));
  }

  @Override
  public CpuHourUsageResponse findUsageByHour(LocalDateTime startDate, LocalDateTime endDate) {
    startDate = DateUtils.getLocalDateTimeMinuteZero(startDate);
    endDate = DateUtils.getLocalDateTimeMinuteZero(endDate);

    List<CpuHourlyUsage> findEntityList = hourlyUsageRepository.findBetweenDate(startDate, endDate);
    return new CpuHourUsageResponse(transToDto(findEntityList, CpuHourUsageDto::of));
  }

  @Override
  public CpuDailyUsageResponse findUsageByDay(LocalDate startDate, LocalDate endDate) {
    List<CpuDailyUsage> findEntityList = dailyUsageRepository.findBetweenDate(startDate, endDate);
    return new CpuDailyUsageResponse(transToDto(findEntityList, CpuDailyUsageDto::of));
  }

  private <T, D> List<D> transToDto(List<T> findEntityList, Function<T, D> mapper) {
    return findEntityList.stream().map(mapper).toList();
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
