CREATE TABLE users
(
    id              bigserial    NOT NULL,
    email           varchar(100) NOT NULL,
    login           varchar(100) NOT NULL,
    password        varchar(100) NOT NULL,
    is_enabled      boolean,
    is_activated    boolean,
    activation_code varchar(100),
    created_at      timestamp DEFAULT (current_timestamp),
    updated_at      timestamp DEFAULT (current_timestamp),
    CONSTRAINT users_pkey PRIMARY KEY (id)
);

CREATE TABLE profiles
(
    user_id      bigint,
    display_name varchar(100),
    telegram     varchar(100),
    created_at   timestamp DEFAULT (current_timestamp),
    updated_at   timestamp DEFAULT (current_timestamp),
    CONSTRAINT fk_user_profile_id FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE boards
(
    id         bigserial    NOT NULL,
    board_name varchar(100) NOT NULL,
    board_desc text,
    created_at timestamp DEFAULT (current_timestamp),
    updated_at timestamp DEFAULT (current_timestamp),
    CONSTRAINT boards_pkey PRIMARY KEY (id)
);

CREATE TABLE sprints
(
    id            bigserial NOT NULL,
    board_id      bigint    NOT NULL,
    sprint_number int       NOT NULL,
    start_date    date,
    end_date      date,
    created_at    timestamp DEFAULT (current_timestamp),
    updated_at    timestamp DEFAULT (current_timestamp),
    CONSTRAINT sprints_pkey PRIMARY KEY (id),
    CONSTRAINT fk_sprint_board_id FOREIGN KEY (board_id) REFERENCES boards (id)
);

CREATE TABLE tasks
(
    id          bigserial NOT NULL,
    board_id    bigint                NOT NULL,
    name        text,
    description text,
    status      varchar(25),
    priority    varchar(25),
    type        varchar(25),
    deadline    date,
    assignee_id bigint                NOT NULL,
    creator_id  bigint                NOT NULL,
    created_at  timestamp DEFAULT (current_timestamp),
    updated_at  timestamp DEFAULT (current_timestamp),
    CONSTRAINT tasks_pkey PRIMARY KEY (id),
    CONSTRAINT fk_board_task_id FOREIGN KEY (board_id) REFERENCES boards (id),
    CONSTRAINT fk_task_assignee_id FOREIGN KEY (assignee_id) REFERENCES users (id),
    CONSTRAINT fk_task_creator_id FOREIGN KEY (creator_id) REFERENCES users (id)
);

CREATE TABLE invites
(
    id          bigserial    NOT NULL,
    email       varchar(100) NOT NULL,
    invite_code varchar(64)  NOT NULL,
    board_id    bigint       NOT NULL,
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp,
    CONSTRAINT invite_pkey PRIMARY KEY (id),
    CONSTRAINT fk_board_invite FOREIGN KEY (board_id) REFERENCES boards (id)
);

CREATE TABLE tags
(
    id         bigserial NOT NULL,
    tag_name   varchar(50)           NOT NULL,
    created_at timestamp DEFAULT (current_timestamp),
    updated_at timestamp DEFAULT (current_timestamp),
    CONSTRAINT tags_pkey PRIMARY KEY (id)
);

CREATE TABLE task_activities
(
    id            bigserial NOT NULL,
    task_id       bigint,
    author_id     bigint,
    status        varchar(25),
    activity_info text,
    created_at    timestamp DEFAULT (current_timestamp),
    updated_at    timestamp DEFAULT (current_timestamp),
    CONSTRAINT task_activities_pkey PRIMARY KEY (id),
    CONSTRAINT fk_task_activities_id FOREIGN KEY (task_id) REFERENCES tasks (id),
    CONSTRAINT fk_task_activities_author_id FOREIGN KEY (author_id) REFERENCES users (id)
);

CREATE TABLE task_comments
(
    id           bigserial NOT NULL,
    task_id      bigint,
    author_id    bigint,
    comment_text text,
    created_at   timestamp DEFAULT (current_timestamp),
    updated_at   timestamp DEFAULT (current_timestamp),
    CONSTRAINT task_comments_pkey PRIMARY KEY (id),
    CONSTRAINT fk_task_comment_id FOREIGN KEY (task_id) REFERENCES tasks (id),
    CONSTRAINT fk_task_comment_author_id FOREIGN KEY (author_id) REFERENCES users (id)
);

CREATE TABLE roles
(
    id         bigserial NOT NULL,
    role_name  varchar(100) UNIQUE   NOT NULL,
    role_desc  varchar(100) UNIQUE   NOT NULL,
    created_at timestamp DEFAULT (current_timestamp),
    updated_at timestamp DEFAULT (current_timestamp),
    CONSTRAINT roles_pkey PRIMARY KEY (id)
);

CREATE TABLE board_roles
(
    id         bigserial NOT NULL,
    role_name  varchar(100) UNIQUE   NOT NULL,
    role_desc  varchar(100) UNIQUE   NOT NULL,
    board_id   bigint                NOT NULL,
    created_at timestamp DEFAULT (current_timestamp),
    updated_at timestamp DEFAULT (current_timestamp),
    CONSTRAINT board_roles_pkey PRIMARY KEY (id),
    CONSTRAINT fk_board_roles_id FOREIGN KEY (board_id) REFERENCES boards (id)
);

CREATE TABLE tasks_sprints
(
    tasks_id   bigint,
    sprints_id bigint,
    CONSTRAINT tasks_sprints_pkey PRIMARY KEY (tasks_id, sprints_id),
    CONSTRAINT fk_task_sprint_id FOREIGN KEY (tasks_id) REFERENCES tasks (id),
    CONSTRAINT fk_sprint_task_id FOREIGN KEY (sprints_id) REFERENCES sprints (id)
);

CREATE TABLE tags_tasks
(
    tags_id  bigint,
    tasks_id bigint,
    CONSTRAINT tags_tasks_pkey PRIMARY KEY (tags_id, tasks_id),
    CONSTRAINT fk_tag_task_id FOREIGN KEY (tags_id) REFERENCES tags (id),
    CONSTRAINT fk_task_tag_id FOREIGN KEY (tasks_id) REFERENCES tasks (id)
);

CREATE TABLE users_boards
(
    users_id  bigint,
    boards_id bigint,
    CONSTRAINT users_boards_pkey PRIMARY KEY (users_id, boards_id),
    CONSTRAINT fk_user_board_id FOREIGN KEY (users_id) REFERENCES users (id),
    CONSTRAINT fk_board_user_id FOREIGN KEY (boards_id) REFERENCES boards (id)
);

CREATE TABLE users_roles
(
    users_id bigint,
    roles_id bigint,
    CONSTRAINT users_roles_pkey PRIMARY KEY (users_id, roles_id),
    CONSTRAINT fk_user_roles_id FOREIGN KEY (users_id) REFERENCES users (id),
    CONSTRAINT fk_roles_user_id FOREIGN KEY (roles_id) REFERENCES roles (id)
);

CREATE TABLE board_roles_users
(
    board_roles_id bigint,
    users_id       bigint,
    CONSTRAINT board_roles_users_pkey PRIMARY KEY (board_roles_id, users_id),
    CONSTRAINT fk_board_roles_user_id FOREIGN KEY (board_roles_id) REFERENCES board_roles (id),
    CONSTRAINT fk_board_user_roles_id FOREIGN KEY (users_id) REFERENCES users (id)
);