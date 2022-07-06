FROM openjdk:11
EXPOSE 8000
WORKDIR user/controller
ADD /target/controller-0.0.1-SNAPSHOT.jar controller-0.0.1-SNAPSHOT.jar
ENTRYPOINT [ "java", "-jar", "/controller-0.0.1-SNAPSHOT.jar" ]
