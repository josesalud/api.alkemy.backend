# Base image
FROM openjdk:17-jdk
LABEL authors="salud"

# Copiar la aplicación Java a la imagen
COPY ./target/technical-0.0.1-SNAPSHOT.jar /technical-test-api.jar



# Comando para ejecutar la aplicación Java
CMD ["java", "-jar", "/technical-test-api.jar"]