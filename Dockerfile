FROM adoptopenjdk/openjdk11:jdk-11.0.6_10-alpine-slim
#FROM openjdk:11-jre
ENV STAGE_NAME staging
WORKDIR /usr/app
COPY target/demo.jar .
#COPY extras/newrelic/ newrelic/
CMD java -Duser.timezone="America/Santiago" -Xms1024m -Xmx1024m -jar demo.jar
