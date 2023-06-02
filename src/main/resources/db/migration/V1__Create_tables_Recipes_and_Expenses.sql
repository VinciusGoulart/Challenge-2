CREATE TABLE tb_recipes (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    description MEDIUMTEXT,
    price FLOAT,
    date_time DATE
);

CREATE TABLE tb_expenses (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    description MEDIUMTEXT,
    price FLOAT,
    date_time DATE
);