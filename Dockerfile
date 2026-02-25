FROM maven:3.8.4-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM amazoncorretto:17-al2-jdk
COPY --from=build /target/*.jar app.jar
EXPOSE 8084
ENTRYPOINT ["java","-jar","/app.jar"]