FROM openjdk:17
MAINTAINER supershop
COPY target/supershop-0.0.1-SNAPSHOT.jar  /tmp/supershop-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD ["java","-jar","/tmp/supershop-0.0.1-SNAPSHOT.jar"]
