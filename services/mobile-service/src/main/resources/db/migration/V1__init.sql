CREATE EXTENSION IF NOT EXISTS pgcrypto;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";



CREATE TABLE IF NOT EXISTS public."user"
(
    id            UUID PRIMARY KEY NOT NULL,
    email         bytea UNIQUE     NOT NULL,
    password      bytea UNIQUE     NOT NULL,
    mobile_number bytea UNIQUE     NOT NULL,
    created       TIMESTAMP        NOT NULL
);

