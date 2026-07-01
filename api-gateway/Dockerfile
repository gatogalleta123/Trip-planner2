FROM eclipse-temurin:21-jre-alpine
LABEL maintainer="TripPlanner"
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 9000
ENTRYPOINT ["java", "-jar", "app.jar"]