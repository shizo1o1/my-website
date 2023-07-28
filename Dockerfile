# Используем образ с Java для сборки приложения
FROM openjdk:17-jdk-slim AS build
WORKDIR /my-web-site

# Установка Maven
RUN apt-get update && \
    apt-get install -y maven

# Копируем исходный код приложения
COPY . .

# Собираем приложение
RUN mvn package

# Используем образ с Java для запуска приложения
FROM openjdk:17-jdk-slim
WORKDIR /my-web-site

# Копируем собранные файлы из предыдущего образа
COPY --from=build /my-web-site/target/my-web-site-0.0.1-SNAPSHOT.jar ./app.jar

# Копирование файла price.xls внутрь контейнера
COPY src/main/resources/static/price.xls /price.xls
# Указываем порт, на котором приложение будет слушать
EXPOSE 8080

# Команда запуска приложения
CMD ["java", "-jar", "app.jar"]
