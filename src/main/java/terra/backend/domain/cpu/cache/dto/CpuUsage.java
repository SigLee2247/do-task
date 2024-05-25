package terra.backend.domain.cpu.cache.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CpuUsage {
  private int usage;
  private LocalDateTime samplingDate;

  public CpuUsage(int usage, LocalDateTime samplingDate) {
    this.usage = usage;
    this.samplingDate = samplingDate;
  }

  public int getHour() {
    return samplingDate.getHour();
  }
}
