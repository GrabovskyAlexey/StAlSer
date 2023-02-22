CREATE TABLE restrictions
(
    id               bigserial           NOT NULL,
    restriction_name varchar(256) UNIQUE NOT NULL,
    restriction_desc varchar(256) UNIQUE NOT NULL,
    created_at       timestamp DEFAULT (current_timestamp),
    updated_at       timestamp DEFAULT (current_timestamp),
    CONSTRAINT restrictions_pkey PRIMARY KEY (id)
);

CREATE TABLE board_roles_binding_restriction
(
    board_role_id  bigint,
    restriction_id bigint,
    CONSTRAINT board_role_restriction_pkey PRIMARY KEY (board_role_id, restriction_id),
    CONSTRAINT fk_board_role_restriction FOREIGN KEY (board_role_id) REFERENCES board_roles (id),
    CONSTRAINT fk_restriction_board_role FOREIGN KEY (restriction_id) REFERENCES restrictions (id)
);

CREATE TABLE board_roles_binding_board
(
    board_role_id bigint,
    board_id      bigint,
    CONSTRAINT board_role_board_pkey PRIMARY KEY (board_role_id, board_id),
    CONSTRAINT fk_board_role_board FOREIGN KEY (board_role_id) REFERENCES board_roles (id),
    CONSTRAINT fk_board_board_role FOREIGN KEY (board_id) REFERENCES boards (id)
);
ALTER TABLE board_roles
    DROP CONSTRAINT fk_board_roles_id,
    DROP COLUMN board_id;
