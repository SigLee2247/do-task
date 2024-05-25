package terra.backend.domain.cpu.init;

import jakarta.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import terra.backend.domain.cpu.cache.CpuCache;
import terra.backend.domain.cpu.entity.CpuMinuteUsage;
import terra.backend.domain.cpu.repository.MinuteUsageRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitCache {
  private final MinuteUsageRepository repository;
  private final CpuCache cache;

  @PostConstruct
  public void uploadToCache() {
    LocalDateTime now = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0));

    List<CpuMinuteUsage> todayUsage = repository.findTodayUsage(now);
    todayUsage.forEach(cache::save);
  }
}
