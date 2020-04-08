FROM openjdk:11-jdk AS carx-app
ENV TZ=Europe/Moscow
COPY carx.jar /usr/src/myapp/carx.jar
WORKDIR /usr/src/myapp
CMD java -jar carx.jar

