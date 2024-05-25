package terra.backend.domain.cpu.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.IntSummaryStatistics;
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
  private String avgUsage;

  @Column(nullable = false)
  private int totalUsage;

  private int samplingCount;

  @Column(nullable = false)
  private LocalDateTime samplingDate;

  public CpuHourlyUsage(
      int maxUsage,
      int minUsage,
      double avgUsage,
      int totalUsage,
      int samplingCount,
      LocalDateTime samplingDate) {
    this.maxUsage = maxUsage;
    this.minUsage = minUsage;
    this.avgUsage = String.valueOf(Math.round(avgUsage));
    this.samplingCount = samplingCount;
    this.totalUsage = totalUsage;
    this.samplingDate = samplingDate;
  }

  public CpuHourlyUsage(IntSummaryStatistics summary, LocalDateTime samplingDate) {
    this.maxUsage = summary.getMax();
    this.minUsage = summary.getMin();
    this.avgUsage = String.valueOf(Math.round(summary.getAverage()));
    this.samplingCount = (int) summary.getCount();
    this.totalUsage = (int) summary.getSum();
    this.samplingDate = samplingDate;
  }
}
