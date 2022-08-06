# Pull from Java base image
FROM azul/zulu-openjdk-alpine:17-jre-headless

# Create working dir
RUN mkdir /app && cd /app

# Copy files to working dir
COPY build/libs/mcontigo-api.jar /app/api.jar

# Use /app as the working directory
WORKDIR /app

# Entry command
CMD ["java", "-jar", "api.jar"]
