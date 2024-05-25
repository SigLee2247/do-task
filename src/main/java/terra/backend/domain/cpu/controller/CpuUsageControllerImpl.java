package terra.backend.domain.cpu.controller;

import static terra.backend.domain.cpu.validation.enums.DateValidType.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import terra.backend.common.dto.response.ResponseDto;
import terra.backend.domain.cpu.dto.response.CpuDailyUsageResponse;
import terra.backend.domain.cpu.dto.response.CpuHourUsageResponse;
import terra.backend.domain.cpu.dto.response.CpuMinuteUsageResponse;
import terra.backend.domain.cpu.dto.response.CpuResponseDto;
import terra.backend.domain.cpu.service.CpuService;

@Validated
@RestController
@RequestMapping("/api/cpu-usages")
@Tag(name = "CPU_Usage API", description = "CPU의 분 / 시간/ 일 별 사용량 조회 API")
@RequiredArgsConstructor
public class CpuUsageControllerImpl implements CpuUsageController {

  private final CpuService cpuService;

  @Override
  @GetMapping("/minute")
  public ResponseEntity<ResponseDto<CpuMinuteUsageResponse>> findUsageByMin(
      LocalDateTime startDate, LocalDateTime endDate) {
    CpuResponseDto result = cpuService.findUsage(startDate, endDate, MIN);
    return ResponseEntity.ok(new ResponseDto(result));
  }

  @Override
  @GetMapping("/hour")
  public ResponseEntity<ResponseDto<CpuHourUsageResponse>> findUsageByHour(
      LocalDateTime startDate, LocalDateTime endDate) {
    CpuResponseDto result = cpuService.findUsage(startDate, endDate, HOUR);
    return ResponseEntity.ok(new ResponseDto(result));
  }

  @Override
  @GetMapping("/day")
  public ResponseEntity<ResponseDto<CpuDailyUsageResponse>> findUsageByDay(
      LocalDate startDate, LocalDate endDate) {
    CpuResponseDto result = cpuService.findUsage(startDate, endDate, DAY);
    return ResponseEntity.ok(new ResponseDto(result));
  }
}
