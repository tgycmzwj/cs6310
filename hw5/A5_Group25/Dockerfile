# use maven as a builder image to separate the compile step from our deployment image
FROM maven:3.8.1-openjdk-17-slim AS builder
WORKDIR application
COPY ./ ./

RUN mvn clean install -DskipTests

FROM openjdk:17-slim AS backend
WORKDIR application
# copy the jar from the builder image to the final image
COPY --from=builder /application/target/application-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]

