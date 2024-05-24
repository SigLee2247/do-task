package terra.backend.domain.cpu.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import terra.backend.domain.cpu.entity.CpuHourlyUsage;

public interface HourlyUsageRepository extends JpaRepository<CpuHourlyUsage, Long> {
  @Query("select m from CpuHourlyUsage m where m.samplingDate between :startDate and :endDate")
  List<CpuHourlyUsage> findBetweenDate(
      @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
