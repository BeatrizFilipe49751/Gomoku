CREATE TABLE IF NOT EXISTS users (
    userId  SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_validation VARCHAR(256) not null
);

CREATE TABLE IF NOT EXISTS tokens(
    token_validation VARCHAR(256) primary key,
    user_id int references users(userId),
    created_at bigint not null,
    last_used_at bigint not null
);

CREATE TABLE IF NOT EXISTS lobby (
    lobbyId  SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    variant int NOT NULL,
    opening int NOT NULL,
    boardSize int NOT NULL,
    p1 INTEGER NOT NULL REFERENCES users(userId) ON DELETE CASCADE,
    p2 INTEGER REFERENCES users(userId) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS games (
    gameId  VARCHAR(36) UNIQUE PRIMARY KEY,
    board VARCHAR(2048) NOT NULL,
    name VARCHAR(255) NOT NULL,
    opening int NOT NULL,
    variant int NOT NULL,
    state CHAR NOT NULL CHECK (state IN ('A', 'F', 'D')),
    turn CHAR NOT NULL CHECK (turn IN ('b', 'w'))
);

CREATE TABLE IF NOT EXISTS game_users (
    game VARCHAR(36) NOT NULL REFERENCES games(gameId) ON DELETE CASCADE,
    game_number INTEGER UNIQUE NOT NULL,
    player_white INTEGER NOT NULL REFERENCES users(userId) ON DELETE CASCADE,
    player_black INTEGER NOT NULL REFERENCES users(userId) ON DELETE CASCADE,
    PRIMARY KEY (game, player_white, player_black)
);

CREATE TABLE IF NOT EXISTS leaderboard (
    userId INTEGER NOT NULL REFERENCES users(userId) ON DELETE CASCADE,
    username VARCHAR(255) NOT NULL,
    points INTEGER NOT NULL,
    PRIMARY KEY (userId)
)
