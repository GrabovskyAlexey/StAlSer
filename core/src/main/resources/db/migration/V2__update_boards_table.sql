ALTER TABLE boards
    ADD COLUMN IF NOT EXISTS board_alias varchar(10) NOT NULL DEFAULT 'board',
ADD COLUMN IF NOT EXISTS is_active boolean NOT NULL DEFAULT true,
ADD COLUMN IF NOT EXISTS creator_id bigint,
ADD CONSTRAINT fk_board_creator_id FOREIGN KEY (creator_id) REFERENCES users (id);