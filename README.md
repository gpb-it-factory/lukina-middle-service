# Миддл сервис для lukina-telegram-bot

* Принимает запросы от tg-бота
* Выполняет валидацию и бизнес-логику
* Маршрутизирует запросы в backend

## Стек

Java

## Быстрый старт

```bash
./gradlew build
./gradlew bootRun
```

## Схема работы

```plantuml
@startuml
actor Client
participant Frontend
participant Middle
participant Backend

Client -> Frontend: команда start
Frontend -> Client: список команд
Client -> Frontend: валидная команда
Frontend -> Middle: HTTP-запрос
alt Запрос валиден
    Middle -> Backend: HTTP-запрос
else Запрос невалиден
    Middle -> Frontend: Ошибка
end
Backend -> Middle: Данные
Middle -> Frontend: Обработанные данные
Frontend -> Client: Текстовый ответ
@enduml
```

## Автор
devmargooo@gmail.com

