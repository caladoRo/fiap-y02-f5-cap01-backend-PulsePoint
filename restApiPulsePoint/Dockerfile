# Etapa 1: Build do projeto com Maven e Java 21
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app

# Copia o arquivo pom.xml e baixa dependências
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o código-fonte e compila o projeto
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Imagem final para executar o JAR
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copia o JAR da etapa de build
COPY --from=builder /app/target/*.jar app.jar

# Expõe
