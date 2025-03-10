# Contact List Application (`Список контактов`)

## Описание
Простое веб-приложение для управления контактами, реализованное с помощью **Spring MVC**, **Spring Data JDBC**, **Thymeleaf** и базы данных. Позволяет добавлять, редактировать, удалять и просматривать контакты через веб-интерфейс.

## Функциональность
- **Просмотр контактов:** Таблица с полями `ID`, `Имя`, `Фамилия`, `Почта`, `Телефон`.
- **Добавление нового контакта:** Через форму (ID генерируется автоматически).
- **Редактирование контакта:** Изменение данных через форму (ID не изменяется).
- **Удаление контакта:** По нажатию кнопки.

## Используемые технологии
- **Spring MVC** для обработки запросов.
- **Spring Data JDBC** и **JdbcTemplate** для работы с базой данных.
- **Thymeleaf** для отображения HTML.
- **Docker** для настройки базы данных.

## Настройка базы данных
Используется **PostgreSQL** через Docker. 

## Как запустить
- Запустите базу данных с помощью Docker:
```
docker-compose up -d
```

- Настройте подключение в application.properties:
```
spring.datasource.url=jdbc:postgresql://localhost:5432/contacts_db
spring.datasource.username=user
spring.datasource.password=password
```

- Соберите и запустите проект

- Откройте приложение в браузере: http://localhost:8080.
