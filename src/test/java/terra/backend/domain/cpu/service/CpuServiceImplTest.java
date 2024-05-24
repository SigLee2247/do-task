package terra.backend.domain.cpu.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import terra.backend.common.utils.DateUtils;
import terra.backend.domain.cpu.cache.CpuCacheImpl;
import terra.backend.domain.cpu.cache.dto.CpuUsage;
import terra.backend.domain.cpu.entity.CpuDailyUsage;
import terra.backend.domain.cpu.entity.CpuHourlyUsage;
import terra.backend.domain.cpu.entity.CpuMinuteUsage;
import terra.backend.domain.cpu.mapper.CpuUsageMapper;
import terra.backend.domain.cpu.repository.DailyUsageRepository;
import terra.backend.domain.cpu.repository.HourlyUsageRepository;
import terra.backend.domain.cpu.repository.MinuteUsageRepository;
import terra.backend.domain.cpu.validation.enums.DateValidType;
import terra.backend.domain.dto.response.CpuDailyUsageResponse;
import terra.backend.domain.dto.response.CpuDailyUsageResponse.CpuDailyUsageDto;
import terra.backend.domain.dto.response.CpuHourUsageResponse;
import terra.backend.domain.dto.response.CpuHourUsageResponse.CpuHourUsageDto;
import terra.backend.domain.dto.response.CpuMinuteUsageResponse;
import terra.backend.domain.dto.response.CpuResponseDto;

@ExtendWith(MockitoExtension.class)
class CpuServiceImplTest {

  @Mock DailyUsageRepository dailyUsageRepository;
  @Mock MinuteUsageRepository minuteUsageRepository;
  @Mock HourlyUsageRepository hourlyUsageRepository;
  @Spy CpuCacheImpl cpuCache;
  @Spy CpuUsageMapper mapper;
  @InjectMocks CpuServiceImpl service;

  @Test
  @DisplayName("단위 분 당 CPU 사용량 저장 save() 테스트")
  void saveMinuteUsageTest() {
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
  void saveHourlyUsageTest() {
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
  void saveDailyUsageTest() {
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

  @Test
  @DisplayName("findMinList 테스트")
  void findMinuteListTest() {
    LocalDateTime startDate = LocalDateTime.now().minusDays(1);
    LocalDateTime endDate = LocalDateTime.now();

    List<CpuMinuteUsage> list =
        List.of(
            new CpuMinuteUsage(1L, 10),
            new CpuMinuteUsage(2L, 24),
            new CpuMinuteUsage(3L, 53),
            new CpuMinuteUsage(4L, 14));
    BDDMockito.given(
            minuteUsageRepository.findBetweenDate(
                Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class)))
        .willReturn(list);

    CpuResponseDto result = service.findUsage(startDate, endDate, DateValidType.MIN);
    CpuMinuteUsageResponse transResult = (CpuMinuteUsageResponse) result;
    Mockito.verify(minuteUsageRepository, Mockito.times(1))
        .findBetweenDate(Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class));

    Assertions.assertThat(result).isInstanceOf(CpuMinuteUsageResponse.class);
    Assertions.assertThat(transResult.getCpuUsageList().stream().map(m -> m.getUsage()))
        .contains(10, 24, 53, 14);
  }

  @Test
  @DisplayName("findHourList 테스트 : 캐쉬 데이터에 조회할 데이터가 없을 때")
  void findHourListWithoutCacheTest() {
    LocalDateTime startDate = LocalDateTime.now().minusDays(5);
    LocalDateTime endDate = LocalDateTime.now();

    List<CpuHourlyUsage> list = new ArrayList<>();
    list.add(new CpuHourlyUsage(50, 10, 25.4, 400, 100, LocalDateTime.now()));
    list.add(new CpuHourlyUsage(78, 20, 26, 400, 100, LocalDateTime.now()));
    list.add(new CpuHourlyUsage(65, 15, 56, 400, 100, LocalDateTime.now()));
    list.add(new CpuHourlyUsage(87, 26, 30, 400, 100, LocalDateTime.now()));

    BDDMockito.given(
            hourlyUsageRepository.findBetweenDate(
                Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class)))
        .willReturn(list);

    CpuResponseDto result = service.findUsage(startDate, endDate, DateValidType.HOUR);
    CpuHourUsageResponse transResult = (CpuHourUsageResponse) result;
    Mockito.verify(hourlyUsageRepository, Mockito.times(1))
        .findBetweenDate(Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class));

