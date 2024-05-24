package terra.backend.domain.cpu.validation.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DateValidType {
  MIN("분 단위 CPU 사용량 조회는 최근 1주일 데이터만 제공이 합니다.", 7),
  HOUR("시간 단위 CPU 사용량 조회는 최근 3월 데이터만 제공이 합니다.", 3),
  DAY("일 단위 CPU 사용량 조회는 최근 1년 데이터만 제공이 합니다.", 1);

  private String message;
  private int limit;
}
