ALTER TABLE invites
    ADD COLUMN IF NOT EXISTS status varchar(20) NOT NULL DEFAULT 'SENT',
    ADD COLUMN IF NOT EXISTS expiration_date timestamp,
    ADD COLUMN IF NOT EXISTS user_id bigint,
ADD CONSTRAINT fk_invite_user_id FOREIGN KEY (user_id) REFERENCES users (id);