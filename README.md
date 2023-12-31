# Дипломная работа Sky-pro "Бэкенд-часть для сайта объявлений на Java"

___

## Описание и основной функционал:

Сайт предаставляет возможность для продажи товаров. Зарегестрированные пользователи могут размещать объявления,
оставлять комментарии к ним.

Реализован следующий функционал:

- Авторизация и аутентификация пользователей.
- Распределение ролей между пользователями: пользователь и администратор.
- CRUD для объявлений на сайте: администратор может удалять или редактировать все объявления, а пользователи — только
  свои.
- Под каждым объявлением пользователи могут оставлять отзывы.
- В заголовке сайта можно осуществлять поиск объявлений по названию.
- Показывать и сохранять картинки объявлений.

___

## ТЗ на разработку

[Task](https://skyengpublic.notion.site/64113e0a2641475c9ad9bea93144afff)
___

## Запуск проекта

1. Склонировать проект
2. Изменить параметры доступа к базе данных в файлах (application.properties)
3. Запустить фронт через докер контейнер командой: docker run --rm -p 3000:3000 ghcr.io/bizinmitya/front-react-avito:
   v1.19

___

## Технологии используемые в проекте

- Spring Boot
- Spring Web
- Spring Data
- Spring JPA
- Spring Security
- Java
- Maven
- GIT
- REST
- Swagger
- Lombok
- Stream API
___

## Разработчик 
- [Коренев Владимир](https://github.com/C0sm0Black)
