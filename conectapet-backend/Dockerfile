# Estágio 1: Construir a aplicação com o Maven
FROM maven:3.8-openjdk-17 AS build

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia o arquivo de configuração do Maven (do caminho correto)
COPY conectapet-backend/pom.xml .

# Copia todo o código-fonte do backend (do caminho correto)
COPY conectapet-backend/src ./src

# Executa o comando do Maven para "empacotar" a aplicação
RUN mvn -f pom.xml clean package -DskipTests

# Estágio 2: Executar a aplicação
FROM openjdk:17-slim

# Define o diretório de trabalho
WORKDIR /app

# Copia o arquivo .jar que foi gerado no estágio anterior
COPY --from=build /app/target/conectapet-0.0.1-SNAPSHOT.jar .

# Expõe a porta 8080
EXPOSE 8080

# O comando para iniciar a aplicação
CMD ["java", "-jar", "conectapet-0.0.1-SNAPSHOT.jar"]
