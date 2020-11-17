FROM amazoncorretto:11
ENV STAGE_NAME staging
WORKDIR /usr/app
COPY target/demo.jar .
#COPY extras/newrelic/ newrelic/
CMD java -Duser.timezone="America/Santiago" -Xms1024m -Xmx1024m -jar demo.jar
