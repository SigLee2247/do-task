package terra.backend.domain.cpu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import terra.backend.domain.cpu.entity.CpuHourlyUsage;

public interface HourlyUsageRepository extends JpaRepository<CpuHourlyUsage, Long> {}
