FROM openjdk:17
EXPOSE 8085
ADD target/UniHub-News-0.0.1-SNAPSHOT.jar newsms.jar
ENTRYPOINT ["java", "-jar", "newsms.jar"]