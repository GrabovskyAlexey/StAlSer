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

INSERT INTO restrictions (restriction_name, restriction_desc)
VALUES ('view_board', 'Просмотр доски'),
       ('view_task', 'Просмотр задачи'),
       ('change_task_status', 'Изменение статуса задачи'),
       ('create_comment_to_task', 'Создание комментария к задаче'),
       ('add_tag_to_task', 'Добавление тега к задаче'),
       ('appoint_yourself_as_performer', 'Назначать себя исполнителем'),
       ('create_task', 'Создание задачи'),
       ('edit_task', 'Редактирование задачи'),
       ('change_task_performer', 'Изменение исполнителя задачи'),
       ('mark_task_as_completed', 'Помечать задачу как <Выполнено>'),
       ('edit_comment_to_task', 'Редактирование комментария к задаче'),
       ('delete_comment', 'Удаление комментария'),
       ('create_sprint', 'Создание спринта'),
       ('edit_sprint', 'Редактирование спринта'),
       ('delete_sprint', 'Удаление спринта'),
       ('create_tag', 'Создание тега'),
       ('edit_tag', 'Редактирование тега'),
       ('delete_tag', 'Удаление тега'),
       ('add_users_on_board', 'Добавление пользователей на доску'),
       ('remove_users_from_board', 'Удаление пользователей с доски'),
       ('change_board_name', 'Изменение названия доски'),
       ('change_board_description', 'Изменение описания доски');

INSERT INTO board_roles(role_name, role_desc)
VALUES ('reporter', 'Наблюдатель'),
       ('user', 'Пользователь'),
       ('manager', 'Менеджер'),
       ('admin', 'Администратор');

INSERT INTO board_roles_binding_restriction(board_role_id, restriction_id)
VALUES (1, 1), (1, 2),
       (2, 1), (2, 2), (2, 3), (2, 4), (2, 5), (2, 6),
       (3, 1), (3, 2), (3, 3), (3, 4), (3, 5), (3, 6), (3, 7), (3, 8), (3, 9), (3, 10), (3, 11), (3, 12), (3, 13), (3, 14), (3, 15), (3, 16), (3, 17), (3, 18), (3, 19),
       (4, 1), (4, 2), (4, 3), (4, 4), (4, 5), (4, 6), (4, 7), (4, 8), (4, 9), (4, 10), (4, 11), (4, 12), (4, 13), (4, 14), (4, 15), (4, 16), (4, 17), (4, 18), (4, 19), (4, 20), (4, 21), (4, 22);

CREATE TABLE board_binding_board_role_binding_user
(
    board_id  bigint,
    board_role_id bigint,
    user_id bigint,
    CONSTRAINT board_binding_board_role_binding_user_pkey PRIMARY KEY (board_id, user_id),
    CONSTRAINT fk_board_id FOREIGN KEY (board_id) REFERENCES boards (id),
    CONSTRAINT fk_board_role_id FOREIGN KEY (board_role_id) REFERENCES board_roles (id),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users (id)
);
insert into users_boards (users_id, boards_id)
values (1, 2), (1, 3), (1, 4),
       (2, 3), (2, 4),
       (3, 1),
       (4, 2),
       (5, 1), (5, 2), (5, 3),
       (6, 4), (6, 1),
       (7, 2), (7, 4),
       (8, 2), (8, 3),
       (9, 4), (9, 1),
       (10, 1);

insert into board_binding_board_role_binding_user(board_id, board_role_id, user_id)
values (1, 1, 9),
       (1, 2, 3),
       (1, 3, 5),
       (1, 4, 6),
       (1, 1, 10);