FROM java:8
MAINTAINER Aidan Delaney "aidan@ontologyengineering.org"

RUN mkdir /code
WORKDIR /code

ADD ["target/K-ttelbr-cke-2.0.0-SNAPSHOT.jar", "/code/"]

EXPOSE 8080
CMD ["/usr/lib/jvm/java-8-openjdk-amd64/bin/java", "-jar", "K-ttelbr-cke-2.1.0-SNAPSHOT.jar"]