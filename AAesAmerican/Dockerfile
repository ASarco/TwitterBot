FROM openjdk:17-jdk-slim as builder
#WORKDIR build
#EXPOSE 8080
RUN mkdir target
ARG JAR_FILE=./target/AAesAmerican-1.0-jar-with-dependencies.jar
COPY ${JAR_FILE} target/app.jar
ENTRYPOINT java -jar /target/app.jar
    -Dtwitter4j.oauth.consumerKey=$consumerKey
    -Dtwitter4j.oauth.consumerSecret=$consumerSecret
    -Dtwitter4j.oauth.accessToken=$accessToken
    -Dtwitter4j.oauth.accessTokenSecret=$accessTokenSecret