package terra.backend.domain.cpu.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import terra.backend.common.exception.exception.BusinessLogicException;
import terra.backend.common.utils.DateUtils;
import terra.backend.domain.cpu.cache.CpuCache;
import terra.backend.domain.cpu.cache.dto.CpuUsage;
import terra.backend.domain.cpu.dto.response.CpuHourUsageResponse.CpuHourUsageDto;
import terra.backend.domain.cpu.entity.CpuDailyUsage;
import terra.backend.domain.cpu.entity.CpuHourlyUsage;
import terra.backend.domain.cpu.entity.CpuMinuteUsage;
import terra.backend.domain.cpu.exception.CpuExceptionCode;
import terra.backend.domain.cpu.mapper.CpuUsageMapper;
import terra.backend.domain.cpu.repository.DailyUsageRepository;
import terra.backend.domain.cpu.repository.HourlyUsageRepository;
import terra.backend.domain.cpu.repository.MinuteUsageRepository;
import terra.backend.domain.cpu.validation.enums.DateValidType;
import terra.backend.domain.cpu.dto.response.CpuDailyUsageResponse;
import terra.backend.domain.cpu.dto.response.CpuDailyUsageResponse.CpuDailyUsageDto;
import terra.backend.domain.cpu.dto.response.CpuHourUsageResponse;
import terra.backend.domain.cpu.dto.response.CpuMinuteUsageResponse;
import terra.backend.domain.cpu.dto.response.CpuMinuteUsageResponse.CpuMinuteUsageDto;
import terra.backend.domain.cpu.dto.response.CpuResponseDto;

@Service
@Transactional(readOnly = true)
public class CpuServiceImpl implements CpuService {
  private final DailyUsageRepository dailyUsageRepository;
  private final MinuteUsageRepository minuteUsageRepository;
  private final HourlyUsageRepository hourlyUsageRepository;
  private final CpuUsageMapper mapper;
  private final CpuCache cache;

  public CpuServiceImpl(
      DailyUsageRepository dailyUsageRepository,
      MinuteUsageRepository minuteUsageRepository,
      HourlyUsageRepository hourlyUsageRepository,
      CpuCache cpuCache) {
    this.dailyUsageRepository = dailyUsageRepository;
    this.minuteUsageRepository = minuteUsageRepository;
    this.hourlyUsageRepository = hourlyUsageRepository;
    this.cache = cpuCache;
    this.mapper = new CpuUsageMapper();
  }

  @Override
  @Transactional
  public CpuMinuteUsage saveMinuteUsage(int cpuLoad) {
    return minuteUsageRepository.save(new CpuMinuteUsage(cpuLoad));
  }

  @Override
  @Transactional
  public List<CpuHourlyUsage> saveHourlyUsage(List<CpuUsage> list) {
    List<CpuHourlyUsage> cpuHourlyUsages = mapper.transToCpuHourUsageList(list);
    hourlyUsageRepository.saveAll(cpuHourlyUsages);
    return cpuHourlyUsages;
  }

  @Override
  @Transactional
  public CpuDailyUsage saveDailyUsage(List<CpuHourlyUsage> list) {
    CpuDailyUsage cpuDailyUsage = mapper.transToCpuDailyUsage(list);
    return dailyUsageRepository.save(cpuDailyUsage);
  }

  @Override
  public CpuResponseDto findUsage(
      Temporal startDate, Temporal endDate, DateValidType dateValidType) {
    switch (dateValidType) {
      case HOUR:
        return findUsageByHour((LocalDateTime) startDate, (LocalDateTime) endDate);
      case MIN:
        return findUsageByMin((LocalDateTime) startDate, (LocalDateTime) endDate);
      case DAY:
        return findUsageByDay((LocalDate) startDate, (LocalDate) endDate);
      default:
        throw new BusinessLogicException(CpuExceptionCode.NOT_SUPPORT);
    }
  }

  private CpuMinuteUsageResponse findUsageByMin(LocalDateTime startDate, LocalDateTime endDate) {
    List<CpuMinuteUsage> findEntityList = minuteUsageRepository.findBetweenDate(startDate, endDate);
    return new CpuMinuteUsageResponse(mapper.transToDto(findEntityList, CpuMinuteUsageDto::of));
  }

  private CpuHourUsageResponse findUsageByHour(LocalDateTime startDate, LocalDateTime endDate) {
    startDate = DateUtils.getLocalDateTimeMinuteZero(startDate);
    endDate = DateUtils.getLocalDateTimeMinuteZero(endDate);

    List<CpuHourlyUsage> findEntityList = hourlyUsageRepository.findBetweenDate(startDate, endDate);

    List<CpuUsage> findCache = cache.findBetweenLocalDateTime(startDate, endDate);
    List<CpuHourlyUsage> cpuHourlyUsages = mapper.transToCpuHourUsageList(findCache);
    findEntityList.addAll(cpuHourlyUsages);
    return new CpuHourUsageResponse(mapper.transToDto(findEntityList, CpuHourUsageDto::of));
  }

  private CpuDailyUsageResponse findUsageByDay(LocalDate startDate, LocalDate endDate) {
    List<CpuDailyUsage> findEntityList = dailyUsageRepository.findBetweenDate(startDate, endDate);

    List<CpuUsage> findCache = cache.findBetweenLocalDate(startDate, endDate);

    if (!findCache.isEmpty()) {
      List<CpuHourlyUsage> cpuHourlyUsages = mapper.transToCpuHourUsageList(findCache);
      CpuDailyUsage cpuDailyUsage = mapper.transToCpuDailyUsage(cpuHourlyUsages);

      findEntityList.add(cpuDailyUsage);
    }
    return new CpuDailyUsageResponse(mapper.transToDto(findEntityList, CpuDailyUsageDto::of));
  }
}
