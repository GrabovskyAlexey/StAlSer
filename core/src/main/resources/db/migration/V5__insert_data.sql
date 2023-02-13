insert into users (email, login, password)
values ('Ivan@gmail.com', 'Ivan', '$2a$12$7FXCEUFEMFYip9dhrA.MEeFXDo9ZzwtGk1q7Todqmnpupjk73a32C'),
       ('Lesha@gmail.com', 'Lesha', '$2a$12$BVQ3qMrmGbHGkWiSgoNQ2eYMgy2kI3rs3Dy5t44eyqQLp3xBIvSQK'),
       ('Mary@gmail.com', 'Mary', '$2a$12$.88RmaU5xMi8aqfYI0ge9uKa8bCIZPerAzCswCRejYVSUvmTA0jSS'),
       ('Dasha@gmail.com', 'Dasha', '$2a$12$fH9ud2EGnwwwTyw/ZBqTbO5fT43i.pXfKOtwwD.isyYB2lSWntWvq'),
       ('Alex@gmail.com', 'Alex', '$2a$12$oEExJRVwzWhDmlKK0Ek5Ee6a/DfwTbertBcKJ4NUFK9yCalxnpEIe'),
       ('Stas@gmail.com', 'Stas', '$2a$12$IBsnVW703w8wxhZ7AYjqpeWs8nx2M8pxAsM/oe0gufe8jNvOs1Q6.'),
       ('Vasilisa@gmail.com', 'Vasilisa', '$2a$12$pMhNadeqcJ4uWDGQnnoMx.lPI7ySquin/yLtl.7RrJG.E.v3xH4T.'),
       ('Tema@gmail.com', 'Tema', '$2a$12$H7k.Cu3y9F2Uq/CG7QxsXOw21by8hQ3zmshhE3bkb9cJ5eWV5vOrm'),
       ('Teodor@gmail.com', 'Teodor', '$2a$12$LvDdDm7qxghtS6GeWGKLEO8DAi1gNyMhQxwDz0i0/.XpTJ/pZefTa');
--login: Ivan, password: 100
--login: Lesha, password: 200
--login: Mary, password: 300
--login: Dasha, password: 400
--login: Alex, password: 500
--login: Stas, password: 600
--login: Vasilisa, password: 700
--login: Tema, password: 800
--login: Teodor, password: 900

insert into profiles (user_id, display_name, telegram)
values (1, 'Сергей', '@Sergey'),
       (2, 'Иван', '@Ivan'),
       (3, 'Алексей', '@Lesha'),
       (4, 'Мария', '@Mary'),
       (5, 'Дарья', '@Dasha'),
       (6, 'Александр', '@Alex'),
       (7, 'Станислав', '@Stas'),
       (8, 'Василиса', '@Vasilisa'),
       (9, 'Тема', '@Tema'),
       (10, 'Теодор', '@Teodor');

insert into users_roles (users_id, roles_id)
values (1, 2),
       (2, 1),
       (3, 1),
       (3, 2),
       (4, 1),
       (5, 1),
       (6, 1),
       (7, 1),
       (7, 2),
       (8, 1),
       (9, 1),
       (9, 2),
       (10, 1);

insert into boards(board_name, board_desc, board_alias, creator_id)
VALUES ('Проект1', 'Тестовый проект', 'test1', 1),
       ('Проект2', 'Тестовый проект', 'test2', 3),
       ('Проект3', 'Тестовый проект', 'test3', 7),
       ('Проект4', 'Тестовый проект', 'test4', 9);

insert into tasks(board_id, name, description, status, priority, type, deadline, assignee_id, creator_id)
VALUES (1, 'Задача 1', 'Описание задача 1', 'IN_WORK', 'MINOR', 'TASK', '2023-03-01', 5, 1),
       (2, 'Задача 2', 'Описание задача 2', 'IN_REVIEW', 'BLOCKER', 'IMPROVEMENT', '2023-03-01', 4, 1),
       (3, 'Задача 3', 'Описание задача 3', 'RESOLVE', 'MINOR', 'FEATURE', '2023-03-01', 1, 3),
       (4, 'Задача 4', 'Описание задача 4', 'CLOSE', 'TRIVIAL', 'NEW_FEATURE', '2023-03-01', 7, 1),
       (1, 'Задача 5', 'Описание задача 5', 'BACKLOG', 'MAJOR', 'FEATURE', '2023-03-01', 6, 7),
       (2, 'Задача 6', 'Описание задача 6', 'TODO', 'MINOR', 'TASK', '2023-03-01', 2, 3),
       (3, 'Задача 7', 'Описание задача 7', 'NEW', 'CRITICAL', 'FEATURE', '2023-03-01', 9, 1),
       (4, 'Задача 8', 'Описание задача 8', 'IN_WORK', 'TRIVIAL', 'TASK', '2023-03-01', 10, 3),
       (1, 'Задача 9', 'Описание задача 9', 'BACKLOG', 'BLOCKER', 'IMPROVEMENT', '2023-03-01', 8, 9),
       (2, 'Задача 10', 'Описание задача 10', 'IN_REVIEW', 'MAJOR', 'TASK', '2023-03-01', 2, 9),
       (3, 'Задача 11', 'Описание задача 11', 'CLOSE', 'CRITICAL', 'BUG', '2023-03-01', 3, 9),
       (4, 'Задача 12', 'Описание задача 12', 'IN_REVIEW', 'TRIVIAL', 'FEATURE', '2023-03-01', 5, 7),
       (1, 'Задача 13', 'Описание задача 13', 'NEW', 'BLOCKER', 'TASK', '2023-03-01', 7, 9),
       (2, 'Задача 14', 'Описание задача 14', 'IN_REVIEW', 'MINOR', 'BUG', '2023-03-01', 10, 7),
       (3, 'Задача 15', 'Описание задача 15', 'IN_WORK', 'MAJOR', 'FEATURE', '2023-03-01', 4, 7);
