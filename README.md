Spring Boot con Docker 
==========================
Proyecto de API REST con DOCKER, a continuación, se enlistan los comandos necesarios para ejecutar el proyecto

Primero que nada hay que posicionarse en la raiz del proyecto:
Una vez estando en el directorio raiz hay que empaquetar el proyecto, con el siguiente comando:



    mvn clean package  -DskipTests


Una vez teniendo el proyecto, lo unico que falta por hacer es contruir los contenedores de Docker, para ello hay que ejecutar el siguiente comando:

    docker-compose up

Una vez ejecutando estos comandos el proyecto estará listo para usarse
