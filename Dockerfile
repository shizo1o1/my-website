# Используем образ с Java для сборки приложения
FROM openjdk:17-jdk-slim AS build
WORKDIR /app

# Установка Maven
RUN apt-get update && \
    apt-get install -y maven

# Копируем исходный код приложения
COPY . .

# Собираем приложение
RUN mvn package

# Используем образ с Java для запуска приложения
FROM openjdk:17-jdk-slim
WORKDIR /app

# Копируем собранные файлы из предыдущего образа
COPY --from=build /app/target/my-web-site-0.0.1-SNAPSHOT.jar ./app.jar

# Указываем порт, на котором приложение будет слушать
EXPOSE 8080

# Переменные окружения для подключения к базе данных PostgreSQL
ENV DB_HOST=db
ENV DB_PORT=5432
ENV DB_NAME=my_web_site_db
ENV DB_USER=root
ENV DB_PASSWORD=root

# Команда запуска приложения
CMD ["java", "-jar", "app.jar", "--spring.datasource.url=jdbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_NAME}", "--spring.datasource.username=${DB_USER}", "--spring.datasource.password=${DB_PASSWORD}"]
