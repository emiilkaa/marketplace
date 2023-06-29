# Marketplace

## Как запустить
Можно воспользоваться Docker Compose (запустить команду ```docker-compose up``` в папке ```src/main/docker```), предварительно поменяв пароль от БД в файле ```docker-compose.yml```. Этот скрипт автоматически поднимет необходимую для сервиса БД (PostgreSQL) и сам сервис, слушающий запросы на порте 8080.

## Доступные ручки
- Добавление нового товара. ```POST http://{host}:{port}/product```, принимает тело запроса в JSON-формате:
```
{
    "name": "Наименование товара",
    "description": "Описание товара (необязательное поле)",
    "available_quantity": 30,
    "price": 99.9
}
```
- Обновление информации о товаре. ```PATCH http://{host}:{port}/product```, принимает тело запроса в JSON-формате (обязательное поле - только ```id```):
```
{
    "id": 1,
    "name": "Шоколад",
    "description": "Молочный шоколад",
    "available_quantity": 29,
    "price": 90
}
```
- Получение информации о товаре. ```GET http://{host}:{port}/product/{product_id}```
- Удаление товара. ```DELETE http://{host}:{port}/product/{product_id}```
-----
- Добавение нового отзыва. ```POST http://{host}:{port}/review/{product_id}```, принимает тело запроса в JSON-формате (rating - целое число от 1 до 5):
```
{
    "rating": 5,
    "message": "Текст отзыва (необязательное поле)"
}
```
- Изменение отзыва. ```PATCH http://{host}:{port}/review```, принимает тело запроса в JSON-формате (обязательное поле - только ```reviewId```):
```
{
    "reviewId": 1,
    "rating": 4,
    "message": "Нормально"
}
```
- Добавление медиафайла (ссылки) к отзыву. ```POST http://{host}:{port}/review/add_media```, принимает тело запроса в JSON-формате:
```
{
    "reviewId": 1,
    "mediaUrl": "https://example.com/image.jpg"
}
```
- Удаление медиафайла (ссылки). ```DELETE http://{host}:{port}/review/delete_media```, принимает тело запроса в JSON-формате:
```
{
    "reviewId": 1,
    "mediaUrl": "https://example.com/image.jpg"
}
```
