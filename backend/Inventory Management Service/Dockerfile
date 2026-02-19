FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /workspace

# copy maven settings and project
COPY pom.xml mvnw ./
COPY .mvn .mvn
COPY src src

RUN mvn -B -DskipTests package

FROM eclipse-temurin:21-jre
WORKDIR /app

# copy built jar
COPY --from=build /workspace/target/*.jar app.jar

ENV JAVA_OPTS=""
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
