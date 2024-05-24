package terra.backend.domain.cpu.dto.response;

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