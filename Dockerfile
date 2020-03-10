FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/be-assignment-0.0.1-SNAPSHOT.jar hrs-app.jar
ENTRYPOINT ["java","-jar","hrs-app.jar"]