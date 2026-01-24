#Tem que dar clean e depois package no docker para ele refazer o target
#A imagem do docker é baseada no .jar(segundo as configs abaixo) da pasta 'target'
FROM eclipse-temurin:21-jdk
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
