package terra.backend.domain.cpu.scheduler;

import java.util.Random;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;
import terra.backend.domain.cpu.cache.CpuCache;
import terra.backend.domain.cpu.entity.CpuMinuteUsage;

@ActiveProfiles(profiles = "total")
@SpringBootTest
@Transactional
class CpuSaveSchedulerTest {
  @Autowired CpuSaveScheduler scheduler;
  @Autowired CpuCache cache;

  @Test
  @DisplayName("스케줄링한 Task가 30초 이내에 실행이 되는지 검증")
  void saveDailyAndHourlyUsageTest() throws Exception {
    int count = 24 * 60;
    Random random = new Random();

    // 20에서 80 사이의 임의의 정수 값 생성

    for (int i = 1; i <= count; i++) {
      cache.save(new CpuMinuteUsage(random.nextInt(61) + 20));
    }

    StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    scheduler.saveDailyAndHourlyUsage();
    stopWatch.stop();

    double runningTime = stopWatch.getTotalTimeSeconds();
    System.out.println(stopWatch.prettyPrint());
    Assertions.assertThat(runningTime).isLessThan(30.0);
  }
}
