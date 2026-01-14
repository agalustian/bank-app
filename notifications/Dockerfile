FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

COPY ./build/libs/notifications-0.0.1.jar /app/notifications-0.0.1.jar

EXPOSE 8081
CMD ["java", "-jar", "notifications-0.0.1.jar"]
