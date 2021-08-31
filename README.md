# Getting Started with My Microservices Arch.

<details open="open">
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#requirements">Requirements</a>
    </li>
      <li>
      <a href="#prerequisites">Prerequisites</a>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a>
      </ul>
       </li>
        <li><a href="#built-with">Built With</a></li>
  </ol>
</details>

## Requirements


1. Java JDK 11
2. Docker Desktop
3. IDE (IntelliJ)
4. PostgreSQL


## Prerequisites

### 1-Run the following command in main project in terminal:
   ```sh
   mvn clean install
   ```
This command Dockerize all microservices automatically.

### 2-Run the following commands in docker-compose folder in terminal:
   ```sh
   docker-compose -fkafka_cluster.yml -f elastic_cluster.yml -f monitoring.yml -f keycloak_authorization_server.yml up
   
   docker run -d -p 9411:9411 openzipkin/zipkin
   ```
   
   
  
### 3-Send the following files to PostgreSQL.
##### `BackupNetflix and DataNetflix Files.` 
After that; You can reach every service.Look at the <a href="#port-list">Port Lists.</a> 



### 4- Choice time:

#### Option 1: 
If you want to run in docker, just do the following items:
Run into docker-compose file>  docker-compose -f services.yml up
Services will be running in a few minutes.


#### Option 2: 
If you want to debug, run the following in order:
-config-server
-eureka-server
-gateway-server
-zipkin-server
-After that you can run which service you want.



## Port List

Film Services: http://localhost:8088/film-controller/api/..
User Services: http://localhost:8088/controller/api/..

Use Swagger:
http://localhost:8088/film-controller/swagger-ui.html#
http://localhost:8088/controller/swagger-ui.html#

Keycloak Admin Panel: http://localhost:9091/auth/

Eureka Server: http://localhost:8761/eureka/
Config Server Status: http://localhost:8088/actuator/health

Zipkin Server: http://localhost:9411/zipkin/
Prometheus: http://localhost:9090/ 
Grafana: http://localhost:3000/

Elastic: http://localhost:9200/ 
Kibana: http://localhost:5601/  
Add index pattern in discover page
spring-boot-elk-logs-YYYY.MM.dd 

## Built With

1. Java 
2. Spring Boot
3. Spring Cloud
4. Docker and Docker Compose
5. PostgreSQL
6. Swagger
7. ELK Stack (Elascticsearch-Logstash-Kibana)
8. Keycloak
9. Prometheus & Grafana
10. Zipkin




Would you like to any notify or suggest something to me?
mbahardogan@hotmail.com
