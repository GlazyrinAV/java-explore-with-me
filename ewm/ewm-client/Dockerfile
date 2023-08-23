FROM amazoncorretto:11-alpine-jdk
COPY target/*.jar ewm-client.jar
ENTRYPOINT ["java", "-jar", "/ewm-client.jar"]