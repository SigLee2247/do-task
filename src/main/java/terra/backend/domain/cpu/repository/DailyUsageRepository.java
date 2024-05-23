package terra.backend.domain.cpu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import terra.backend.domain.cpu.entity.CpuDailyUsage;

public interface DailyUsageRepository extends JpaRepository<CpuDailyUsage, Long> {}
