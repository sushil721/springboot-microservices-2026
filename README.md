# springboot-microservices-2026
springboot-microservices-2026

H2-DB:

http://localhost:9001/h2-console

![h2-db-img.png](h2-db-img.png)

Open-API URL: http://localhost:9001/swagger-ui.html

### Docker setup Documentation
For further reference, please consider the following sections:

#### 3 Ways to build Spring Boot Application in Docker
A) Dockerfile (account-service)

B) Buildpack (loans-service)

C) Google Jib (cards-service)

#### A) Containerize account-service with Dockerfile :
* Add port in properties file.
```bash
server.port=9001
```

* Dockerhub openJDK link for Dockerfile: [https://hub.docker.com/_/eclipse-temurin](https://hub.docker.com/_/eclipse-temurin)

* Create Dockerfile in project account-service directory
```bash
FROM eclipse-temurin:25-jre-ubi10-minimal
LABEL "org.opencontainers.image.authors"="sushilgiacr@gmail.com"
COPY target/account-service-0.0.1-SNAPSHOT.jar account-service-1.0.0.jar
ENTRYPOINT ["java", "-jar", "account-service-1.0.0.jar"]
```

* Create Docker image (Open Docker Desktop and cmd) :
```bash
docker version
docker build -t sushilgiacr/account-service:s4 .
```
** Note : here . (dot) means current working directory where Dockerfile exist.

** create image with tag name
```bash
docker build -t sushilgiacr/account-service:s4 .
```

* Check created image
```bash
docker images
docker images inspect <IMAGE_ID>
```

* Docker image run :
```bash
docker run --name account-service-9001 -p 9001:9001 sushilgiacr/account-service:s4
```

**Note: 9001:9001 -> first to access, 2nd to run docker container. eg,

```bash
docker run -p 9001:9001 sushilgiacr/account-service:s4
```
** We can access with http://localhost:9001/v1/accounts/ping

* Check Running containers
```bash
docker ps
docker ps -a
```

* Run container in Detached-mode (-d) (in background). Ctrl+C will not terminate our service.
```bash
docker run -p 9001:9001 -d sushilgiacr/account-service:s4
```
**Note: It will give us image ID: 3409e6ebe84740b419982c77124d45ac50effaab8c25da9cdac727ad23fcda71

* Check Swagger-UI URL: http://localhost:9001/swagger-ui.html

* Check detached-mode running application log.
```bash
docker logs -f 3409
```
**Note: 3409 is first 4 digit of running id of above container.

* Stop running docker container:
```bash
docker ps
docker stop 3409
docker ps -a
docker start 3409
docker stop 3409
docker rm 3409
```

#### B) Containerize loans-service using Buildpack :
* Cloud native buildpack link for Dockerfile: [https://buildpacks.io/](https://buildpacks.io/)
* Add port in properties file.
```bash
server.port=9002
```
* Add plugin configuration in pom.xml
```xml
<build>
    <plugins>
        <plugin>
            <configuration>
                <image>
                    <name>sushilgiacr/loans-service:s4</name>
                </image>
            </configuration>
        </plugin>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>    
```
* Clean and package the application using maven command or by IDE
```bash
mvn clean package
mvn spring-boot:build-image
```

* Goto loans-service directory
```bash
cd loans-service
```
* Run mvn command to build docker image using buildpack
```bash
mvn spring-boot:build-image
```
* Check the built image
```bash
docker images
```
* Run the built image
```bash
docker run --name loans-service-9002 -d -p 9002:9002 sushilgiacr/loans-service:s4
```
* Check running containers
```bash
docker ps -a
```

* Check Swagger-UI URL: http://localhost:9002/swagger-ui.html

