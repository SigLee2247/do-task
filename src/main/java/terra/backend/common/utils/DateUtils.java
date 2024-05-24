package terra.backend.common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public abstract class DateUtils {
  private DateUtils() {}

  public static LocalDateTime getCustomHour(int hour) {
    return LocalDateTime.of(LocalDate.now(), LocalTime.of(hour, 0, 0));
  }

  public static LocalDateTime parse(String pattern) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    return LocalDateTime.parse(pattern, formatter);
  }
}
