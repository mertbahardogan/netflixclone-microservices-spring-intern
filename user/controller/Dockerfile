FROM openjdk:11
#EXPOSE 8000
#COPY ./target/*.jar app.jar
#ENTRYPOINT [ "java", "-jar", "/app.jar" ]

EXPOSE 8000
RUN mkdir target
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} target/app.jar
ENTRYPOINT ["java","-jar","/target/app.jar"]