insert into roles (role_name, role_desc)
values ('ROLE_USER', 'Пользователь'),
       ('ROLE_ADMIN', 'Администратор');

insert into users (emale, login, password)
values ('test@gmail.com', 'user', '$2a$12$44Ob4I8FtyfgfrdyUfrMQ.JuBogZTSxt5fawiUcWPoMsDrwAZL.Me');


insert into users_roles (users_id, roles_id)
values (1, 1);