    Assertions.assertThat(result).isInstanceOf(CpuHourUsageResponse.class);
    Assertions.assertThat(transResult.getCpuUsageList().stream().map(CpuHourUsageDto::getMaxUsage))
        .contains(50, 78, 65, 87);
    Assertions.assertThat(transResult.getCpuUsageList().stream().map(CpuHourUsageDto::getMinUsage))
        .contains(10, 20, 15, 26);
    Assertions.assertThat(transResult.getCpuUsageList()).hasSize(4);
  }

  @Test
  @DisplayName("findHourList 테스트 : 캐쉬 데이터에 조회할 데이터가 있을 때")
  void findHourListWithCacheTest() {
    LocalDateTime startDate = LocalDateTime.now().minusDays(5);
    LocalDateTime endDate = LocalDateTime.now();

    List<CpuHourlyUsage> list = new ArrayList<>();
    list.add(new CpuHourlyUsage(50, 10, 25.4, 400, 100, LocalDateTime.now().minusDays(1)));
    list.add(new CpuHourlyUsage(78, 20, 26, 400, 100, LocalDateTime.now().minusDays(2)));
    list.add(new CpuHourlyUsage(65, 15, 56, 400, 100, LocalDateTime.now().minusDays(3)));
    list.add(new CpuHourlyUsage(87, 26, 30, 400, 100, LocalDateTime.now().minusDays(4)));

    cpuCache.save(new CpuMinuteUsage(23, LocalDateTime.now().minusHours(1).minusMinutes(1)));
    cpuCache.save(new CpuMinuteUsage(23, LocalDateTime.now().minusHours(1).minusMinutes(2)));
    cpuCache.save(new CpuMinuteUsage(23, LocalDateTime.now().minusHours(1).minusMinutes(3)));
    cpuCache.save(new CpuMinuteUsage(23, LocalDateTime.now().minusHours(1).minusMinutes(4)));
    cpuCache.save(new CpuMinuteUsage(23, LocalDateTime.now().minusHours(1).minusMinutes(5)));

    BDDMockito.given(
            hourlyUsageRepository.findBetweenDate(
                Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class)))
        .willReturn(list);

    CpuResponseDto result = service.findUsage(startDate, endDate, DateValidType.HOUR);
    CpuHourUsageResponse transResult = (CpuHourUsageResponse) result;
    Mockito.verify(hourlyUsageRepository, Mockito.times(1))
        .findBetweenDate(Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class));

    Assertions.assertThat(result).isInstanceOf(CpuHourUsageResponse.class);
    Assertions.assertThat(transResult.getCpuUsageList().stream().map(CpuHourUsageDto::getMaxUsage))
        .contains(50, 78, 65, 87, 23);
    Assertions.assertThat(transResult.getCpuUsageList().stream().map(CpuHourUsageDto::getMinUsage))
        .contains(10, 20, 15, 26, 23);
    Assertions.assertThat(transResult.getCpuUsageList()).hasSize(5);
  }

  @Test
  @DisplayName("findDayList 테스트 : 캐쉬 데이터에 조회할 데이터가 없을 때")
  void findDayListWithoutCacheTest() {
    LocalDate startDate = LocalDate.now().minusYears(5);
    LocalDate endDate = LocalDate.now();

    List<CpuDailyUsage> list = new ArrayList<>();
    list.add(new CpuDailyUsage(70, 20, 24.4, LocalDate.now().minusDays(2)));
    list.add(new CpuDailyUsage(60, 10, 30.4, LocalDate.now().minusDays(3)));
    list.add(new CpuDailyUsage(50, 23, 34.4, LocalDate.now().minusDays(4)));
    list.add(new CpuDailyUsage(40, 24, 30.4, LocalDate.now().minusDays(5)));

    BDDMockito.given(
            dailyUsageRepository.findBetweenDate(
                Mockito.any(LocalDate.class), Mockito.any(LocalDate.class)))
        .willReturn(list);

    CpuResponseDto result = service.findUsage(startDate, endDate, DateValidType.DAY);
    CpuDailyUsageResponse transResult = (CpuDailyUsageResponse) result;
    Mockito.verify(dailyUsageRepository, Mockito.times(1))
        .findBetweenDate(Mockito.any(LocalDate.class), Mockito.any(LocalDate.class));

    Assertions.assertThat(result).isInstanceOf(CpuDailyUsageResponse.class);
    Assertions.assertThat(transResult.getCpuUsageList().stream().map(CpuDailyUsageDto::getMaxUsage))
        .contains(70, 60, 50, 40);
    Assertions.assertThat(transResult.getCpuUsageList().stream().map(CpuDailyUsageDto::getMinUsage))
        .contains(10, 20, 23, 24);
    Assertions.assertThat(transResult.getCpuUsageList()).hasSize(4);
  }

  @Test
  @DisplayName("findDayList 테스트 : 캐쉬 데이터에 조회할 데이터가 있을 때")
  void findDayListWithCacheTest() {
    LocalDate startDate = LocalDate.now().minusYears(5);
    LocalDate endDate = LocalDate.now();

    List<CpuDailyUsage> list = new ArrayList<>();
    list.add(new CpuDailyUsage(70, 20, 24.4, LocalDate.now().minusDays(2)));
    list.add(new CpuDailyUsage(60, 10, 30.4, LocalDate.now().minusDays(3)));
    list.add(new CpuDailyUsage(50, 23, 34.4, LocalDate.now().minusDays(4)));
    list.add(new CpuDailyUsage(40, 24, 30.4, LocalDate.now().minusDays(5)));

    cpuCache.save(new CpuMinuteUsage(23, LocalDateTime.now().minusHours(1).minusMinutes(1)));
    cpuCache.save(new CpuMinuteUsage(23, LocalDateTime.now().minusHours(1).minusMinutes(2)));
    cpuCache.save(new CpuMinuteUsage(23, LocalDateTime.now().minusHours(1).minusMinutes(3)));
    cpuCache.save(new CpuMinuteUsage(23, LocalDateTime.now().minusHours(1).minusMinutes(4)));
    cpuCache.save(new CpuMinuteUsage(23, LocalDateTime.now().minusHours(1).minusMinutes(5)));

    BDDMockito.given(
            dailyUsageRepository.findBetweenDate(
                Mockito.any(LocalDate.class), Mockito.any(LocalDate.class)))
        .willReturn(list);

    CpuResponseDto result = service.findUsage(startDate, endDate, DateValidType.DAY);
    CpuDailyUsageResponse transResult = (CpuDailyUsageResponse) result;
    Mockito.verify(dailyUsageRepository, Mockito.times(1))
        .findBetweenDate(Mockito.any(LocalDate.class), Mockito.any(LocalDate.class));

    Assertions.assertThat(result).isInstanceOf(CpuDailyUsageResponse.class);
    Assertions.assertThat(transResult.getCpuUsageList().stream().map(CpuDailyUsageDto::getMaxUsage))
        .contains(70, 60, 50, 40, 23);
    Assertions.assertThat(transResult.getCpuUsageList().stream().map(CpuDailyUsageDto::getMinUsage))
        .contains(10, 20, 23, 24);
    Assertions.assertThat(transResult.getCpuUsageList()).hasSize(5);
  }
}
