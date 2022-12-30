FROM openjdk:17-jdk-slim-buster
EXPOSE 9003

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} ui-0.0.1-snapshot.jar
ENTRYPOINT ["java","-jar","/ui-0.0.1-snapshot.jar"]