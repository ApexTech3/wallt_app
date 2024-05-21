create table roles
(
    role_id serial
        primary key,
    name    varchar not null
        constraint roles_pk_2
            unique
);

alter table roles
    owner to postgres;

INSERT INTO public.roles (role_id, name)
VALUES (1, 'ADMIN');
INSERT INTO public.roles (role_id, name)
VALUES (2, 'USER');
