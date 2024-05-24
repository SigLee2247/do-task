package terra.backend.common.utils;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DateUtilsTest {

  @Test
  @DisplayName("hour정보를 제공했을 때 다른 정보를 제외한 hour 데이터가 내가 설정한 데이터가 맞는지 확인하는 테스트")
  void createLocalDateTimeWithCustomHourTest() throws Exception {
    // given
    int hour = 10;
    // when
    LocalDateTime result = DateUtils.getCustomHour(hour);
    // then
    Assertions.assertThat(result.getHour()).isEqualTo(hour);

    LocalDateTime now = LocalDateTime.now();
    LocalDateTime localDateTime = LocalDateTime.now().minusDays(12);
    // 오늘이 후에 있는가? now 는 더 뒤의 수다
    System.out.println(now.isAfter(localDateTime));
    System.out.println("now = " + now);
    System.out.println("localDateTime = " + localDateTime);
  }
}
