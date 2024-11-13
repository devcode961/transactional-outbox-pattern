CREATE TABLE IF NOT EXISTS task
(
    id
    UUID
    NOT
    NULL,
    name
    VARCHAR
(
    255
),
    CONSTRAINT pk_task PRIMARY KEY
(
    id
)
    );