CREATE TYPE file_status AS ENUM ('pending', 'completed', 'failed');

ALTER TABLE files
ADD COLUMN status file_status NOT NULL DEFAULT 'pending';