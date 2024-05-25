package terra.backend.domain.cpu.controller;

import static terra.backend.domain.cpu.validation.enums.DateValidType.DAY;
import static terra.backend.domain.cpu.validation.enums.DateValidType.HOUR;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import terra.backend.common.dto.response.ResponseDto;
import terra.backend.domain.cpu.dto.response.CpuDailyUsageResponse;
import terra.backend.domain.cpu.dto.response.CpuHourUsageResponse;
import terra.backend.domain.cpu.dto.response.CpuMinuteUsageResponse;
import terra.backend.domain.cpu.validation.annotation.DateValidation;

@Validated
@RestController
@RequestMapping("/api/cpu-usages")
@Tag(name = "CPU_Usage API", description = "CPU의 분 / 시간/ 일 별 사용량 조회 API")
public interface CpuUsageController {
  @GetMapping("/minute")
  @Operation(
      summary = "분당 CPU 사용량 조회",
      description = "조회는 최대 1주일 전 데이터까지만 조회 가능 <br> `yyyy-MM-dd HH:mm:ss` 형식의 데이터 전달")
  @Parameter(name = "startDate", description = "조회 시작할 날짜 `yyyy-MM-dd HH:mm:ss`", required = true)
  @Parameter(name = "endDate", description = "조회 종료할 날짜 `yyyy-MM-dd HH:mm:ss`", required = true)
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "분 별 CPU 사용량 제공",
            useReturnTypeSchema = true),
      })
  public ResponseEntity<ResponseDto<CpuMinuteUsageResponse>> findUsageByMin(
      @RequestParam("startDate") @DateValidation LocalDateTime startDate,
      @RequestParam("endDate") @DateValidation LocalDateTime endDate);

  @Operation(
      summary = "시간당 CPU 사용량 조회",
      description = "조회는 최대 3개월 전 데이터까지만 조회 가능 <br> `yyyy-MM-dd HH:mm:ss` 형식의 데이터 전달")
  @Parameter(name = "startDate", description = "조회 시작할 날짜 `yyyy-MM-dd HH:mm:ss`", required = true)
  @Parameter(name = "endDate", description = "조회 종료할 날짜 `yyyy-MM-dd HH:mm:ss`", required = true)
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "시간 별 CPU 사용량 제공",
            useReturnTypeSchema = true)
      })
  @GetMapping("/hour")
  public ResponseEntity<ResponseDto<CpuHourUsageResponse>> findUsageByHour(
      @RequestParam("startDate") @DateValidation(type = HOUR) LocalDateTime startDate,
      @RequestParam("endDate") @DateValidation(type = HOUR) LocalDateTime endDate);

  @Operation(
      summary = "시간당 CPU 사용량 조회",
      description = "조회는 최대 1년 전 데이터까지만 조회 가능 <br> `yyyy-MM-dd` 형식의 데이터 전달")
  @Parameter(name = "startDate", description = "조회 시작할 날짜 `yyyy-MM-dd`", required = true)
  @Parameter(name = "endDate", description = "조회 종료할 날짜 `yyyy-MM-dd`", required = true)
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "일 별 CPU 사용량 제공",
            useReturnTypeSchema = true)
      })
  @GetMapping("/day")
  public ResponseEntity<ResponseDto<CpuDailyUsageResponse>> findUsageByDay(
      @RequestParam("startDate") @DateValidation(type = DAY) LocalDate startDate,
      @RequestParam("endDate") @DateValidation(type = DAY) LocalDate endDate);
}
