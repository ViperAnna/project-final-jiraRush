## [REST API](http://localhost:8080/doc)

## Концепция:

- Spring Modulith
    - [Spring Modulith: достигли ли мы зрелости модульности](https://habr.com/ru/post/701984/)
    - [Introducing Spring Modulith](https://spring.io/blog/2022/10/21/introducing-spring-modulith)
    - [Spring Modulith - Reference documentation](https://docs.spring.io/spring-modulith/docs/current-SNAPSHOT/reference/html/)

```
  url: jdbc:postgresql://localhost:5432/jira
  username: jira
  password: JiraRush
```

- Есть 2 общие таблицы, на которых не fk
    - _Reference_ - справочник. Связь делаем по _code_ (по id нельзя, тк id привязано к окружению-конкретной базе)
    - _UserBelong_ - привязка юзеров с типом (owner, lead, ...) к объекту (таска, проект, спринт, ...). FK вручную будем
      проверять

## Аналоги

- https://java-source.net/open-source/issue-trackers

## Тестирование

- https://habr.com/ru/articles/259055/

Список выполненных задач:
- 2 Удалены социальные сети: vk, yandex.
- 3 Вынесена чувствительная информация:
  - логин
  - пароль БД
  - идентификаторы для OAuth регистрации/авторизации
  - настройки почты
- 4 Переделаны тесты так, чтобы во время тестов использовалась in memory БД (H2), а не PostgreSQL.
- 5 Написаны тесты для всех публичных методов контроллера ProfileRestController.
- 6 Сделан рефакторинг метода com.javarush.jira.bugtracking.attachment.FileUtil#upload, чтобы он
использовал современный подход для работы с файловой системмой.

- 9 Написан Dockerfile для основного сервера
- 10 Написан docker-compose файл для запуска контейнера сервера вместе с БД и nginx.
- 11 Добавлена локализация на en и cz языках для шаблонов писем (mails) и стартовой страницы index.html.




