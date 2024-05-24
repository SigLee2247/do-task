package terra.backend.domain.cpu.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import terra.backend.domain.cpu.entity.CpuMinuteUsage;

public interface MinuteUsageRepository extends JpaRepository<CpuMinuteUsage, Long> {

  @Query("SELECT c FROM CpuMinuteUsage c WHERE c.samplingDate > :startDate ")
  List<CpuMinuteUsage> findTodayUsage(@Param("startDate") LocalDateTime startDate);
}
