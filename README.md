# Тестовое задание на вакансию Backend-разработчик в компанию «Selsup»

## Суть задания
Реализовать thread-safe класс для работы с API Честного знака с ограничением на количество запросов к API

## Структура проекта
### Класс CrptApi
#### Поля:
- private final RateLimiter rateLimiter - переменная типа RateLimiter из библиотеки Guava для реализации ограничения на кол-во запросов к API\
#### Методы:
- public CrptApi(TimeUnit timeUnit, int requestLimit) - конструктор. В конструктор передаем timeUnit и requestLimit для инициализации rateLimiter
- public void sendRequest(JsonObject document, String signature) - сам метод для отправки POST-запроса. В метод передается JsonObject document - тело запроса и String signature - подпись

## Используемые библиотеки
- [Guava: Google Core Libraries For Java](https://mvnrepository.com/artifact/com.google.guava/guava/33.0.0-jre)
- [Gson](https://mvnrepository.com/artifact/com.google.code.gson/gson/2.10.1)
