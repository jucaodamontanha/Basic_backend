# Etapa de build com Maven e JDK já prontos (mais leve e estável)
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Definir diretório de trabalho
WORKDIR /app

# Copiar os arquivos do projeto
COPY . .

# Construir o projeto e pular os testes para agilizar
RUN mvn clean install -DskipTests


# Etapa final: apenas a JDK e o .jar pronto
FROM openjdk:17-jdk-slim

# Definir diretório de trabalho
WORKDIR /app

# Expor a porta padrão do Spring Boot
EXPOSE 8080

# Copiar o jar gerado na fase anterior
COPY --from=build /app/target/*.jar app.jar

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
