create table transactions
(
    transaction_id  serial
        primary key,
    receiver_wallet integer                                         not null
        constraint transactions_wallets_wallet_id_fk_2
            references wallets,
    sender_wallet   integer                                         not null
        constraint transactions_wallets_wallet_id_fk
            references wallets,
    amount          numeric                                         not null,
    exchange_rate   double precision                                not null,
    status          varchar(10) default 'FAILED'::character varying not null,
    stamp_created   timestamp   default CURRENT_TIMESTAMP           not null
);

alter table transactions
    owner to doadmin;

INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (3, 1, 2, 123123, 2, 'FAILED', '2024-03-01 14:10:42.721960');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (4, 2, 1, 666, 1, 'SUCCESSFUL', '2024-03-01 14:11:19.600193');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (5, 1, 2, 12312, 1, 'SUCCESSFUL', '2024-03-01 14:11:19.600193');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (103, 1, 7, 10, 1, 'SUCCESSFUL', '2024-03-04 18:42:41.007621');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (104, 7, 1, 41, 1, 'SUCCESSFUL', '2024-03-04 18:48:22.925369');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (105, 2, 1, 2, 1, 'SUCCESSFUL', '2024-03-04 19:14:42.339112');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (106, 2, 1, 2000, 1, 'FAILED', '2024-03-04 19:15:00.687622');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (107, 2, 1, 2000, 1, 'FAILED', '2024-03-04 19:22:24.064476');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (108, 2, 1, 2000, 1, 'FAILED', '2024-03-04 19:22:58.425434');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (109, 2, 1, 2000, 1, 'FAILED', '2024-03-04 19:24:22.527945');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (110, 2, 1, 2000, 1, 'FAILED', '2024-03-04 19:27:25.282826');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (111, 2, 1, 2000, 1, 'FAILED', '2024-03-04 19:32:46.359385');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (112, 1, 2, 2000, 1, 'FAILED', '2024-03-04 20:18:44.900789');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (113, 2, 1, 500, 1, 'SUCCESSFUL', '2024-03-04 20:19:04.417971');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (119, 2, 1, 10, 1, 'SUCCESSFUL', '2024-03-05 12:13:33.097524');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (120, 4, 2, 5.54918, 0.554918, 'FAILED', '2024-03-05 12:14:13.371032');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (121, 4, 7, 5.54918, 0.554918, 'SUCCESSFUL', '2024-03-05 12:14:36.616899');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (122, 7, 4, 27.0310207994694705, 1.8020680532979647, 'FAILED', '2024-03-05 13:22:23.826770');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (123, 7, 4, 27.0310207994694705, 1.8020680532979647, 'FAILED', '2024-03-05 13:23:08.555110');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (124, 7, 4, 27.0310207994694705, 1.8020680532979647, 'FAILED', '2024-03-05 13:23:30.766017');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (125, 7, 4, 25.2289527461715058, 1.8020680532979647, 'FAILED', '2024-03-05 13:24:06.343241');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (126, 7, 4, 3.6041361065959294, 1.8020680532979647, 'SUCCESSFUL', '2024-03-05 13:40:42.249539');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (127, 7, 4, 3.6041361065959294, 1.8020680532979647, 'SUCCESSFUL', '2024-03-05 13:41:08.317853');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (128, 7, 4, 3.6041361065959294, 1.8020680532979647, 'SUCCESSFUL', '2024-03-05 13:41:17.604512');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (129, 7, 4, 3.6041361065959294, 1.8020680532979647, 'SUCCESSFUL', '2024-03-05 13:41:21.529275');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (130, 7, 4, 3.6041361065959294, 1.8020680532979647, 'FAILED', '2024-03-05 13:41:27.452666');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (131, 7, 4, 3.6041361065959294, 1.8020680532979647, 'FAILED', '2024-03-05 13:42:26.963804');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (132, 7, 4, 3.6041361065959294, 1.8020680532979647, 'FAILED', '2024-03-05 13:42:31.306080');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (135, 2, 1, 8, 1, 'SUCCESSFUL', '2024-03-05 14:43:50.846511');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (136, 2, 1, 3000, 1, 'FAILED', '2024-03-05 14:44:15.530645');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (139, 2, 1, 100, 1, 'SUCCESSFUL', '2024-03-05 14:48:06.326322');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (140, 2, 1, 100, 1, 'SUCCESSFUL', '2024-03-05 14:59:14.164896');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (153, 2, 1, 10, 1, 'SUCCESSFUL', '2024-03-06 16:16:39.466430');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (154, 2, 1, 10, 1, 'SUCCESSFUL', '2024-03-06 16:16:55.302413');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (159, 1, 3, 39.116409992106944, 1.9558204996053472, 'SUCCESSFUL', '2024-03-07 12:56:28.683201');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (7, 1, 3, 15.848, 1.9558204996053472, 'SUCCESSFUL', '2024-03-08 12:56:28.683000');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (161, 1, 3, 19.558204996053472, 1.9558204996053472, 'SUCCESSFUL', '2024-03-12 14:17:55.303358');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (6, 3, 1, 100, 1, 'SUCCESSFUL', '2024-03-01 14:11:19.600193');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (229, 12, 7, 19.558204996053472, 1.9558204996053472, 'SUCCESSFUL', '2024-03-17 21:48:33.571683');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (230, 13, 7, 10, 1, 'SUCCESSFUL', '2024-03-17 22:04:26.321262');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (231, 13, 7, 10.8532, 1.08532, 'SUCCESSFUL', '2024-03-17 22:06:16.185107');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (232, 12, 1, 10, 1, 'SUCCESSFUL', '2024-03-17 22:51:16.775742');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (233, 12, 1, 10, 1, 'SUCCESSFUL', '2024-03-17 22:51:18.553328');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (234, 13, 7, 10.8532, 1.08532, 'SUCCESSFUL', '2024-03-17 23:09:37.694083');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (235, 12, 1, 10, 1, 'SUCCESSFUL', '2024-03-17 23:14:20.260693');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (236, 13, 7, 10.8532, 1.08532, 'SUCCESSFUL', '2024-03-17 23:16:26.218896');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (237, 13, 7, 10.8532, 1.08532, 'SUCCESSFUL', '2024-03-17 23:17:36.328871');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (238, 13, 7, 10.8532, 1.08532, 'SUCCESSFUL', '2024-03-17 23:18:03.764167');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (239, 12, 1, 10, 1, 'SUCCESSFUL', '2024-03-18 10:55:11.745250');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (240, 2, 1, 10.23, 1, 'SUCCESSFUL', '2024-03-18 10:55:56.657735');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (251, 13, 7, 10.8532, 1.08532, 'SUCCESSFUL', '2024-03-18 11:56:53.418775');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (252, 13, 7, 0.0108532, 1.08532, 'SUCCESSFUL', '2024-03-18 11:57:37.402899');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (256, 2, 1, 2, 1, 'SUCCESSFUL', '2024-03-18 12:44:42.832458');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (257, 2, 185, 4.5715222789673434, 2.2857611394836717, 'SUCCESSFUL', '2024-03-18 12:47:25.078264');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (258, 2, 1, 2, 1, 'SUCCESSFUL', '2024-03-18 12:50:12.846096');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (259, 2, 1, 2, 1, 'SUCCESSFUL', '2024-03-18 12:50:15.621077');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (260, 2, 7, 3.9116409992106944, 1.9558204996053472, 'SUCCESSFUL', '2024-03-18 12:50:26.992749');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (261, 2, 185, 4.5715222789673434, 2.2857611394836717, 'SUCCESSFUL', '2024-03-18 12:52:39.553809');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (262, 2, 1, 10, 1, 'SUCCESSFUL', '2024-03-18 12:55:07.524989');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (263, 2, 7, 0, 1.9558204996053472, 'SUCCESSFUL', '2024-03-18 13:00:09.365255');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (264, 2, 7, 19.558204996053472, 1.9558204996053472, 'SUCCESSFUL', '2024-03-18 13:04:51.731535');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (265, 2, 185, 22.857611394836717, 2.2857611394836717, 'SUCCESSFUL', '2024-03-18 13:35:07.236159');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (266, 2, 1, 10, 1, 'SUCCESSFUL', '2024-03-18 13:35:44.568702');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (267, 2, 7, 19.558204996053472, 1.9558204996053472, 'SUCCESSFUL', '2024-03-18 15:02:53.769301');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (272, 2, 7, 19.558204996053472, 1.9558204996053472, 'SUCCESSFUL', '2024-03-18 15:18:57.653355');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (276, 13, 7, 2170.64, 1.08532, 'FAILED', '2024-03-18 18:21:39.503229');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (277, 13, 7, 2170.64, 1.08532, 'FAILED', '2024-03-18 18:24:46.724188');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (278, 13, 7, 2170.64, 1.08532, 'FAILED', '2024-03-18 18:25:05.669799');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (279, 13, 7, 0.0108532, 1.08532, 'SUCCESSFUL', '2024-03-18 18:43:33.803843');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (280, 13, 7, 10.8532, 1.08532, 'SUCCESSFUL', '2024-03-18 18:46:18.249701');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (281, 13, 7, 5.4266, 1.08532, 'SUCCESSFUL', '2024-03-18 18:48:47.562944');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (282, 13, 7, 2.17064, 1.08532, 'SUCCESSFUL', '2024-03-18 18:50:34.469696');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (283, 13, 7, 2.17064, 1.08532, 'SUCCESSFUL', '2024-03-18 23:12:43.304820');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (284, 13, 185, 1.26841, 1.26841, 'SUCCESSFUL', '2024-03-18 23:13:11.329761');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (290, 3, 185, 11.686967898868537, 1.1686967898868537, 'SUCCESSFUL', '2024-03-19 09:36:40.462211');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (299, 2, 7, 19.558204996053472, 1.9558204996053472, 'SUCCESSFUL', '2024-03-19 10:10:29.519088');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (317, 7, 313, 1, 1, 'SUCCESSFUL', '2024-03-19 12:44:05.863771');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (362, 336, 321, 10, 1, 'SUCCESSFUL', '2024-03-20 02:34:01.678215');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (376, 8, 372, 10, 1, 'SUCCESSFUL', '2024-03-20 02:11:31.066918');
INSERT INTO wallt_db.transactions (transaction_id, receiver_wallet, sender_wallet, amount, exchange_rate, status,
                                   stamp_created)
VALUES (382, 8, 1, 200, 1, 'SUCCESSFUL', '2024-03-20 11:53:44.174119');
