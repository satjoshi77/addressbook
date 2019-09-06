# Pull base image.
FROM ubuntu:latest

RUN \
# Update
apt-get update -y && \
# Install Java
apt-get install default-jre -y

ADD ./target/addressbook-app-0.0.1-SNAPSHOT-fat.jar spring-mvc-example.jar

EXPOSE 8090

CMD java -jar spring-mvc-example.jar
