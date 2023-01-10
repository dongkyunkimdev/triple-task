FROM eclipse-temurin:17-jdk
EXPOSE 8080
COPY ./build/libs/*.jar /app/spring-boot-application.jar
ENTRYPOINT ["java", "-jar", "-DSpring.profiles.active=production", "/app/spring-boot-application.jar"]