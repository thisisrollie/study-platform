CREATE TABLE IF NOT EXISTS users
(
    id         BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(64)         NOT NULL,
    last_name  VARCHAR(64)         NOT NULL,
    email      VARCHAR(128) UNIQUE NOT NULL,
    password   VARCHAR(256)        NOT NULL,
    role       VARCHAR(32)         NOT NULL
);

-- CREATE TYPE user_role AS ENUM ('student', 'instructor', 'admin');

CREATE TABLE IF NOT EXISTS course
(
    id            BIGSERIAL PRIMARY KEY,
    title         VARCHAR(128) NOT NULL,
    description   TEXT,
    instructor_id BIGINT       NOT NULL REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS lesson
(
    id          BIGSERIAL PRIMARY KEY,
    title       VARCHAR(128) NOT NULL,
    description TEXT,
    "order"     INT          NOT NULL,
    course_id   BIGINT       NOT NULL REFERENCES course (id),
    UNIQUE (course_id, "order")
);

CREATE TABLE IF NOT EXISTS lesson_material
(
    id          BIGSERIAL PRIMARY KEY,
    content_url VARCHAR(256) NOT NULL,
    type        VARCHAR(32)  NOT NULL,
    "order"     INT          NOT NULL,
    lesson_id   BIGINT       NOT NULL REFERENCES lesson (id),
    UNIQUE (lesson_id, "order")
);

CREATE TABLE IF NOT EXISTS enrollment
(
    id         BIGSERIAL PRIMARY KEY,
    student_id BIGINT    NOT NULL REFERENCES users (id),
    course_id  BIGINT    NOT NULL REFERENCES course (id),
    started_at TIMESTAMP NOT NULL,
    UNIQUE (student_id, course_id)
);

CREATE TABLE IF NOT EXISTS quiz
(
    id          BIGSERIAL PRIMARY KEY,
    title       VARCHAR(256) NOT NULL,
    description TEXT,
    course_id   BIGINT       NOT NULL REFERENCES course (id)
);

CREATE TABLE IF NOT EXISTS question
(
    id      BIGSERIAL PRIMARY KEY,
    text    TEXT         NOT NULL,
    options JSON         NOT NULL,
    answer  VARCHAR(128) NOT NULL,
    quiz_id BIGINT       NOT NULL REFERENCES quiz (id)
);

CREATE TABLE IF NOT EXISTS quiz_attempt
(
    id           BIGSERIAL PRIMARY KEY,
    student_id   BIGINT    NOT NULL REFERENCES users (id),
    quiz_id      BIGINT    NOT NULL REFERENCES quiz (id),
    score        INT       NOT NULL,
    attempted_at TIMESTAMP NOT NULL
);

-- DO
-- $$
--     DECLARE
--         r RECORD;
--     BEGIN
--         FOR r IN (SELECT tablename FROM pg_tables WHERE schemaname = 'public')
--             LOOP
--                 EXECUTE 'DROP TABLE IF EXISTS ' || quote_ident(r.tablename) || ' CASCADE';
--             END LOOP;
--     END
-- $$;
