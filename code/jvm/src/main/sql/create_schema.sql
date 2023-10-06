CREATE TABLE IF NOT EXISTS boards (
    id INTEGER UNIQUE PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS users (
    id  SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    token VARCHAR(36) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS games (
     id  VARCHAR(36) UNIQUE PRIMARY KEY,
     board INTEGER NOT NULL REFERENCES boards(id) ON DELETE CASCADE,
     name VARCHAR(255) NOT NULL,
     state VARCHAR(8) NOT NULL CHECK (state IN ('ACTIVE', 'FINISHED'))
);

CREATE TABLE IF NOT EXISTS game_users (
     game VARCHAR(36) NOT NULL REFERENCES games(id) ON DELETE CASCADE,
     player_white INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
     player_black INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
     PRIMARY KEY (game, player_white, player_black)
);