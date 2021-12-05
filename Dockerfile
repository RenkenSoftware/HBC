FROM maven:3.8.2-eclipse-temurin-17 AS builder

COPY . .

RUN mvn clean package -DskipTests=true


FROM eclipse-temurin:17_35-jdk

COPY --from=builder target/TheNetwork-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT java -jar app.jar