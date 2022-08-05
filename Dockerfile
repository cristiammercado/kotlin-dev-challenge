# Pull from Java base image
FROM azul/zulu-openjdk-alpine:17-jre-headless

# Create working dir
RUN mkdir /app && cd /app

# Adding fonts
RUN apk add --no-cache fontconfig ttf-dejavu libstdc++

# Copy files to working dir
COPY build/libs/golito-api.jar /app/api.jar

# Use /app as the working directory
WORKDIR /app

# Entry command
CMD ["java", "-Dlogback.configurationFile=logback.xml", "-jar", "api.jar"]
