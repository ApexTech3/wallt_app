create table wallets
(
    wallet_id     serial
        primary key,
    holder_id     integer                             not null
        constraint wallets_users_id_fk
            references users,
    amount        numeric   default 0                 not null
        constraint chk_amount_positive
            check (amount >= (0)::numeric),
    currency_id   integer   default 0                 not null
        constraint wallets_currencies_currency_id_fk
            references currencies,
    stamp_created timestamp default CURRENT_TIMESTAMP not null,
    is_active     boolean   default true              not null,
    is_default    boolean   default false             not null
);

alter table wallets
    owner to postgres;

INSERT INTO wallt_db.wallets (wallet_id, holder_id, amount, currency_id, stamp_created, is_active, is_default)
VALUES (2, 2, 1370.3751169361959862, 1, '2024-02-26 18:29:28.878875', true, true);
INSERT INTO wallt_db.wallets (wallet_id, holder_id, amount, currency_id, stamp_created, is_active, is_default)
VALUES (9, 3, 300, 2, '2024-03-13 22:36:26.130007', true, false);
INSERT INTO wallt_db.wallets (wallet_id, holder_id, amount, currency_id, stamp_created, is_active, is_default)
VALUES (10, 3, 0.5, 4, '2024-03-13 22:36:26.130007', true, false);
INSERT INTO wallt_db.wallets (wallet_id, holder_id, amount, currency_id, stamp_created, is_active, is_default)
VALUES (12, 4, 59.558204996053472, 1, '2024-03-17 19:47:24.736454', true, false);
INSERT INTO wallt_db.wallets (wallet_id, holder_id, amount, currency_id, stamp_created, is_active, is_default)
VALUES (306, 305, 0, 2, '2024-03-19 10:43:04.144109', true, true);
INSERT INTO wallt_db.wallets (wallet_id, holder_id, amount, currency_id, stamp_created, is_active, is_default)
VALUES (313, 305, 1, 3, '2024-03-19 10:51:09.078455', true, false);
INSERT INTO wallt_db.wallets (wallet_id, holder_id, amount, currency_id, stamp_created, is_active, is_default)
VALUES (331, 330, 0, 2, '2024-03-19 14:38:45.947347', true, true);
INSERT INTO wallt_db.wallets (wallet_id, holder_id, amount, currency_id, stamp_created, is_active, is_default)
VALUES (333, 332, 0, 2, '2024-03-19 14:40:49.116756', true, true);
INSERT INTO wallt_db.wallets (wallet_id, holder_id, amount, currency_id, stamp_created, is_active, is_default)
VALUES (4, 2, 0.3034555736162824, 2, '2024-02-26 18:29:28.878875', true, false);
INSERT INTO wallt_db.wallets (wallet_id, holder_id, amount, currency_id, stamp_created, is_active, is_default)
VALUES (16, 255, 30, 1, '2024-03-19 08:14:06.662500', true, true);
INSERT INTO wallt_db.wallets (wallet_id, holder_id, amount, currency_id, stamp_created, is_active, is_default)
VALUES (350, 349, 0, 2, '2024-03-20 00:51:23.975030', true, true);
INSERT INTO wallt_db.wallets (wallet_id, holder_id, amount, currency_id, stamp_created, is_active, is_default)
VALUES (352, 349, 20, 1, '2024-03-20 00:56:20.160534', true, false);
INSERT INTO wallt_db.wallets (wallet_id, holder_id, amount, currency_id, stamp_created, is_active, is_default)
VALUES (321, 320, 30, 2, '2024-03-19 14:18:23.137943', true, true);
INSERT INTO wallt_db.wallets (wallet_id, holder_id, amount, currency_id, stamp_created, is_active, is_default)
VALUES (336, 1, 20, 2, '2024-03-19 16:24:25.447960', true, false);
INSERT INTO wallt_db.wallets (wallet_id, holder_id, amount, currency_id, stamp_created, is_active, is_default)
VALUES (3, 3, 308.255798842737626, 3, '2024-02-26 18:29:28.878875', true, true);
INSERT INTO wallt_db.wallets (wallet_id, holder_id, amount, currency_id, stamp_created, is_active, is_default)
VALUES (13, 4, 84.9087864, 2, '2024-03-17 20:00:12.511563', true, true);
INSERT INTO wallt_db.wallets (wallet_id, holder_id, amount, currency_id, stamp_created, is_active, is_default)
VALUES (372, 368, 5, 1, '2024-03-20 02:09:18.966319', true, false);
INSERT INTO wallt_db.wallets (wallet_id, holder_id, amount, currency_id, stamp_created, is_active, is_default)
VALUES (369, 368, 30, 2, '2024-03-20 01:56:14.101207', true, true);
INSERT INTO wallt_db.wallets (wallet_id, holder_id, amount, currency_id, stamp_created, is_active, is_default)
VALUES (381, 380, 0, 2, '2024-03-20 11:34:21.408709', true, true);
INSERT INTO wallt_db.wallets (wallet_id, holder_id, amount, currency_id, stamp_created, is_active, is_default)
VALUES (1, 1, 328.444614988160416, 1, '2024-02-26 18:29:28.878875', true, false);
INSERT INTO wallt_db.wallets (wallet_id, holder_id, amount, currency_id, stamp_created, is_active, is_default)
VALUES (185, 1, 4966, 5, '2024-03-14 23:21:06.041938', true, false);
INSERT INTO wallt_db.wallets (wallet_id, holder_id, amount, currency_id, stamp_created, is_active, is_default)
VALUES (8, 3, 232, 1, '2024-03-13 22:36:26.130007', true, false);
INSERT INTO wallt_db.wallets (wallet_id, holder_id, amount, currency_id, stamp_created, is_active, is_default)
VALUES (386, 385, 0, 2, '2024-03-20 14:08:48.372457', true, true);
INSERT INTO wallt_db.wallets (wallet_id, holder_id, amount, currency_id, stamp_created, is_active, is_default)
VALUES (394, 393, 0, 2, '2024-03-20 13:31:04.790773', true, true);
INSERT INTO wallt_db.wallets (wallet_id, holder_id, amount, currency_id, stamp_created, is_active, is_default)
VALUES (396, 395, 0, 2, '2024-03-20 15:03:38.180156', true, true);
INSERT INTO wallt_db.wallets (wallet_id, holder_id, amount, currency_id, stamp_created, is_active, is_default)
VALUES (398, 397, 0, 2, '2024-03-20 15:06:48.151378', true, true);
INSERT INTO wallt_db.wallets (wallet_id, holder_id, amount, currency_id, stamp_created, is_active, is_default)
VALUES (402, 401, 0, 2, '2024-03-20 15:10:11.798550', true, true);
INSERT INTO wallt_db.wallets (wallet_id, holder_id, amount, currency_id, stamp_created, is_active, is_default)
VALUES (404, 401, 0, 4, '2024-03-20 15:12:08.448542', true, false);
INSERT INTO wallt_db.wallets (wallet_id, holder_id, amount, currency_id, stamp_created, is_active, is_default)
VALUES (187, 1, 13.5, 4, '2024-03-14 23:21:25.339359', true, true);
INSERT INTO wallt_db.wallets (wallet_id, holder_id, amount, currency_id, stamp_created, is_active, is_default)
VALUES (7, 1, 698466.20082, 3, '2024-02-27 12:45:28.035422', true, false);
