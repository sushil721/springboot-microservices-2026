#Base image containing Java runtime
# Search for the appropriate base image
FROM eclipse-temurin:25-jre-ubi10-minimal

# Information around who maintain the image
LABEL "org.opencontainers.image.authors"="sushilgiacr@gmail.com"

# Add the application jar to the container
COPY target/account-service-0.0.1-SNAPSHOT.jar account-service-1.0.0.jar

# Execute the application
ENTRYPOINT ["java", "-jar", "account-service-1.0.0.jar"]

