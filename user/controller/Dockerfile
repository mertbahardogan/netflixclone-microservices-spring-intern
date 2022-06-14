FROM openjdk:11
EXPOSE 9009
WORKDIR /microservices-netflix-clone/user/controller/
COPY "target/controller-0.0.1-SNAPSHOT.jar" app.jar
CMD [ "java", "-jar", "app.jar" ]