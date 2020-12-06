FROM adoptopenjdk/openjdk15:alpine-slim AS MAVEN_BUILD
COPY .mvn/wrapper/maven-wrapper.properties /build/.mvn/wrapper/
COPY .mvn/wrapper/MavenWrapperDownloader.java /build/.mvn/wrapper/
COPY ./mvnw /build/
COPY pom.xml /build/
WORKDIR /build/
RUN ./mvnw dependency:go-offline
COPY src /build/src/
RUN ./mvnw package -DskipTests

FROM adoptopenjdk/openjdk15:alpine-slim
WORKDIR /app
COPY --from=MAVEN_BUILD /build/target/garzweiler.jar /app/
ENTRYPOINT ["java", "-jar", "garzweiler.jar"]