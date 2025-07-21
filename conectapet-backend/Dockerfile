# Estágio 1: Construir a aplicação com o Maven
FROM maven:3.8-openjdk-17 AS build

# Copia o diretório inteiro do seu backend para uma pasta /app no contêiner
COPY conectapet-backend /app

# Define o diretório de trabalho para a pasta que acabamos de copiar
WORKDIR /app

# Executa o comando do Maven. Ele encontrará o pom.xml aqui.
RUN mvn clean package -DskipTests

# --- PASSO DE DIAGNÓSTICO ---
# Este comando irá listar todos os arquivos e pastas dentro do diretório de trabalho /app.
# O resultado aparecerá no log de build do Render.
RUN ls -R

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
