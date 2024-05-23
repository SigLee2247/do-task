package terra.backend.common.exception.exception;

import lombok.Getter;
import terra.backend.common.exception.exception.enums.ExceptionCode;

@Getter
public class BusinessLogicException extends RuntimeException {

  private final ExceptionCode exceptionCode;

  public BusinessLogicException(ExceptionCode exceptionCode) {
    super(exceptionCode.getMessage());
    this.exceptionCode = exceptionCode;
  }
}
