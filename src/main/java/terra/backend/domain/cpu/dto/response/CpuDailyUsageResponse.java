package terra.backend.domain.cpu.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import terra.backend.domain.cpu.entity.CpuDailyUsage;

@Getter
@AllArgsConstructor
public class CpuDailyUsageResponse implements CpuResponseDto {
  private List<CpuDailyUsageDto> cpuUsageList;

  @Getter
  @Builder
  public static class CpuDailyUsageDto {
    private int maxUsage;
    private int minUsage;
    private String aveUsage;

    @Schema(example = "2024-05-23 22:13:00")
    private String samplingDate;

    public static CpuDailyUsageDto of(CpuDailyUsage entity) {
      return CpuDailyUsageDto.builder()
          .maxUsage(entity.getMaxUsage())
          .minUsage(entity.getMinUsage())
          .aveUsage(entity.getAvgUsage())
          .samplingDate(
              LocalDateTime.of(entity.getSamplingDate(), LocalTime.MIN)
                  .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
          .build();
    }
  }
}
