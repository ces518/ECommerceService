FROM openjdk:17-ea-jdk-slim
# Container 내부 /tmp 디렉터리 생성
VOLUME /tmp
#target 의 파일을 Container 내부의 UserService.jar 로 복사한다.
COPY target/userservice-1.0.jar UserService.jar
# 복사된 jar 실행
ENTRYPOINT ["java", "-jar", "UserService.jar"]
