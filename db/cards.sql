create table cards
(
    card_id          serial
        primary key,
    number           varchar(16) not null
        constraint cards2_pk_2
            unique,
    cvv              varchar(3)  not null,
    holder_id        integer     not null
        constraint cards2_users_id_fk
            references users,
    first_last_name  varchar(30),
    stamp_created    timestamp default CURRENT_TIMESTAMP,
    expiration_month varchar(2),
    expiration_year  varchar(2),
    is_active        boolean   default true
);

alter table cards
    owner to postgres;

INSERT INTO public.cards (card_id, number, cvv, holder_id, first_last_name, stamp_created, expiration_month,
                            expiration_year, is_active)
VALUES (373, '1234432481234123', '459', 368, 'new user', '2024-03-20 02:10:03.232310', '01', '28', true);
INSERT INTO public.cards (card_id, number, cvv, holder_id, first_last_name, stamp_created, expiration_month,
                            expiration_year, is_active)
VALUES (41, '0003124177370041', '000', 1, 'Pesho Pesheev', '2024-02-29 23:12:54.000000', '12', '24', true);
INSERT INTO public.cards (card_id, number, cvv, holder_id, first_last_name, stamp_created, expiration_month,
                            expiration_year, is_active)
VALUES (403, '0000000000000000', '222', 401, 'MOQ EKARTATA', '2024-03-20 15:11:59.449592', '02', '22', true);
INSERT INTO public.cards (card_id, number, cvv, holder_id, first_last_name, stamp_created, expiration_month,
                            expiration_year, is_active)
VALUES (164, '1234123412341234', '123', 1, 'P P', '2024-03-12 18:24:58.854859', '11', '25', true);
INSERT INTO public.cards (card_id, number, cvv, holder_id, first_last_name, stamp_created, expiration_month,
                            expiration_year, is_active)
VALUES (308, '0011223344556677', '123', 305, 'Bai Shile', '2024-03-19 10:47:04.458865', '11', '27', true);
INSERT INTO public.cards (card_id, number, cvv, holder_id, first_last_name, stamp_created, expiration_month,
                            expiration_year, is_active)
VALUES (314, '7766554433221111', '023', 305, 'Bai Shile', '2024-03-19 10:51:36.247059', '11', '25', true);
INSERT INTO public.cards (card_id, number, cvv, holder_id, first_last_name, stamp_created, expiration_month,
                            expiration_year, is_active)
VALUES (344, '9999999966666666', '123', 255, 'Mariika Mariikova', '2024-03-19 17:59:05.219736', '11', '27', true);
INSERT INTO public.cards (card_id, number, cvv, holder_id, first_last_name, stamp_created, expiration_month,
                            expiration_year, is_active)
VALUES (351, '1234567812345678', '123', 349, 'pesh pesh', '2024-03-20 00:52:11.017334', '01', '55', true);
INSERT INTO public.cards (card_id, number, cvv, holder_id, first_last_name, stamp_created, expiration_month,
                            expiration_year, is_active)
VALUES (356, '4132123465412342', '982', 320, 'Lucky Taylor', '2024-03-20 02:25:40.293725', '12', '25', true);
INSERT INTO public.cards (card_id, number, cvv, holder_id, first_last_name, stamp_created, expiration_month,
                            expiration_year, is_active)
VALUES (1, '7895656565646565', '123', 1, 'Pesho Peshov', '2024-02-26 18:28:11.029178', '10', '25', false);
INSERT INTO public.cards (card_id, number, cvv, holder_id, first_last_name, stamp_created, expiration_month,
                            expiration_year, is_active)
VALUES (170, '4321432143214321', '123', 1, 'G G', '2024-03-14 10:27:12.764613', '11', '25', true);
INSERT INTO public.cards (card_id, number, cvv, holder_id, first_last_name, stamp_created, expiration_month,
                            expiration_year, is_active)
VALUES (2, '1153124177370041', '000', 1, 'Pesho Pesheev', '2024-02-27 12:47:37.476429', '12', '26', true);
INSERT INTO public.cards (card_id, number, cvv, holder_id, first_last_name, stamp_created, expiration_month,
                            expiration_year, is_active)
VALUES (39, '1113124177370041', '000', 1, 'Pesho Pesheev', '2024-02-28 20:03:06.000000', '12', '25', true);
INSERT INTO public.cards (card_id, number, cvv, holder_id, first_last_name, stamp_created, expiration_month,
                            expiration_year, is_active)
VALUES (9, '0023124177370041', '000', 3, 'Gosho Goshov', '2024-02-29 23:12:54.000000', '12', '24', true);
