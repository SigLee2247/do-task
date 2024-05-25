package terra.backend.domain.cpu.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import terra.backend.domain.cpu.entity.CpuDailyUsage;

public interface DailyUsageRepository extends JpaRepository<CpuDailyUsage, Long> {

  @Query("select m from CpuDailyUsage m where m.samplingDate between :startDate and :endDate")
  List<CpuDailyUsage> findBetweenDate(
      @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
