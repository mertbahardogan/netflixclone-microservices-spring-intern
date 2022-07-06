FROM openjdk:11
EXPOSE 8000
WORKDIR user/controller
COPY /target/*.jar app.jar
ENTRYPOINT [ "java", "-jar", "/app.jar" ]
