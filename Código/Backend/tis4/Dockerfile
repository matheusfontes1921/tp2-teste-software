FROM maven:3.8.4-openjdk-17 as build

WORKDIR /back

COPY ./Backend/tis4 /back

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk-alpine

WORKDIR /back

COPY --from=build /back/target/tis4-0.0.1-SNAPSHOT.jar tis4.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "tis4.jar"]

