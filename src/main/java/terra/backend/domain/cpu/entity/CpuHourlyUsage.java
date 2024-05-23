package terra.backend.domain.cpu.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class CpuHourlyUsage {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, name = "cpu_hourly_usage_id")
  private Long id;

  @Column(nullable = false)
  private int maxUsage;

  @Column(nullable = false)
  private int minUsage;

  @Column(nullable = false)
  private int avgUsage;

  @Column(nullable = false)
  private LocalDateTime samplingDate;
}
