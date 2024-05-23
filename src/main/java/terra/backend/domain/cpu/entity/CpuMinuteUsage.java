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
  }
}
