package terra.backend.domain.dto.response;

import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import terra.backend.domain.cpu.entity.CpuHourlyUsage;

@Getter
@AllArgsConstructor
public class CpuHourUsageResponse implements CpuResponseDto {
  private List<CpuHourUsageDto> cpuUsageList;

  @Getter
  @Builder
  public static class CpuHourUsageDto {
    private int maxUsage;
    private int minUsage;
    private String aveUsage;
    private String samplingDate;

    public static CpuHourUsageDto of(CpuHourlyUsage entity) {
      return CpuHourUsageDto.builder()
          .maxUsage(entity.getMaxUsage())
          .minUsage(entity.getMinUsage())
          .aveUsage(entity.getAvgUsage())
          .samplingDate(
              entity.getSamplingDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
          .build();
    }
  }
}
