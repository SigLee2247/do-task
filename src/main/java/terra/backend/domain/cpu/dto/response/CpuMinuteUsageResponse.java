package terra.backend.domain.cpu.dto.response;

import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import terra.backend.domain.cpu.entity.CpuMinuteUsage;

@Getter
@AllArgsConstructor
public class CpuMinuteUsageResponse implements CpuResponseDto {

  private List<CpuMinuteUsageDto> cpuUsageList;

  @Getter
  @Builder
  public static class CpuMinuteUsageDto {
    private int usage;
    private String samplingDate;

    public static CpuMinuteUsageDto of(CpuMinuteUsage entity) {
      return CpuMinuteUsageDto.builder()
          .usage(entity.getUsage())
          .samplingDate(
              entity.getSamplingDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
          .build();
    }
  }
}
