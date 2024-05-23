package terra.backend.domain.cpu.cache;

import java.util.List;
import terra.backend.domain.cpu.cache.dto.CpuUsage;
import terra.backend.domain.cpu.entity.CpuMinuteUsage;

public interface CpuCache {
  void save(CpuMinuteUsage entity);

  void clear();

  List<CpuUsage> getCpuUsageList();
}
