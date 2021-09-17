FROM openjdk:11
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
EXPOSE 6007
COPY "target/controller-0.0.1-SNAPSHOT.jar" app.jar
CMD [ "java", "-jar", "app.jar" ]
