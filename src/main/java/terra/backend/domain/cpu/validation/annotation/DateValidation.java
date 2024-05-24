package terra.backend.domain.cpu.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import terra.backend.domain.cpu.validation.enums.DateValidType;
import terra.backend.domain.cpu.validation.validator.DateValidator;

@Documented
@Constraint(validatedBy = DateValidator.class)
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface DateValidation {
  String message() default "";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  DateValidType type() default DateValidType.MIN;
}
