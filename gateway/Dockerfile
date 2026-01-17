FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

COPY ./build/libs/gateway.jar /app/gateway.jar

EXPOSE 8081
CMD ["java", "-jar", "gateway.jar"]
