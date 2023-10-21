CREATE SEQUENCE user_seq START 1;
CREATE SEQUENCE applicant_request_seq START 1;
CREATE SEQUENCE token_seq START 1;

CREATE TABLE IF NOT EXISTS roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE
);

CREATE TABLE IF NOT EXISTS users (
    id BIGINT DEFAULT nextval('user_seq') NOT NULL PRIMARY KEY,
    login VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    patronymic VARCHAR(255),
    photo_path VARCHAR(255),
    group_number VARCHAR(255),
    activation_status VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT,
    role_id BIGINT,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE IF NOT EXISTS applicant_request (
    id BIGINT DEFAULT nextval('applicant_request_seq') NOT NULL PRIMARY KEY,
    leader_name VARCHAR(255) NOT NULL,
    subunit_name VARCHAR(255) NOT NULL,
    current_position VARCHAR(255) NOT NULL,
    work_experience VARCHAR(255) NOT NULL,
    personal_achievements VARCHAR(255) NOT NULL,
    motivation_message VARCHAR(255) NOT NULL,
    request_status VARCHAR(255),
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS activation_token (
    id BIGINT DEFAULT nextval('token_seq') NOT NULL PRIMARY KEY,
    token VARCHAR(255),
    expiration_date TIMESTAMP,
    user_id BIGINT,
    token_type VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

