package terra.backend.domain.cpu.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CpuMinuteUsage {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, name = "cpu_minute_usage_id")
  private Long id;

  @Column(nullable = false)
  private int usage;

  @Column(nullable = false)
  private LocalDateTime samplingDate;

  public CpuMinuteUsage(int cpuLoad) {
    this.usage = cpuLoad;
    this.samplingDate = LocalDateTime.now();
  }

  public CpuMinuteUsage(Long id, int usage) {
    this.id = id;
    this.usage = usage;
    this.samplingDate = LocalDateTime.now();
  }

  public CpuMinuteUsage(int usage, LocalDateTime samplingDate) {
    this.usage = usage;
    this.samplingDate = samplingDate;
  }
}
