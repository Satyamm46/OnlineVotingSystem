create DATABASE online_voting;
USE online_voting;

CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    
    role VARCHAR(20) NOT NULL DEFAULT 'ROLE_USER',
    has_voted BOOLEAN NOT NULL DEFAULT FALSE,

    aadhaar_number VARCHAR(255) UNIQUE NOT NULL,  -- encrypted
    otp VARCHAR(10),
    otp_verified BOOLEAN DEFAULT FALSE,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE leaders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    party VARCHAR(100),
    achievements TEXT,
    experience INT DEFAULT 0,
    education VARCHAR(100),
    corruption_cases INT DEFAULT 0,
    votes INT DEFAULT 0
);

CREATE TABLE votes (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    leader_id BIGINT NOT NULL,
    voted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_leader
        FOREIGN KEY (leader_id)
        REFERENCES leaders(id)
        ON DELETE CASCADE,

    UNIQUE(user_id)  -- ensures 1 vote per user
);

ALTER TABLE leaders
ADD COLUMN logo VARCHAR(255);

select *from users;

UPDATE users
SET role = 'ROLE_ADMIN'
WHERE email = 'sknink4@gmail.com';