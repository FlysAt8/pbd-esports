-- 1. Таблица команд
CREATE TABLE teams (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 2. Таблица игроков
CREATE TABLE players (
    id BIGSERIAL PRIMARY KEY,
    nickname VARCHAR(255) NOT NULL UNIQUE,
    real_name VARCHAR(255),
    role VARCHAR(50),
    team_id BIGINT REFERENCES teams(id) ON DELETE SET NULL
);

-- 3. Таблица турниров
CREATE TABLE tournaments (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    start_date DATE NOT NULL,
    prize_pool NUMERIC(10, 2)
);

-- 4. Таблица регламентов
CREATE TABLE regulations (
    tournament_id BIGINT PRIMARY KEY REFERENCES tournaments(id) ON DELETE CASCADE,
    rules_text TEXT NOT NULL,
    max_teams INTEGER NOT NULL
);

-- 5. Таблица заявок
CREATE TABLE applications (
    id BIGSERIAL PRIMARY KEY,
    team_id BIGINT NOT NULL REFERENCES teams(id),
    tournament_id BIGINT NOT NULL REFERENCES tournaments(id),
    status VARCHAR(50) NOT NULL, -- PENDING, APPROVED, REJECTED
    seed_number INTEGER,
    applied_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_team_tournament UNIQUE (team_id, tournament_id)
);

-- Тестовые данные
INSERT INTO teams (name) VALUES ('Team Liquid');
INSERT INTO players (nickname, real_name, role, team_id) VALUES ('Stewie2K', 'Jacky Yip', 'Rifler', 1);
INSERT INTO players (nickname, real_name, role, team_id) VALUES ('NAF', 'Keith Markovic', 'Rifler', 1);

INSERT INTO tournaments (title, start_date, prize_pool) VALUES ('IEM Katowice', '2026-02-01', 1000000.00);
INSERT INTO tournaments (title, start_date, prize_pool) VALUES ('ESL Pro League', '2026-03-15', 850000.00);

INSERT INTO applications (team_id, tournament_id, status, seed_number) VALUES (1, 1, 'APPROVED', 1);