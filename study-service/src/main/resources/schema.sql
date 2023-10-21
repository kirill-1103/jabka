begin;
DROP TABLE IF EXISTS professor_id CASCADE;
DROP TABLE IF EXISTS subject CASCADE;
DROP TABLE IF EXISTS study_groups CASCADE;
DROP TABLE IF EXISTS schedule CASCADE;
DROP TABLE IF EXISTS study_materials CASCADE;
DROP TABLE IF EXISTS homework CASCADE;
DROP TABLE IF EXISTS files CASCADE;
DROP TABLE IF EXISTS materials_files CASCADE;
DROP TABLE IF EXISTS group_subject CASCADE;
DROP TABLE IF EXISTS editor_subject CASCADE;
DROP TABLE IF EXISTS professor_subject CASCADE;
DROP TABLE IF EXISTS group_subject CASCADE;
DROP TABLE IF EXISTS attendance CASCADE;


CREATE TABLE IF NOT EXISTS professor_id
(
    professor_id SERIAL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS subject
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(1024),
    creator_id BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS study_groups
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    CONSTRAINT unq_name
        UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS schedule
(
    id                     SERIAL PRIMARY KEY,
    date_time              TIMESTAMP(6) NOT NULL,
    group_id               INTEGER      NOT NULL,
    professor_professor_id INTEGER      NOT NULL,
    subject_id             INTEGER      NOT NULL,
    auditorium             VARCHAR(255),
    class_format           VARCHAR(255) NOT NULL,
    link_for_the_class     VARCHAR(1024),
    CONSTRAINT fk_group
        FOREIGN KEY (group_id)
            REFERENCES study_groups (id)
            ON DELETE CASCADE,
    CONSTRAINT fk_professor_id
        FOREIGN KEY (professor_professor_id)
            REFERENCES professor_id (professor_id)
            ON DELETE CASCADE,
    CONSTRAINT fk_subject
        FOREIGN KEY (subject_id)
            REFERENCES subject (id)
            ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS study_materials
(
    id             SERIAL PRIMARY KEY,
    subject_id     INTEGER NOT NULL,
    type           VARCHAR(255),
    materials_text TEXT,
    CONSTRAINT fk_subject
        FOREIGN KEY (subject_id)
            REFERENCES subject (id)
            ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS homework
(
    id         SERIAL PRIMARY KEY,
    date       TIMESTAMP(6) NOT NULL,
    grade      BIGINT,
    student_id BIGINT       NOT NULL,
    task_id    INTEGER REFERENCES study_materials (id) ON DELETE CASCADE,
    comment    TEXT
);

CREATE TABLE IF NOT EXISTS files
(
    id           SERIAL PRIMARY KEY,
    initial_name VARCHAR(1024)        NOT NULL,
    name_s3      VARCHAR(1024) UNIQUE NOT NULL,
    homework_id  INTEGER REFERENCES homework (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS materials_files
(
    materials_id INTEGER REFERENCES study_materials (id) ON UPDATE CASCADE ON DELETE CASCADE,
    file_id      INTEGER REFERENCES files (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT materials_files_pkey
        PRIMARY KEY (materials_id, file_id)
);

CREATE TABLE IF NOT EXISTS group_subject
(
    subject_id       INTEGER REFERENCES subject (id) ON UPDATE CASCADE ON DELETE CASCADE,
    study_group_name VARCHAR(255) UNIQUE REFERENCES study_groups (name) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT group_subject_pkey
        PRIMARY KEY (subject_id, study_group_name)
);

CREATE TABLE IF NOT EXISTS professor_subject
(
    subject_id   INTEGER REFERENCES subject (id) ON UPDATE CASCADE ON DELETE CASCADE,
    professor_id INTEGER REFERENCES professor_id (professor_id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT professor_subject_pkey
        PRIMARY KEY (subject_id, professor_id)
);

CREATE TABLE IF NOT EXISTS editor_subject
(
    subject_id INTEGER REFERENCES subject (id) ON UPDATE CASCADE ON DELETE CASCADE,
    editor_id  INTEGER REFERENCES professor_id (professor_id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT subject_editor_pkey
        PRIMARY KEY (subject_id, editor_id)
);


CREATE TABLE IF NOT EXISTS attendance
(
    id                SERIAL PRIMARY KEY,
    attendance_status VARCHAR(255) NOT NULL,
    student_id        BIGINT       NOT NULL,
    schedule_id       INTEGER REFERENCES schedule (id) ON DELETE CASCADE
);


end;