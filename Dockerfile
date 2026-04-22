FROM eclipse-temurin:21.0.10_7-jre-ubi10-minimal
LABEL authors="Ponram"

COPY target/spring-feature-practice.jar spring-feature-practice.jar
EXPOSE 9080
ENTRYPOINT ["java", "-jar", "/spring-feature-practice.jar"]