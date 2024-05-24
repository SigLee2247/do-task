package terra.backend.domain.cpu.service;

import java.util.List;
import terra.backend.domain.cpu.cache.dto.CpuUsage;
import terra.backend.domain.cpu.entity.CpuDailyUsage;
import terra.backend.domain.cpu.entity.CpuHourlyUsage;
import terra.backend.domain.cpu.entity.CpuMinuteUsage;

public interface CpuService {
  CpuMinuteUsage saveMinuteUsage(int cpuLoad);

  List<CpuHourlyUsage> saveHourlyUsage(List<CpuUsage> list);

  CpuDailyUsage saveDailyUsage(List<CpuHourlyUsage> cpuHourlyUsages);
}
