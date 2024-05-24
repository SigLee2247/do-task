package terra.backend.domain.cpu.controller;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static terra.backend.domain.dto.response.CpuMinuteUsageResponse.*;
import static terra.backend.utils.ApiTestUtil.getRequestSpecification;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import terra.backend.common.exception.exception.response.ErrorResponse;
import terra.backend.common.utils.DateUtils;
import terra.backend.domain.cpu.validation.enums.DateValidType;
import terra.backend.domain.dto.response.CpuMinuteUsageResponse;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CpuUsageControllerTest {
  @LocalServerPort int port;

  @BeforeEach
  void setUp() {
    RestAssured.port = port;
  }

  @DisplayName("분당 CPU 사용량 조회")
  @Test
  void findUsageByMinTest() {

    LocalDateTime endDate = LocalDateTime.now();
    LocalDateTime startDate = endDate.minusMinutes(10);
    Map<String, Object> params =
        Map.of(
            "startDate", startDate.toString(),
            "endDate", endDate.toString());

    ExtractableResponse<Response> response =
        getRequestSpecification()
            .params(params)
            .when()
            .get("/api/cpu-usages/minute")
            .then()
            .extract();

    JsonPath result = response.jsonPath();
    assertAll(
        () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
        () ->
            assertThat(result.getList("data.cpuUsageList", CpuMinuteUsageDto.class))
                .isNotEmpty(),
        () -> {
          List<CpuMinuteUsageDto> cpuUsageList =
              result.getList(
                  "data.cpuUsageByMinute", CpuMinuteUsageResponse.CpuMinuteUsageDto.class);

          // startDate와 endDate 사이에 있는지 확인
          for (CpuMinuteUsageDto expect : cpuUsageList) {
            LocalDateTime parse = DateUtils.parse(expect.getSamplingDate());
            Assertions.assertThat(parse).isAfterOrEqualTo(startDate).isBeforeOrEqualTo(endDate);
          }
        });
  }

  @DisplayName("분당 CPU 사용량 조회 : 실패")
  @Test
  void findUsageByMinFailTest() {

    LocalDateTime endDate = LocalDateTime.now().minusDays(10);
    LocalDateTime startDate = endDate.minusMinutes(10);
    Map<String, Object> params =
        Map.of(
            "startDate", startDate.toString(),
            "endDate", endDate.toString());

    ExtractableResponse<Response> response =
        getRequestSpecification()
            .params(params)
            .when()
            .get("/api/cpu-usages/minute")
            .then()
            .extract();

    JsonPath result = response.jsonPath();
    assertAll(
        () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
        () ->
            assertThat(
                    result.getList("violationErrors", ErrorResponse.ConstraintViolationError.class))
                .isNotEmpty(),
        () ->
            assertThat(
                    result
                        .getList("violationErrors", ErrorResponse.ConstraintViolationError.class)
                        .get(0)
                        .getMessage())
                .isEqualTo(DateValidType.MIN.getMessage()));
  }
}
