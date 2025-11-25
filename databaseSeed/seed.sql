CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- DROP TABLE IF EXISTS studyAppUser;
-- DROP TABLE IF EXISTS studyAppEmailStatus;

CREATE TABLE IF NOT EXISTS studyAppUser (
    Id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    accountEnabled BOOLEAN DEFAULT FALSE,
    password TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS studyAppEmailStatus (
    Id SERIAL PRIMARY KEY,
    emailToken VARCHAR(255) NOT NULL,
    emailVerified BOOLEAN DEFAULT FALSE,
    userId UUID NOT NULL,
    CONSTRAINT userIdFk FOREIGN KEY(userId) REFERENCES studyAppUser(Id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS gameSession (
    Id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    sessionName VARCHAR(255) NOT NULL,
    gameStatus VARCHAR(255) NOT NULL,
    gameType VARCHAR(255) NOT NULL,
    userId UUID NOT NULL,
    CONSTRAINT userIdFk FOREIGN KEY(userId) REFERENCES studyAppUser(Id) ON DELETE CASCADE
);

INSERT INTO studyAppUser (username, email, accountEnabled, password)
VALUES
('testuser', 'testemail@gmail.com', true, '1222223');