FROM maven:openjdk AS builder
COPY src /app/src
COPY pom.xml /app
RUN mvn -f /app/pom.xml clean package

FROM bellsoft/liberica-openjdk-alpine:17
COPY --from=builder /app/target/hw16-docker.jar /app/hw16-docker.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/hw16-docker.jar"]
