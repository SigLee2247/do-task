package terra.backend.domain.cpu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import terra.backend.domain.cpu.entity.CpuMinuteUsage;

public interface MinuteUsageRepository extends JpaRepository<CpuMinuteUsage, Long> {}
