package terra.backend.domain.cpu.controller;

import static terra.backend.domain.cpu.validation.enums.DateValidType.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import terra.backend.common.dto.response.ResponseDto;
import terra.backend.domain.cpu.service.CpuService;
import terra.backend.domain.cpu.validation.annotation.DateValidation;
import terra.backend.domain.dto.response.CpuDailyUsageResponse;
import terra.backend.domain.dto.response.CpuHourUsageResponse;
import terra.backend.domain.dto.response.CpuMinuteUsageResponse;

@Validated
@RestController
@RequestMapping("/api/cpu-usages")
@RequiredArgsConstructor
public class CpuUsageController {

  private final CpuService cpuService;

  @GetMapping("/minute")
  public ResponseEntity findUsageByMin(
      @RequestParam("startDate") @DateValidation LocalDateTime startDate,
      @RequestParam("endDate") @DateValidation LocalDateTime endDate) {
    CpuMinuteUsageResponse result = cpuService.findUsageByMin(startDate, endDate);
    return ResponseEntity.ok(new ResponseDto(result));
  }

  @GetMapping("/hour")
  public ResponseEntity findUsageByHour(
      @RequestParam("startDate") @DateValidation(type = HOUR) LocalDateTime startDate,
      @RequestParam("endDate") @DateValidation(type = HOUR) LocalDateTime endDate) {
    CpuHourUsageResponse result = cpuService.findUsageByHour(startDate, endDate);
    return ResponseEntity.ok(new ResponseDto(result));
  }

  @GetMapping("/day")
  public ResponseEntity findUsageByDay(
      @RequestParam("startDate") @DateValidation(type = HOUR) LocalDate startDate,
      @RequestParam("endDate") @DateValidation(type = HOUR) LocalDate endDate) {
    CpuDailyUsageResponse result = cpuService.findUsageByDay(startDate, endDate);
    return ResponseEntity.ok(new ResponseDto(result));
  }
}
