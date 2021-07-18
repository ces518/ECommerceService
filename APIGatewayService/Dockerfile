FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY target/APIGatewayService-1.0.jar ApiGatewayService.jar
ENTRYPOINT ["java", "-jar", "ApiGatewayService.jar"]