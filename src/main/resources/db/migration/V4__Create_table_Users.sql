CREATE TABLE tb_users (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(80),
    password VARCHAR(100),
    category VARCHAR(30) DEFAULT 'Others'
);