FROM amazoncorretto:11
ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5081
COPY target/*.jar stats_server.jar
ENTRYPOINT ["java", "-jar", "/stats_server.jar"]