FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

COPY ./build/libs/cash-0.0.1.jar /app/cash-0.0.1.jar

EXPOSE 8081
CMD ["java", "-jar", "cash-0.0.1.jar"]
