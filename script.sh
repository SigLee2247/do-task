#!/bin/bash

# Spring Boot JAR 파일 경로
JAR_PATH="./lovlind-0.0.1-SNAPSHOT.jar"

# 프로세스 ID 파일 경로
PID_FILE="app.pid"

# 환경설정
ARGV="-Duser.timezone=Asia/Seoul -Dspring.profiles.active=prod"

# 스크립트 사용법 안내
function usage() {
    echo "Usage: $0 {start|stop}"
    exit 1
}

# Spring Boot 애플리케이션 시작 함수
function start() {
    ./gradlew spotlessApply
    ./gradlew clean build

    echo "Starting Spring Boot Build"
    echo "docker-compose up"
    if docker-compose up --build -d; then
      echo "docker-compose up 성공적으로 실행됨"
    else
      echo "docker-compose 실행 중 문제가 발생했습니다."
    fi
}

# Spring Boot 애플리케이션 종료 함수
function stop() {
    echo "docker-compose down"
    docker-compose down
}

# 인자에 따라 start 또는 stop 함수를 호출
case "$1" in
    start)
        start
        ;;
    stop)
        stop
        ;;
    *)
        usage
        ;;
esac

