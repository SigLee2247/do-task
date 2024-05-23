package terra.backend.domain.cpu.scheduler;

import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import terra.backend.domain.cpu.cache.CpuCache;
import terra.backend.domain.cpu.cache.dto.CpuUsage;
import terra.backend.domain.cpu.entity.CpuHourlyUsage;
import terra.backend.domain.cpu.entity.CpuMinuteUsage;
import terra.backend.domain.cpu.service.CpuService;

@Slf4j
@Component
@RequiredArgsConstructor
public class CpuSaveScheduler {
  private final CpuService service;
  private final CpuCache cache;

  @Scheduled(cron = "0 * * * * *")
  public void saveCpuUsagePerMinute() {
    OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

    // return 0 since we have no data, not -1 which indicates error
    double cpuLoad = osBean.getCpuLoad() * 100;

    // save Data in RDB
    CpuMinuteUsage entity = service.saveMinuteUsage((int) cpuLoad);

    // save Data in Cache
    cache.save(entity);
  }

  @Scheduled(cron = "30 59 23 * * *")
  public void saveDailyAndHourlyUsage() {
    List<CpuUsage> todayCacheUsage = cache.getCpuUsageList();

    // 시간별 CPU 사용량
    List<CpuHourlyUsage> cpuHourlyUsages = service.saveHourlyUsage(todayCacheUsage);

    // 하루 CPU 사용량
    service.saveDailyUsage(cpuHourlyUsages);

    // 캐쉬 초기화
    cache.clear();
  }
}
