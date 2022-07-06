FROM openjdk:11
EXPOSE 8000
WORKDIR "user/controller/target/"
COPY "controller.jar" controller.jar
CMD [ "java", "-jar", "controller.jar" ]
