# Filmorate

## Описание проекта

Filmorate - это приложение для составления рейтинга фильмов и обмена рекомендациями между пользователями. Цель проекта - улучшить процесс выбора фильмов и обеспечить пользователям персонализированные рекомендации. Проект разработан с использованием Java и фреймворка Spring Boot.

### Функциональность

- Добавление и обновление фильмов в рейтинг.
- Оценка фильмов пользователями.
- Объединение пользователей в комьюнити для обмена рекомендациями.
- Возможность добавления друзей и оценка фильмов друзей.

## Технологический стек

- Java, Spring Boot, H2 Database, Maven

## Настройка и развертывание

### Системные требования

- Java 11 или выше
- Maven
- Интернет-браузер

### Инструкция по развёртыванию

1. Склонируйте репозиторий: `git clone https://github.com/EugeneP87/filmorate.git`
2. Перейдите в директорию проекта: `cd filmorate`
3. Соберите проект: `mvn clean install`
4. Запустите приложение: `mvn spring-boot:run`

Приложение будет доступно по адресу: [http://localhost:8080](http://localhost:8080)

## Диаграмма базы данных

На диаграмме базы данных отображены основные таблицы, представляющие фильмы, пользователей, отзывы и другие сущности. Отношения между таблицами демонстрируют, как связаны различные компоненты системы. Это помогает лучше понять структуру базы данных и взаимодействие между различными частями приложения Filmorate.

![Database Diagram](./schema.png)