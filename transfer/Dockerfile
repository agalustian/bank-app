FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

COPY ./build/libs/transfer-0.0.1.jar /app/transfer-0.0.1.jar

EXPOSE 8081
CMD ["java", "-jar", "transfer-0.0.1.jar"]
