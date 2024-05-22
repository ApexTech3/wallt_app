create table transfers
(
    transfer_id   serial
        primary key,
    card_id       integer                                     not null
        constraint c
            references cards,
    amount        numeric default 0                           not null,
    status        varchar default 'FAILED'::character varying not null,
    currency_id   integer                                     not null
        constraint transfers_currencies_currency_id_fk
            references currencies,
    wallet_id     integer                                     not null
        constraint transfers_wallets_wallet_id_fk
            references wallets,
    direction     varchar(20)                                 not null,
    stamp_created timestamp                                   not null
);

INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (20, 1, 10, 'FAILED', 1, 1, 'DEPOSIT', '2024-02-27 17:06:32.781993');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (21, 1, 10, 'FAILED', 1, 1, 'DEPOSIT', '2024-02-27 17:06:36.264199');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (22, 1, 10, 'SUCCESSFUL', 1, 1, 'DEPOSIT', '2024-02-27 17:06:41.625035');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (23, 1, 10, 'FAILED', 1, 1, 'DEPOSIT', '2024-02-27 17:11:06.683032');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (24, 1, 10, 'SUCCESSFUL', 1, 1, 'DEPOSIT', '2024-02-27 17:12:22.810094');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (25, 1, 10, 'SUCCESSFUL', 1, 1, 'DEPOSIT', '2024-02-27 17:12:43.820458');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (26, 1, 10, 'SUCCESSFUL', 1, 1, 'DEPOSIT', '2024-02-27 17:12:49.127893');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (27, 2, 10, 'SUCCESSFUL', 1, 1, 'DEPOSIT', '2024-02-27 17:13:08.969587');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (28, 2, 10, 'SUCCESSFUL', 1, 1, 'DEPOSIT', '2024-02-27 17:13:13.883680');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (29, 2, 10, 'SUCCESSFUL', 1, 1, 'DEPOSIT', '2024-02-27 17:13:27.434781');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (30, 2, 10, 'SUCCESSFUL', 1, 1, 'DEPOSIT', '2024-02-27 17:13:32.907268');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (31, 2, 10, 'FAILED', 1, 1, 'DEPOSIT', '2024-02-27 17:14:29.342810');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (32, 2, 10, 'FAILED', 1, 1, 'DEPOSIT', '2024-02-27 17:14:39.808830');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (33, 2, 10, 'FAILED', 1, 1, 'DEPOSIT', '2024-02-27 17:14:43.893827');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (34, 1, 10, 'SUCCESSFUL', 1, 1, 'DEPOSIT', '2024-02-27 17:15:03.364267');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (35, 1, 10, 'FAILED', 1, 1, 'DEPOSIT', '2024-02-27 17:15:10.304193');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (36, 1, 10, 'FAILED', 1, 1, 'DEPOSIT', '2024-02-27 17:15:16.579049');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (37, 1, 10, 'FAILED', 1, 1, 'DEPOSIT', '2024-02-27 17:15:18.674989');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (38, 1, 10, 'SUCCESSFUL', 1, 1, 'DEPOSIT', '2024-02-27 17:15:22.748004');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (42, 1, 100, 'SUCCESSFUL', 1, 1, 'DEPOSIT', '2024-02-29 11:14:52.161065');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (43, 1, 100, 'FAILED', 1, 1, 'WITHDRAW', '2024-02-29 11:17:03.366139');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (44, 1, 100, 'FAILED', 1, 1, 'WITHDRAW', '2024-02-29 11:17:13.679556');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (45, 1, 100, 'SUCCESSFUL', 1, 1, 'WITHDRAW', '2024-02-29 11:17:17.357724');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (46, 1, 100, 'SUCCESSFUL', 1, 1, 'WITHDRAW', '2024-02-29 11:17:24.637600');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (47, 1, 100, 'FAILED', 1, 1, 'WITHDRAW', '2024-02-29 11:17:39.848710');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (48, 1, 100, 'SUCCESSFUL', 1, 1, 'WITHDRAW', '2024-02-29 11:17:47.573154');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (49, 1, 100, 'SUCCESSFUL', 1, 1, 'WITHDRAW', '2024-02-29 11:17:48.984551');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (50, 1, 10, 'FAILED', 1, 1, 'DEPOSIT', '2024-02-29 11:22:03.290206');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (51, 1, 10, 'FAILED', 1, 1, 'DEPOSIT', '2024-02-29 11:22:14.704378');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (52, 1, 10, 'SUCCESSFUL', 1, 1, 'DEPOSIT', '2024-02-29 11:22:19.059895');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (53, 1, 100, 'FAILED', 1, 1, 'WITHDRAW', '2024-02-29 11:22:31.970668');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (56, 1, 10, 'FAILED', 1, 1, 'DEPOSIT', '2024-02-29 11:43:24.272262');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (57, 1, 10, 'SUCCESSFUL', 1, 1, 'DEPOSIT', '2024-02-29 11:43:28.956858');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (58, 1, 10, 'SUCCESSFUL', 1, 1, 'DEPOSIT', '2024-02-29 11:43:30.295866');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (59, 1, 10, 'FAILED', 1, 1, 'WITHDRAW', '2024-02-29 11:43:50.314651');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (60, 1, 10, 'SUCCESSFUL', 1, 1, 'WITHDRAW', '2024-02-29 11:43:52.995845');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (61, 1, 10, 'SUCCESSFUL', 1, 1, 'WITHDRAW', '2024-02-29 11:43:54.237784');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (62, 1, 10, 'SUCCESSFUL', 1, 1, 'WITHDRAW', '2024-02-29 11:44:01.812070');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (63, 1, 10, 'SUCCESSFUL', 1, 1, 'DEPOSIT', '2024-03-01 08:00:59.134384');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (64, 1, 10, 'FAILED', 1, 1, 'DEPOSIT', '2024-03-01 08:01:04.681825');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (65, 1, 10, 'SUCCESSFUL', 1, 1, 'DEPOSIT', '2024-03-01 08:01:07.447644');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (66, 2, 10, 'SUCCESSFUL', 1, 1, 'DEPOSIT', '2024-03-01 08:02:02.581425');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (67, 2, 10, 'FAILED', 1, 1, 'DEPOSIT', '2024-03-01 08:02:49.494245');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (68, 2, 10, 'SUCCESSFUL', 1, 1, 'DEPOSIT', '2024-03-01 08:02:51.814009');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (69, 2, 10, 'SUCCESSFUL', 1, 1, 'DEPOSIT', '2024-03-01 08:02:53.072194');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (70, 2, 10, 'SUCCESSFUL', 1, 1, 'DEPOSIT', '2024-03-01 08:02:55.199772');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (71, 2, 10, 'SUCCESSFUL', 1, 1, 'DEPOSIT', '2024-03-01 08:02:57.229977');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (72, 2, 10, 'FAILED', 1, 1, 'DEPOSIT', '2024-03-01 08:03:30.908768');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (73, 2, 10, 'FAILED', 1, 1, 'DEPOSIT', '2024-03-01 08:03:33.021017');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (74, 2, 10, 'FAILED', 1, 1, 'DEPOSIT', '2024-03-01 08:03:34.334781');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (75, 2, 10, 'FAILED', 1, 1, 'DEPOSIT', '2024-03-01 08:03:35.824834');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (76, 2, 10, 'FAILED', 1, 1, 'DEPOSIT', '2024-03-01 08:03:37.065909');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (77, 9, 10, 'FAILED', 1, 1, 'DEPOSIT', '2024-03-01 08:03:53.074323');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (78, 9, 10, 'FAILED', 1, 1, 'DEPOSIT', '2024-03-01 08:03:56.028572');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (79, 9, 10, 'SUCCESSFUL', 1, 1, 'DEPOSIT', '2024-03-01 08:03:57.659545');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (80, 2, 10, 'FAILED', 1, 1, 'DEPOSIT', '2024-03-01 08:14:26.305826');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (81, 1, 10, 'SUCCESSFUL', 1, 1, 'DEPOSIT', '2024-03-01 08:14:31.731365');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (82, 1, 10, 'SUCCESSFUL', 1, 1, 'DEPOSIT', '2024-03-01 08:14:32.954255');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (83, 1, 10, 'FAILED', 1, 1, 'DEPOSIT', '2024-03-01 08:14:33.808319');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (84, 1, 10, 'SUCCESSFUL', 1, 1, 'WITHDRAW', '2024-03-01 08:14:50.814442');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (85, 1, 10, 'FAILED', 1, 1, 'WITHDRAW', '2024-03-01 08:14:54.731348');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (86, 1, 10, 'SUCCESSFUL', 1, 1, 'WITHDRAW', '2024-03-01 08:14:59.620667');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (87, 1, 10, 'SUCCESSFUL', 1, 1, 'WITHDRAW', '2024-03-01 09:12:20.492354');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (114, 1, 10, 'SUCCESSFUL', 1, 1, 'DEPOSIT', '2024-03-05 11:11:37.592991');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (115, 1, 200, 'SUCCESSFUL', 1, 1, 'DEPOSIT', '2024-03-05 11:12:32.297436');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (116, 39, 200, 'FAILED', 1, 1, 'DEPOSIT', '2024-03-05 11:13:16.818606');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (117, 39, 200, 'SUCCESSFUL', 1, 1, 'DEPOSIT', '2024-03-05 11:13:40.332522');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (118, 1, 10, 'SUCCESSFUL', 1, 1, 'DEPOSIT', '2024-03-05 11:15:10.405625');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (157, 9, 150, 'FAILED', 3, 3, 'DEPOSIT', '2024-03-07 12:52:39.270622');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (158, 9, 100, 'FAILED', 3, 3, 'WITHDRAW', '2024-03-07 12:55:50.447306');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (175, 39, 10, 'SUCCESSFUL', 1, 1, 'DEPOSIT', '2024-03-14 18:35:36.963732');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (176, 39, 10, 'FAILED', 1, 1, 'DEPOSIT', '2024-03-14 18:35:52.780602');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (177, 39, 10, 'FAILED', 1, 1, 'DEPOSIT', '2024-03-14 18:36:24.680257');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (178, 39, 10.23, 'FAILED', 3, 7, 'DEPOSIT', '2024-03-14 18:39:42.173431');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (179, 39, 10, 'SUCCESSFUL', 1, 1, 'DEPOSIT', '2024-03-14 18:44:20.713326');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (180, 39, 10.23, 'SUCCESSFUL', 1, 1, 'DEPOSIT', '2024-03-14 18:44:30.144651');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (181, 39, 10, 'SUCCESSFUL', 1, 1, 'DEPOSIT', '2024-03-14 18:44:47.801201');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (182, 39, 200, 'FAILED', 3, 7, 'DEPOSIT', '2024-03-14 19:53:31.830206');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (183, 39, 200, 'SUCCESSFUL', 3, 7, 'DEPOSIT', '2024-03-14 19:53:46.267735');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (193, 39, 1, 'SUCCESSFUL', 3, 7, 'DEPOSIT', '2024-03-15 11:44:18.978638');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (194, 39, 10, 'SUCCESSFUL', 1, 1, 'WITHDRAW', '2024-03-15 12:00:58.011603');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (195, 39, 10, 'SUCCESSFUL', 3, 7, 'WITHDRAW', '2024-03-15 13:07:05.933072');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (196, 39, 10.23, 'SUCCESSFUL', 1, 1, 'WITHDRAW', '2024-03-15 13:07:18.353943');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (197, 39, 10.23, 'FAILED', 1, 1, 'WITHDRAW', '2024-03-15 13:07:32.738484');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (198, 39, 10.23, 'SUCCESSFUL', 1, 1, 'WITHDRAW', '2024-03-15 13:07:52.498701');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (200, 39, 10, 'FAILED', 3, 7, 'WITHDRAW', '2024-03-15 14:51:01.072220');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (201, 39, 10, 'SUCCESSFUL', 3, 7, 'WITHDRAW', '2024-03-15 14:52:07.884926');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (204, 39, 10000, 'FAILED', 1, 1, 'WITHDRAW', '2024-03-15 14:57:04.750455');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (205, 39, 100000, 'FAILED', 3, 7, 'WITHDRAW', '2024-03-15 14:57:43.215340');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (206, 39, 10, 'SUCCESSFUL', 1, 1, 'WITHDRAW', '2024-03-15 14:59:23.791643');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (207, 39, 10000, 'CANCELLED', 3, 7, 'WITHDRAW', '2024-03-15 14:59:30.831841');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (208, 39, 100, 'SUCCESSFUL', 3, 7, 'WITHDRAW', '2024-03-17 10:30:16.663222');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (218, 39, 10, 'SUCCESSFUL', 1, 1, 'WITHDRAW', '2024-03-17 14:16:41.484201');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (219, 39, 10, 'SUCCESSFUL', 1, 1, 'WITHDRAW', '2024-03-17 14:17:06.319469');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (220, 39, 10, 'SUCCESSFUL', 3, 7, 'WITHDRAW', '2024-03-17 14:17:28.029473');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (222, 39, 10, 'SUCCESSFUL', 1, 1, 'WITHDRAW', '2024-03-17 14:17:45.755613');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (221, 39, 10, 'SUCCESSFUL', 1, 1, 'WITHDRAW', '2024-03-17 14:17:41.569079');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (223, 39, 10, 'FAILED', 1, 1, 'WITHDRAW', '2024-03-17 14:17:49.960252');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (224, 39, 10, 'SUCCESSFUL', 3, 7, 'WITHDRAW', '2024-03-17 14:19:46.778763');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (225, 39, 20, 'SUCCESSFUL', 1, 1, 'WITHDRAW', '2024-03-17 14:26:36.307997');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (226, 39, 10, 'CANCELLED', 3, 7, 'WITHDRAW', '2024-03-17 14:36:08.767004');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (227, 39, 10, 'CANCELLED', 3, 7, 'WITHDRAW', '2024-03-17 14:36:47.227430');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (228, 39, 10, 'SUCCESSFUL', 3, 7, 'WITHDRAW', '2024-03-17 14:43:19.790168');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (244, 39, 10, 'CANCELLED', 3, 7, 'WITHDRAW', '2024-03-18 10:58:15.403458');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (247, 39, 10, 'SUCCESSFUL', 1, 1, 'DEPOSIT', '2024-03-18 11:06:31.791640');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (268, 1, 10, 'SUCCESSFUL', 3, 7, 'WITHDRAW', '2024-03-18 15:03:01.800859');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (271, 1, 10, 'SUCCESSFUL', 4, 187, 'DEPOSIT', '2024-03-18 15:07:19.207045');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (269, 1, 10, 'CANCELLED', 5, 185, 'WITHDRAW', '2024-03-18 15:03:21.065470');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (270, 1, 10, 'CANCELLED', 5, 185, 'WITHDRAW', '2024-03-18 15:06:56.424892');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (285, 1, 20, 'SUCCESSFUL', 1, 1, 'WITHDRAW', '2024-03-18 23:14:13.629123');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (286, 1, 3, 'SUCCESSFUL', 4, 187, 'DEPOSIT', '2024-03-18 23:14:31.382990');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (287, 1, 1000, 'FAILED', 1, 1, 'WITHDRAW', '2024-03-18 23:15:23.911137');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (293, 41, 10.23, 'SUCCESSFUL', 1, 1, 'DEPOSIT', '2024-03-19 09:43:15.889030');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (294, 41, 10, 'SUCCESSFUL', 4, 187, 'DEPOSIT', '2024-03-19 09:43:31.039087');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (295, 2, 10, 'FAILED', 5, 185, 'DEPOSIT', '2024-03-19 09:43:37.752336');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (296, 39, 10, 'SUCCESSFUL', 5, 185, 'WITHDRAW', '2024-03-19 09:45:44.362780');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (309, 308, 10, 'FAILED', 2, 306, 'DEPOSIT', '2024-03-19 10:47:14.525813');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (310, 308, 10, 'FAILED', 2, 306, 'DEPOSIT', '2024-03-19 10:47:21.690452');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (311, 308, 10, 'SUCCESSFUL', 2, 306, 'DEPOSIT', '2024-03-19 10:47:28.290652');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (315, 308, 2, 'FAILED', 3, 313, 'DEPOSIT', '2024-03-19 12:43:32.766308');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (316, 308, 2, 'SUCCESSFUL', 3, 313, 'DEPOSIT', '2024-03-19 12:43:41.093480');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (318, 1, 10, 'SUCCESSFUL', 3, 7, 'WITHDRAW', '2024-03-19 13:34:49.676459');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (319, 1, 3, 'FAILED', 3, 7, 'WITHDRAW', '2024-03-19 13:36:47.531093');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (334, 1, 10, 'SUCCESSFUL', 3, 7, 'DEPOSIT', '2024-03-19 14:01:10.545585');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (335, 1, 696969, 'SUCCESSFUL', 3, 7, 'DEPOSIT', '2024-03-19 14:03:22.047529');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (337, 41, 10, 'FAILED', 3, 7, 'DEPOSIT', '2024-03-19 16:24:40.941988');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (338, 1, 10, 'SUCCESSFUL', 3, 7, 'WITHDRAW', '2024-03-19 16:24:50.606432');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (343, 1, 10, 'SUCCESSFUL', 2, 336, 'DEPOSIT', '2024-03-19 17:58:23.826839');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (345, 344, 10, 'SUCCESSFUL', 1, 16, 'DEPOSIT', '2024-03-19 17:59:13.973056');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (346, 344, 10, 'SUCCESSFUL', 1, 16, 'DEPOSIT', '2024-03-19 17:59:43.079590');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (347, 344, 10, 'SUCCESSFUL', 1, 16, 'DEPOSIT', '2024-03-19 18:01:20.075080');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (353, 351, 20, 'SUCCESSFUL', 1, 352, 'DEPOSIT', '2024-03-20 00:56:39.155705');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (354, 351, 10, 'PENDING', 1, 352, 'WITHDRAW', '2024-03-20 00:56:48.677213');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (355, 41, 10, 'CANCELLED', 3, 7, 'WITHDRAW', '2024-03-20 01:27:59.098832');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (357, 356, 50, 'SUCCESSFUL', 2, 321, 'DEPOSIT', '2024-03-20 02:25:53.708799');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (358, 356, 100, 'FAILED', 2, 321, 'WITHDRAW', '2024-03-20 02:26:05.137218');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (359, 356, 100, 'FAILED', 2, 321, 'WITHDRAW', '2024-03-20 02:26:13.991856');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (360, 356, 100, 'FAILED', 2, 321, 'WITHDRAW', '2024-03-20 02:26:36.107379');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (361, 356, 10, 'SUCCESSFUL', 2, 321, 'WITHDRAW', '2024-03-20 02:27:10.758528');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (374, 373, 20, 'FAILED', 2, 369, 'DEPOSIT', '2024-03-20 02:10:28.879530');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (375, 373, 20, 'SUCCESSFUL', 1, 372, 'DEPOSIT', '2024-03-20 02:10:43.620939');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (377, 373, 5, 'SUCCESSFUL', 1, 372, 'WITHDRAW', '2024-03-20 02:13:26.419607');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (378, 373, 50, 'SUCCESSFUL', 2, 369, 'DEPOSIT', '2024-03-20 03:23:19.757882');
INSERT INTO wallt_db.transfers (transfer_id, card_id, amount, status, currency_id, wallet_id, direction, stamp_created)
VALUES (379, 373, 20, 'SUCCESSFUL', 2, 369, 'WITHDRAW', '2024-03-20 03:23:45.807304');
