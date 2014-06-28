CREATE TABLE users (
    id          MEDIUMINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    email       VARCHAR(255) NOT NULL UNIQUE,
    password    CHAR(60),
    access      CHAR(16) NOT NULL DEFAULT 'student',
    reset_token CHAR(32)
);

CREATE TABLE quizzes (
    id          CHAR(36) PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    points      SMALLINT UNSIGNED,
    open        DATETIME NOT NULL DEFAULT '2000-01-01 00:00:00',
    due         DATETIME NOT NULL,
    data        TEXT
);

CREATE TABLE submissions (
    id          MEDIUMINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    userid      MEDIUMINT UNSIGNED,
    quizid      CHAR(36),
    status      CHAR(32) NOT NULL DEFAULT 'Submitted',
    grade       SMALLINT UNSIGNED DEFAULT 0,
    data        TEXT,
    FOREIGN KEY (userid)
        REFERENCES users(id)
        ON DELETE CASCADE,
    FOREIGN KEY (quizid)
        REFERENCES quizzes(id)
        ON DELETE CASCADE
);