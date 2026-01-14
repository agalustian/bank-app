FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

COPY ./build/libs/app-0.0.1.jar /app/app-0.0.1.jar

EXPOSE 8081
CMD ["java", "-jar", "app-0.0.1.jar"]
