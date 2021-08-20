FROM openjdk:11
ADD target/simple-economy-game-spring-boot-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD java -jar simple-economy-game-spring-boot-0.0.1-SNAPSHOT.jar