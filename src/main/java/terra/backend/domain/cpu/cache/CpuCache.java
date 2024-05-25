package terra.backend.domain.cpu.cache;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import terra.backend.domain.cpu.cache.dto.CpuUsage;
import terra.backend.domain.cpu.entity.CpuMinuteUsage;

public interface CpuCache {
  void save(CpuMinuteUsage entity);

  void clear();

  List<CpuUsage> getCpuUsageList();

  List<CpuUsage> findBetweenLocalDateTime(LocalDateTime startTime, LocalDateTime endTime);

  List<CpuUsage> findBetweenLocalDate(LocalDate startTime, LocalDate endTime);
}
