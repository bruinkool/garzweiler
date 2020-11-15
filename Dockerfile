FROM maven:3-openjdk-15-slim AS MAVEN_BUILD
COPY pom.xml /build/
WORKDIR /build/
# build all dependencies for offline use / cache
RUN mvn dependency:go-offline -B
COPY src /build/src/
RUN mvn package -Dmaven.test.skip=true

FROM openjdk:15-alpine
WORKDIR /app
COPY --from=MAVEN_BUILD /build/target/garzweiler.jar /app/
ENTRYPOINT ["java", "-jar", "garzweiler.jar"]