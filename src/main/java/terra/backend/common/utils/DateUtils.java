package terra.backend.common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class DateUtils {

  private DateUtils() {}

  public static LocalDateTime getCustomHour(int hour) {
    return LocalDateTime.of(LocalDate.now(), LocalTime.of(hour, 0, 0));
  }
}
