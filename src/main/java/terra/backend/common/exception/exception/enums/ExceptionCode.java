package terra.backend.common.exception.exception.enums;

import org.springframework.http.HttpStatus;

public interface ExceptionCode {
  HttpStatus getHttpStatus();

  String getMessage();
}
