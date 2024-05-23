package terra.backend.domain.cpu.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class CpuDailyUsage {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, name = "cpu_daily_usage_id")
  private Long id;

  @Column(nullable = false)
  private int maxUsage;

  @Column(nullable = false)
  private int minUsage;

  @Column(nullable = false)
  private String avgUsage;

  @Column(nullable = false)
  private LocalDate samplingDate;

  public CpuDailyUsage(int maxUsage, int minUsage, double avgUsage, LocalDate samplingDate) {
    this.maxUsage = maxUsage;
    this.minUsage = minUsage;
    this.avgUsage = String.valueOf(avgUsage);
    this.samplingDate = samplingDate;
  }
}
