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

CREATE TABLE IF NOT EXISTS task_outbox
(
    id
    UUID
    NOT
    NULL,
    task_id
    UUID
    NOT
    NULL
    REFERENCES
    task
(
    id
),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
                             sent_to_bus BOOLEAN NOT NULL
                             );

CREATE TABLE IF NOT EXISTS shedlock
(
    name
    VARCHAR
(
    64
) NOT NULL,
    lock_until TIMESTAMP NOT NULL,
    locked_at TIMESTAMP NOT NULL,
    locked_by VARCHAR
(
    255
) NOT NULL,
    PRIMARY KEY
(
    name
)
    );



