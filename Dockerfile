FROM adoptopenjdk/openjdk11:alpine-slim
EXPOSE 8095
ADD target/clients.jar app.jar
ENTRYPOINT ["java", "-jar","/app.jar"]