# Домашнее задание: Киберспортивный турнир (Spring Boot + Hibernate + PostgreSQL)

## Студент
Федоров А.Д.

## Тема
Киберспортивный турнир (Вариант №18)

## Как поднять проект
1. Запустить Docker Desktop
2. В корне проекта выполните команду для запуска базы данных:
```bash
docker compose up -d
```
3. Запустите Spring Boot приложение (через IDE или командой `./mvnw spring-boot:run)`.
4. Приложение будет доступно на порту 8080. При старте Flyway автоматически создаст таблицы и наполнит их тестовыми данными (согласно `V1__init_schema.sql`).

## Таблица самопроверки

| Пункт задания | Где реализовано (Класс / Метод) |
| :--- | :--- |
| 1:N (двусторонняя) | `Team.java` (`@OneToMany`), `Player.java` (`@ManyToOne`). Вспомогательный метод `Team.addPlayer()`. |
| N:M (через сущность) | `Application.java` — связующая сущность с двумя `@ManyToOne` (на `Team` и `Tournament`) и своими атрибутами (`status`, `seedNumber`). |
| 1:1 | `Tournament.java` и `Regulation.java` (через `@MapsId`). Вспомогательный метод `Tournament.setRegulation()`. |
| Запрос из имени метода | `TeamRepository.findByName(String name)` |
| Native SQL запрос | `TournamentRepository.findRichTournaments(Double minPrize)` |
| JPQL запрос | `PlayerRepository.findPlayersByTeamName(String teamName)` |
| Criteria API (динамика) | `TournamentService.findTournamentsDynamic(...)` |
| Транзакции (`@Transactional`) | Все методы в `TournamentService`, `TeamService`, `ApplicationService`. |
| Демонстрация (REST) | `EsportsController` (эндпоинты `/api/...`) |

## Проверка работы

### 1. Проверка динамического запроса Criteria API (GET)
* **URL:** `http://localhost:8080/api/tournaments?minPrize=900000`
* **Ожидание:** Вернётся JSON с одним турниром (`IEM Katowice`), так как призовой фонд второго турнира меньше 900000.

### 2. Проверка загрузки ленивых связей через join fetch (GET)
* **URL:** `http://localhost:8080/api/teams/1/players`
* **Ожидание:** Вернётся JSON команды со списком игроков. Благодаря `JOIN FETCH` в `TeamRepository`, мы избежали `LazyInitializationException`.

### 3. Проверка транзакции и записи в БД (POST)
* **URL:** `http://localhost:8080/api/applications?teamId=1&tournamentId=2`
* **Метод:** POST
* **Ожидание:** В ответ вернётся JSON созданной заявки со статусом `PENDING`. Это доказывает, что транзакция `@Transactional` успешно открылась, изменила данные и закоммитила их в БД.