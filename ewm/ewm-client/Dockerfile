FROM amazoncorretto:11-alpine-jdk
COPY target/ewm-client-0.0.1-SNAPSHOT-exec.jar ewm-client.jar
ENTRYPOINT ["java", "-jar", "/ewm-client.jar"]