CREATE TABLE files (
    file_id      UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    ticket_id    UUID NOT NULL REFERENCES tickets(ticket_id) ON DELETE CASCADE,

    file_name    TEXT NOT NULL,
    storage_key  TEXT NOT NULL UNIQUE,
    content_type TEXT,
    file_size    BIGINT NOT NULL,

    created_at   TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_files_ticket_id ON files(ticket_id);