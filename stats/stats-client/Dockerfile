FROM amazoncorretto:11
ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5081
COPY target/*.jar stats-client.jar
ENTRYPOINT ["java", "-jar", "/stats-client.jar"]