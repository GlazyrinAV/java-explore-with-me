FROM amazoncorretto:11
ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5082
COPY target/*.jar stats-server.jar
ENTRYPOINT ["java", "-jar", "/stats-server.jar"]