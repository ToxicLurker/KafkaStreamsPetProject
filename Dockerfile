FROM alpine/java:11-jdk

RUN apk add libstdc++6 libstdc++

ENTRYPOINT ["java", "-jar", "app/target/app-1.0-SNAPSHOT-jar-with-dependencies.jar"]