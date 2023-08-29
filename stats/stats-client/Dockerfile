FROM amazoncorretto:11-alpine-jdk
COPY target/stats-client-0.0.1-SNAPSHOT-exec.jar stats-client.jar
ENTRYPOINT ["java", "-jar", "/stats-client.jar"]