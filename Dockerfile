FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]

#FROM maven:3.8.4-openjdk-17 AS build
#WORKDIR /app
#COPY pom.xml .
#COPY src ./SRC
#RUN mvn clean install
#
#FROM openjdk:17-alpine
#WORKDIR /app
#COPY --from=build /app/target/e-commerce_backend-0.0.1-SNAPSHOT.jar ./demo-aws.jar
#EXPOSE 8080
#CMD ["java", "-jar", "demo-aws.jar"]
