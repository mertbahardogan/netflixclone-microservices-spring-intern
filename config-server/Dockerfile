FROM openjdk:11
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
EXPOSE 8888
COPY "/target/config-server-0.0.1-SNAPSHOT.jar" app.jar
CMD [ "java", "-jar", "app.jar" ]
