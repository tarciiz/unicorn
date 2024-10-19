# Usar uma imagem base mais leve do OpenJDK 21 e Maven para o build
FROM maven:3.9.5-eclipse-temurin-21 AS builder

# Definir o diretório de trabalho
WORKDIR /app

# Copiar o arquivo pom.xml e baixar as dependências necessárias para o build
COPY pom.xml /app/
RUN mvn dependency:go-offline

# Copiar o código-fonte do projeto
COPY src /app/src

# Construir o projeto sem rodar os testes
RUN mvn clean package -DskipTests

# Usar uma imagem base mais leve do OpenJDK 21 para o ambiente de produção
FROM eclipse-temurin:21-jre-alpine

# Definir o diretório de trabalho
WORKDIR /app

# Copiar o JAR construído do estágio de build
COPY --from=builder /app/target/*.jar /app/app.jar

# Configurar o comando de inicialização
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

# Expor a porta na qual a aplicação irá rodar
EXPOSE 8080