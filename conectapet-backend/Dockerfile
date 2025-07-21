# Estágio 1: Construir a aplicação com o Maven
# Usamos uma imagem oficial do Maven com Java 17 para compilar o projeto
FROM maven:3.8-openjdk-17 AS build

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia o arquivo de configuração do Maven para baixar as dependências
COPY pom.xml .

# Copia todo o código-fonte do backend
COPY src ./src

# Executa o comando do Maven para "empacotar" a aplicação, pulando os testes
RUN mvn -f pom.xml clean package -DskipTests

# Estágio 2: Executar a aplicação
# Usamos uma imagem leve, apenas com o Java 17 necessário para rodar
FROM openjdk:17-slim

# Define o diretório de trabalho
WORKDIR /app

# Copia o arquivo .jar que foi gerado no estágio anterior
COPY --from=build /app/target/conectapet-0.0.1-SNAPSHOT.jar .

# Expõe a porta 8080 para que o Render possa se comunicar com a aplicação
EXPOSE 8080

# O comando para iniciar a aplicação quando o contêiner rodar
CMD ["java", "-jar", "conectapet-0.0.1-SNAPSHOT.jar"]
