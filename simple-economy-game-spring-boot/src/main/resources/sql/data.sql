CREATE DATABASE simple_economy_game_mysql;
USE simple_economy_game_mysql;
CREATE TABLE user
(
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role     VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE building
(
    id   BIGINT NOT NULL AUTO_INCREMENT,
    type VARCHAR(255),
    user BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user) REFERENCES user (id) ON DELETE CASCADE
);

CREATE TABLE resource
(
    id     BIGINT NOT NULL AUTO_INCREMENT,
    type   VARCHAR(255),
    amount BIGINT,
    user   BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user) REFERENCES user (id) ON DELETE CASCADE
);

CREATE TABLE unit
(
    id     BIGINT NOT NULL AUTO_INCREMENT,
    type   VARCHAR(255),
    amount BIGINT,
    active BIT,
    user   BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user) REFERENCES user (id) ON DELETE CASCADE
);
