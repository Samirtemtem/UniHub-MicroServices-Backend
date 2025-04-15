FROM openjdk:17
EXPOSE 8082
ADD target/ApiGateway-0.0.1-SNAPSHOT.jar ApiGateway.jar
ENTRYPOINT ["java", "-jar", "ApiGateway.jar"]