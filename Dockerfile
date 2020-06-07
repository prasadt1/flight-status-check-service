FROM openjdk:8-jdk-alpine
COPY ./target/flightstatus-0.0.1-SNAPSHOT.jar /usr/src/flightstatus/
WORKDIR /usr/src/flightstatus
RUN sh -c 'touch flightstatus-0.0.1-SNAPSHOT.jar'
EXPOSE 8080 9200 9300 5601
CMD ["java", "-jar", "flightstatus-0.0.1-SNAPSHOT.jar"]