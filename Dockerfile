# openjdk 17 기반 이미지 (스프링부트 3.x 기준)
FROM eclipse-temurin:17-jdk-focal

# 작업 디렉토리 생성
WORKDIR /app

# 빌드된 jar 파일 복사 (build/libs/your-app.jar 로 빌드했다고 가정)
COPY build/libs/*.jar app.jar

# 컨테이너 시작 시 실행 명령
ENTRYPOINT ["java", "-jar", "app.jar"]
