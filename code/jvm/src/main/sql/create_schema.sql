
CREATE TABLE IF NOT EXISTS users (
    id  SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    token VARCHAR(36) UNIQUE NOT NULL
);