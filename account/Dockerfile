FROM maven:3.9-eclipse-temurin-11 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:11-jdk
WORKDIR /app

RUN apt-get update && apt-get install -y tzdata && \
    ln -sf /usr/share/zoneinfo/America/Guatemala /etc/localtime && \
    echo "America/Guatemala" > /etc/timezone && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

ENV TZ=America/Guatemala

COPY --from=build /app/target/account-0.0.1.jar app.jar

EXPOSE 8000

ENTRYPOINT ["java", "-jar", "app.jar"]
