# Estágio 1: Construir a aplicação com o Maven
# Usamos uma imagem oficial do Maven com Java 17
FROM maven:3.8-openjdk-17 AS build

# Define o diretório de trabalho principal
WORKDIR /app

# Copia apenas o pom.xml primeiro para aproveitar o cache do Docker.
# Se o pom.xml não mudar, o Docker não baixará as dependências de novo.
COPY conectapet-backend/pom.xml .

# Baixa todas as dependências do projeto
RUN mvn dependency:go-offline

# Agora copia o resto do código-fonte do backend
COPY conectapet-backend/src ./src

# Executa o comando para compilar e empacotar a aplicação
RUN mvn package -DskipTests

# Estágio 2: Executar a aplicação
# Usamos uma imagem leve, apenas com o Java 17
FROM openjdk:17-slim

WORKDIR /app

# Copia o arquivo .jar que foi gerado no estágio anterior
COPY --from=build /app/target/conectapet-0.0.1-SNAPSHOT.jar .

# Expõe a porta 8080 para o Render
EXPOSE 8080

# Comando para iniciar a aplicação
CMD ["java", "-jar", "conectapet-0.0.1-SNAPSHOT.jar"]
