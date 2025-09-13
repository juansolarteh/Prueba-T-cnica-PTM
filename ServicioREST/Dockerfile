# Etapa 1: Construcción
FROM maven:3.9.9-eclipse-temurin-23 AS build
WORKDIR /app

# Copiar pom.xml y descargar dependencias primero (cacheo)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiamos y compilamos el servicio REST
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final
FROM eclipse-temurin:23-jre
WORKDIR /app

RUN apt-get update && apt-get install -y locales \
    && sed -i '/es_ES.UTF-8/s/^# //g' /etc/locale.gen \
    && locale-gen es_ES.UTF-8 \
    && apt-get clean

ENV LANG=es_ES.UTF-8
ENV LANGUAGE=es_ES:es
ENV LC_ALL=es_ES.UTF-8

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]