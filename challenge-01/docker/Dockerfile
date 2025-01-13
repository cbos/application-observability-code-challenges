FROM docker.io/eclipse-temurin:21.0.5_11-jre-alpine

# Expose the port to the external world
EXPOSE 8080

RUN mkdir /app

# create a nonroot user and group
RUN addgroup -S javauser && adduser -S -s /bin/false -G javauser javauser

# Copy the application
COPY target/*.jar /app/challenge-app.jar
WORKDIR /app

# Change ownership to non-root user
RUN chown -R javauser:javauser /app

## set the nonroot user as the default user
USER javauser

CMD "java" "-jar" "challenge-app.jar"
