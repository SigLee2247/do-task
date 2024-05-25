package terra.backend.domain.cpu.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(example = "2024-05-23 22:13:00")
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
