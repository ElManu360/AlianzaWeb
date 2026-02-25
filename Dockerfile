# Fase de construcción
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Fase de ejecución
FROM openjdk:17-oracle
COPY --from=build /target/*.jar app.jar
EXPOSE 8084
ENTRYPOINT ["java","-jar","app.jar"]