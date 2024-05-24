package terra.backend.domain.cpu.service;

import java.time.temporal.Temporal;
import java.util.List;
import terra.backend.domain.cpu.cache.dto.CpuUsage;
import terra.backend.domain.cpu.dto.response.CpuResponseDto;
import terra.backend.domain.cpu.entity.CpuDailyUsage;
import terra.backend.domain.cpu.entity.CpuHourlyUsage;
import terra.backend.domain.cpu.entity.CpuMinuteUsage;
import terra.backend.domain.cpu.validation.enums.DateValidType;

public interface CpuService {
  CpuMinuteUsage saveMinuteUsage(int cpuLoad);

  List<CpuHourlyUsage> saveHourlyUsage(List<CpuUsage> list);

  CpuDailyUsage saveDailyUsage(List<CpuHourlyUsage> cpuHourlyUsages);

  CpuResponseDto findUsage(Temporal startDate, Temporal endDate, DateValidType dateValidType);
}
