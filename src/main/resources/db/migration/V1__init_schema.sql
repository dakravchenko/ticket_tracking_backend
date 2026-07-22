
CREATE EXTENSION IF NOT EXISTS "pgcrypto";


CREATE TYPE status AS ENUM ('open', 'in_progress', 'closed');


CREATE TABLE users (
    user_id   UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    name      TEXT        NOT NULL,
    email     TEXT        NOT NULL UNIQUE
);


CREATE TABLE projects (
    project_id UUID   PRIMARY KEY DEFAULT gen_random_uuid(),
    name       TEXT   NOT NULL UNIQUE
);


CREATE TABLE tickets (
    ticket_id   UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    title       TEXT        NOT NULL,
    description TEXT,
    project_id  UUID        NOT NULL REFERENCES projects(project_id),
    status      status      NOT NULL DEFAULT 'open',
    created_at  TIMESTAMP   NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP
);

CREATE INDEX idx_ticket_status     ON tickets(status);
CREATE INDEX idx_ticket_project_id ON tickets(project_id);


CREATE TABLE ticket_assignment (
    ticket_id   UUID        NOT NULL REFERENCES tickets(ticket_id),
    user_id     UUID        NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    assigned_at TIMESTAMP   NOT NULL DEFAULT NOW(),
    PRIMARY KEY (ticket_id, user_id)
);