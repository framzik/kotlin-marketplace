FROM bellsoft/liberica-openjdk-alpine:17
COPY build/libs/class-app-spring-1.0-SNAPSHOT.jar /app/app.jar

ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-jar", "/app/app.jar"]
