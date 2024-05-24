package terra.backend.common.utils;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DateUtilsTest {

  @Test
  @DisplayName("hour정보를 제공했을 때 다른 정보를 제외한 hour 데이터가 내가 설정한 데이터가 맞는지 확인하는 테스트")
  void createLocalDateTimeWithCustomHourTest() {
    // given
    int hour = 10;
    // when
    LocalDateTime result = DateUtils.getCustomHour(hour);
    // then
    Assertions.assertThat(result.getHour()).isEqualTo(hour);
  }

  @Test
  @DisplayName("yyyy-MM-dd HH:mm:ss 패턴 데이터 파싱")
  void parseTest() {
    // given
    String date = "2024-05-24 20:00:00";
    // when
    LocalDateTime parse = DateUtils.parse(date);
    // then
    Assertions.assertThat(parse).isInstanceOf(LocalDateTime.class);
  }

  @Test
  @DisplayName("현재 날짜 기준 분 정보만 0으로 출력하는 메서드")
  void getLocalDateTimeMinuteZeroTest() {
    // given
    LocalDateTime now = LocalDateTime.now();
    // when
    LocalDateTime result = DateUtils.getLocalDateTimeMinuteZero(now);
    // then
    Assertions.assertThat(result.getMinute()).isZero();
  }
}
