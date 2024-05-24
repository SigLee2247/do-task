package terra.backend.domain.cpu.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import terra.backend.common.exception.exception.enums.ExceptionCode;

@Getter
@AllArgsConstructor
public enum CpuExceptionCode implements ExceptionCode {
  NOT_FOUND(HttpStatus.NOT_FOUND, "사용량을 조회할 수 없습니다.");
  HttpStatus httpStatus;

  String message;
}
