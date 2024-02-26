SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
drop schema if exists wallt_db cascade;
create schema wallt_db;
SET search_path TO wallt_db;
CREATE SEQUENCE increment_SEQ START 1;

create table countries
(
    country_id serial      not null primary key,
    name       varchar(50) not null
        constraint countries_pk_2
            unique
);

create table cities
(
    city_id    serial primary key,
    name       varchar(50) not null
        constraint cities_pk_2
            unique,
    country_id integer     not null
        constraint cities_countries_country_id_fk
            references countries
);

create table addresses
(
    address_id serial      not null primary key,
    street     varchar(50) not null,
    number     integer     not null,
    city_id    integer     not null
        constraint addresses_cities_city_id_fk
            references cities
);

create table users
(
    user_id     serial
        primary key,
    username    varchar(50)           not null
        constraint users_pk_2
            unique,
    password    varchar(32)           not null,
    first_name  varchar(50)           not null,
    middle_name varchar(50)           not null,
    last_name   varchar(50)           not null,
    email       varchar(100)          not null
        constraint users_pk_3
            unique,
    phone       varchar(10)           not null
        constraint users_pk_4
            unique,
    photo       varchar(255)          not null,
    address_id  integer
        constraint users_addresses_address_id_fk
            references addresses,
    verified    boolean default false not null,
    blocked     boolean default false not null
);

create table roles
(
    role_id serial  not null primary key,
    name    varchar not null
        constraint roles_pk_2
            unique
);

create table users_roles
(
    user_id integer not null,
    role_id integer not null,
    constraint users_roles_pk
        primary key (user_id, role_id)
);

create table cards
(
    card_id         serial      not null primary key,
    number          varchar(16) not null
        constraint cards2_pk_2
            unique,
    expiration_date date        not null,
    cvv             varchar(3)  not null,
    holder_id       integer     not null
        constraint cards2_users_id_fk
            references users
);

create table currencies
(
    currency_id serial     not null primary key,
    name        varchar(3) not null
        constraint currencies_pk_2
            unique
);


create table wallets
(
    wallet_id   serial            not null primary key,
    holder_id   integer           not null
        constraint wallets_users_id_fk
            references users,
    amount      bigint  default 0 not null,
    currency_id integer default 0 not null
        constraint wallets_currencies_currency_id_fk
            references currencies
);

create table transactions
(
    transaction_id  serial                       not null primary key,
    receiver_wallet integer                      not null
        constraint transactions_wallets_wallet_id_fk_2
            references wallets,
    sender_wallet   integer                      not null
        constraint transactions_wallets_wallet_id_fk
            references wallets,
    amount          bigint                       not null,
    currency_id     integer                      not null
        constraint transactions_currencies_currency_id_fk
            references currencies,
    status          varchar(10) default 'FAILED' not null
);

create table transfers
(
    transfer_id serial                not null primary key,
    card_id     integer               not null
        constraint c
            references cards,
    amount      bigint  default 0     not null,
    status      boolean default false not null,
    currency_id integer               not null
        constraint transfers_currencies_currency_id_fk
            references currencies,
    wallet_id   integer               not null
        constraint transfers_wallets_wallet_id_fk
            references wallets,
    direction   varchar(20)           not null
);

INSERT INTO wallt_db.users (username, password, first_name, middle_name, last_name, email, phone, photo, address_id,
                            verified, blocked)
VALUES ('pesho', '1234', 'pesho', 'peshov', 'peshov', 'pesho@peshomail.com', 'pesho', 'pesho', 1, false, false);
INSERT INTO wallt_db.users (username, password, first_name, middle_name, last_name, email, phone, photo, address_id,
                            verified, blocked)
VALUES ('gosho', '1234', 'gosho', 'goshov', 'gosho', 'gosho@goshomail.com', 'gosho', 'gosho', 1, false, false);
INSERT INTO wallt_db.users (username, password, first_name, middle_name, last_name, email, phone, photo, address_id,
                            verified, blocked)
VALUES ('tosho', '1234', 'tosho', 'toshov', 'toshov', 'tosho@toshomail.com', 'tosho', 'tosho', 1, false, false);
INSERT INTO wallt_db.users (username, password, first_name, middle_name, last_name, email, phone, photo, address_id,
                            verified, blocked)
VALUES ('ivan', '1234', 'ivan', 'ivanov', 'ivanov', 'ivan@ivanmail.com', 'ivan', 'ivan', 1, false, false);
INSERT INTO wallt_db.users (username, password, first_name, middle_name, last_name, email, phone, photo, address_id,
                            verified, blocked)
VALUES ('mariika', '1234', 'mariika', 'mariikova', 'mariikova', 'mariika@mariikamail.com', 'mariika', 'mariika', 1, false, false);

INSERT INTO wallt_db.wallets (holder_id, amount, currency_id)
VALUES (1, 12345678, 1);
INSERT INTO wallt_db.wallets (holder_id, amount, currency_id)
VALUES (1, 23456789, 2);
INSERT INTO wallt_db.wallets (holder_id, amount, currency_id)
VALUES (1, 3456789, 3);
INSERT INTO wallt_db.wallets (holder_id, amount, currency_id)
VALUES (2, 3456, 2);