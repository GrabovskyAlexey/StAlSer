insert into roles (role_name, role_desc)
values ('ROLE_USER', 'Пользователь'),
       ('ROLE_ADMIN', 'Администратор');

insert into users (email, login, password)
values ('test@example.com', 'user', '$2a$12$eWOTDde53DcQHkJokNOSI.0TxSIH2hz24i4VLzf6Atb.K.SJstbgy');
--login: user, password: 123


insert into users_roles (users_id, roles_id)
values (1, 1);
