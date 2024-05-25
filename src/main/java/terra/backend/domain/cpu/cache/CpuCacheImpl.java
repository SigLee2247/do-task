package terra.backend.domain.cpu.cache;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import terra.backend.domain.cpu.cache.dto.CpuUsage;
import terra.backend.domain.cpu.entity.CpuMinuteUsage;

@Component
public class CpuCacheImpl implements CpuCache {
  private static Map<LocalDateTime, Integer> map = new HashMap<>();

  public void save(CpuMinuteUsage entity) {
    map.put(entity.getSamplingDate(), entity.getUsage());
  }

  @Override
  public void clear() {
    map.clear();
  }

  @Override
  public List<CpuUsage> getCpuUsageList() {
    return map.entrySet().stream()
        .map(entry -> new CpuUsage(entry.getValue(), entry.getKey()))
        .toList();
  }

  public List<CpuUsage> findBetweenLocalDateTime(LocalDateTime startTime, LocalDateTime endTime) {
    return map.entrySet().stream()
        .filter(entry -> entry.getKey().isAfter(startTime) && entry.getKey().isBefore(endTime))
        .map(entry -> new CpuUsage(entry.getValue(), entry.getKey()))
        .toList();
  }

  @Override
  public List<CpuUsage> findBetweenLocalDate(LocalDate startTime, LocalDate endTime) {
    LocalDateTime start = LocalDateTime.of(startTime, LocalTime.of(0, 0));
    LocalDateTime end = LocalDateTime.of(endTime, LocalTime.MAX);

    return map.entrySet().stream()
        .filter(entry -> entry.getKey().isAfter(start) && entry.getKey().isBefore(end))
        .map(entry -> new CpuUsage(entry.getValue(), entry.getKey()))
        .toList();
  }
}
