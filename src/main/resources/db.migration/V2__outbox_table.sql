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
    outbox
    .
    task
(
    id
),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
                             sent_to_bus BOOLEAN NOT NULL
                             );
