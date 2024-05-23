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
  }
}
