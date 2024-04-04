# Reeste - сервис для бюджетирования

## Инфраструктура
### Keycloak

В проекте используется как OAuth 2.0/OIDC-сервер для авторизации сервисов и аутентификации пользователей.

Запуск в Docker:

```shell
docker run --name reeste-keycloak -p 8082:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin -v ./config/keycloak/import:/opt/keycloak/data/import quay.io/keycloak/keycloak:23.0.7 start-dev --import-realm
```

### PostgreSQL

В проекте используется в качестве БД модуля бюджетов.

Запуск в Docker:

```shell
docker run --name budget-db -p 5432:5432 -e POSTGRES_USER=reeste -e POSTGRES_PASSWORD=oY0uZGukMr -e POSTGRES_DB=reeste postgres:16
```

### MongoDB

В проекте используется в качестве БД модуля рецензирования бюджетов.

Запуск в Docker:

```shell
docker run --name approval-db -p 27017:27017 mongo:7
```
