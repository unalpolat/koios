CREATE TABLE IF NOT EXISTS player (
    id IDENTITY NOT NULL PRIMARY KEY,
    name VARCHAR NOT NULL,
    year VARCHAR NOT NULL,
    team VARCHAR,
    birthday VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS team (
    id IDENTITY NOT NULL PRIMARY KEY,
    name VARCHAR NOT NULL,
    year VARCHAR NOT NULL,
    embedded_player_names VARCHAR NOT NULL,
    currency VARCHAR NOT NULL
);