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
    <li><a href="#port-list">Port List</a></li>
        <li><a href="#built-with">Built With</a></li>
     <li><a href="#contact">Contact</a></li>
    <li><a href="#contributing">Contributing</a></li>
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
  ```sh
   docker-compose -f services.yml up
   ```
Services will be running in a few minutes.


#### Option 2: 
If you want to debug, run the following in order:
1. config-server
2. eureka-server
3. gateway-server
4. zipkin-server
After that you can run which service you want.



## Port List
* [Film Services](http://localhost:8088/film-controller/api/)
* [User Services](http://localhost:8088/controller/api/)
* [Film Services-Swagger](http://localhost:8088/film-controller/swagger-ui.html#)
* [User Services-Swagger](http://localhost:8088/controller/swagger-ui.html#)

* [Keycloak Admin Panel](http://localhost:9091/auth/)

* [Config Server Status](http://localhost:8088/actuator/health)
* [Eureka Server](http://localhost:8761/eureka/)

* [Zipkin Server](http://localhost:9411/zipkin/)
* [Prometheus](http://localhost:9090/)
* [Grafana](http://localhost:3000/)
* [Elastic](http://localhost:9200/)
* [Kibana](http://localhost:5601/)


Add Index Pattern in Kibana Discover page:
  ```sh
   spring-boot-elk-logs-YYYY.MM.dd 
   ```


## Built With

### Technologies
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

### Architectures
1. Microservices Architecture
2. Layered Architecture


## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request


## Contact
### Would you like to any notify or suggest something to me?
mbahardogan@hotmail.com
