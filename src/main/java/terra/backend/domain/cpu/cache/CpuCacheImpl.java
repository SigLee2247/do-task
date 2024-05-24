package terra.backend.domain.cpu.cache;

import java.time.LocalDateTime;
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
}
