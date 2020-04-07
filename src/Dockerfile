FROM openjdk:11-jdk AS carxapp
ENV TZ=Europe/Moscow
COPY carx.jar /usr/src/myapp/carx.jar
WORKDIR /usr/src/myapp
CMD java -jar carx.jar

FROM postgres:10.1-alpine as postgres

FROM re
