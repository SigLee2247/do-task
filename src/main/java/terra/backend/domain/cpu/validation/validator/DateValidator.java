package terra.backend.domain.cpu.validation.validator;

import static terra.backend.domain.cpu.validation.enums.DateValidType.*;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import terra.backend.domain.cpu.validation.annotation.DateValidation;
import terra.backend.domain.cpu.validation.enums.DateValidType;

public class DateValidator implements ConstraintValidator<DateValidation, Temporal> {
  private DateValidType type;

  @Override
  public void initialize(DateValidation constraintAnnotation) {
    this.type = constraintAnnotation.type();
  }

  @Override
  public boolean isValid(Temporal value, ConstraintValidatorContext context) {
    boolean isValid;
    if (type.equals(HOUR))
      isValid = LocalDateTime.now().minusMonths(3).isBefore((LocalDateTime) value);
    else if (type.equals(MIN))
      isValid = LocalDateTime.now().minusDays(7).isBefore((LocalDateTime) value);
    else isValid = LocalDate.now().minusYears(3).isBefore((LocalDate) value);

    if (!isValid) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(type.getMessage()).addConstraintViolation();
    }
    return isValid;
  }
}
