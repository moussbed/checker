FROM openjdk:17-alpine

EXPOSE 8761

RUN mkdir -p /usr/app

COPY ./target/discovery-*.jar /usr/app

WORKDIR /usr/app

CMD java -Djava.security.egd=file:/dev/./urandom -jar discovery-*.jar
