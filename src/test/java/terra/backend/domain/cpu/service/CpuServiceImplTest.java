package terra.backend.domain.cpu.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import terra.backend.common.utils.DateUtils;
import terra.backend.domain.cpu.cache.dto.CpuUsage;
import terra.backend.domain.cpu.entity.CpuDailyUsage;
import terra.backend.domain.cpu.entity.CpuHourlyUsage;
import terra.backend.domain.cpu.entity.CpuMinuteUsage;
import terra.backend.domain.cpu.repository.DailyUsageRepository;
import terra.backend.domain.cpu.repository.HourlyUsageRepository;
import terra.backend.domain.cpu.repository.MinuteUsageRepository;

@ExtendWith(MockitoExtension.class)
class CpuServiceImplTest {
  @Mock DailyUsageRepository dailyUsageRepository;
  @Mock MinuteUsageRepository minuteUsageRepository;
  @Mock HourlyUsageRepository hourlyUsageRepository;
  @InjectMocks CpuServiceImpl service;

  @Test
  @DisplayName("단위 분 당 CPU 사용량 저장 save() 테스트")
  void saveMinuteUsageTest() throws Exception {
    // given
    ArgumentCaptor<CpuMinuteUsage> returnRepositoryCaptor =
        ArgumentCaptor.forClass(CpuMinuteUsage.class);
    ArgumentCaptor<CpuMinuteUsage> sendRepositoryCaptor =
        ArgumentCaptor.forClass(CpuMinuteUsage.class);
    int cpuLoad = 10;
    CpuMinuteUsage savedEntity = new CpuMinuteUsage(cpuLoad);

    BDDMockito.given(minuteUsageRepository.save(sendRepositoryCaptor.capture()))
        .willReturn(savedEntity);
    // when
    service.saveMinuteUsage(cpuLoad);
    // then
    Mockito.verify(minuteUsageRepository, Mockito.times(1)).save(returnRepositoryCaptor.capture());

    Assertions.assertThat(returnRepositoryCaptor.getValue().getUsage())
        .isEqualTo(savedEntity.getUsage());
    Assertions.assertThat(returnRepositoryCaptor.getValue().getUsage())
        .isEqualTo(sendRepositoryCaptor.getValue().getUsage());
    Assertions.assertThat(sendRepositoryCaptor.getValue().getUsage())
        .isEqualTo(savedEntity.getUsage());
  }

  @Test
  @DisplayName("단위 시간 당 CPU 사용량 저장 saveHourly() 테스트")
  void saveHourlyUsageTest() throws Exception {
    List<CpuUsage> parameter =
        List.of(
            new CpuUsage(10, LocalDateTime.of(2024, 05, 23, 1, 1)),
            new CpuUsage(20, LocalDateTime.of(2024, 05, 23, 1, 2)),
            new CpuUsage(30, LocalDateTime.of(2024, 05, 23, 1, 3)),
            new CpuUsage(40, LocalDateTime.of(2024, 05, 23, 1, 4)));
    List<CpuHourlyUsage> mockSave =
        List.of(new CpuHourlyUsage(40, 10, 25.0, 100, 4, DateUtils.getCustomHour(1)));

    ArgumentCaptor<List<CpuHourlyUsage>> sendRepositoryCaptor = ArgumentCaptor.forClass(List.class);
    // given

    BDDMockito.given(hourlyUsageRepository.saveAll(sendRepositoryCaptor.capture()))
        .willReturn(mockSave);
    // when
    service.saveHourlyUsage(parameter);
    // then
    List<CpuHourlyUsage> result = sendRepositoryCaptor.getValue();

    Mockito.verify(hourlyUsageRepository, Mockito.times(1)).saveAll(Mockito.anyList());

    Assertions.assertThat(result).hasSameSizeAs(mockSave);
    Assertions.assertThat(result.get(0)).usingRecursiveComparison().isEqualTo(mockSave.get(0));
  }

  @Test
  @DisplayName("단위 시간 당 CPU 사용량 저장 saveHourly() 테스트")
  void saveDailyUsageTest() throws Exception {
    // given
    ArgumentCaptor<CpuDailyUsage> sendRepositoryCaptor =
        ArgumentCaptor.forClass(CpuDailyUsage.class);
    ArgumentCaptor<CpuDailyUsage> returnRepositoryCaptor =
        ArgumentCaptor.forClass(CpuDailyUsage.class);
    List<CpuHourlyUsage> parameter =
        List.of(
            new CpuHourlyUsage(40, 10, 25.0, 100, 4, DateUtils.getCustomHour(1)),
            new CpuHourlyUsage(50, 10, 25.0, 200, 10, DateUtils.getCustomHour(2)),
            new CpuHourlyUsage(60, 5, 25.0, 300, 20, DateUtils.getCustomHour(3)));

    int expectedMax = parameter.stream().mapToInt(CpuHourlyUsage::getMaxUsage).max().getAsInt();
    int expectedMin = parameter.stream().mapToInt(CpuHourlyUsage::getMinUsage).min().getAsInt();
    int expectedCount = parameter.stream().mapToInt(CpuHourlyUsage::getSamplingCount).sum();
    int expectedTotal = parameter.stream().mapToInt(CpuHourlyUsage::getTotalUsage).sum();
    double avg = ((double) expectedTotal / expectedCount);

    CpuDailyUsage saveMock = new CpuDailyUsage(expectedMax, expectedMin, avg, LocalDate.now());
    BDDMockito.given(dailyUsageRepository.save(sendRepositoryCaptor.capture()))
        .willReturn(saveMock);
    // when
    service.saveDailyUsage(parameter);
    // then
    CpuDailyUsage result = sendRepositoryCaptor.getValue();
    Mockito.verify(dailyUsageRepository, Mockito.times(1)).save(returnRepositoryCaptor.capture());
    Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(saveMock);
  }
}
