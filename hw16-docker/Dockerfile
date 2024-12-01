FROM maven:openjdk AS builder
COPY src /app/src
COPY pom.xml /app
RUN mvn -f /app/pom.xml clean package

FROM bellsoft/liberica-openjdk-alpine:17
COPY --from=builder /app/target/hw16-docker-0.0.1-SNAPSHOT.jar /app/hw16-docker-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/hw16-docker-0.0.1-SNAPSHOT.jar"]
