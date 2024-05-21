create table users
(
    user_id            serial
        primary key,
    username           varchar(50)                         not null
        constraint users_pk_2
            unique,
    password           varchar(32)                         not null,
    first_name         varchar(50)                         not null,
    middle_name        varchar(50)                         not null,
    last_name          varchar(50)                         not null,
    email              varchar(100)                        not null
        constraint users_pk_3
            unique,
    phone              varchar(10)                         not null
        constraint users_pk_4
            unique,
    photo              varchar(255),
    verified           boolean   default false             not null,
    blocked            boolean   default false             not null,
    stamp_created      timestamp default CURRENT_TIMESTAMP not null,
    is_deleted         boolean   default false             not null,
    confirmation_token varchar(100),
    street             varchar(50),
    number             integer,
    city               varchar(20),
    country            varchar(20)
);

alter table users
    owner to postgres;

INSERT INTO public.users (user_id, username, password, first_name, middle_name, last_name, email, phone, photo,
                            verified, blocked, stamp_created, is_deleted, confirmation_token, street, number, city,
                            country)
VALUES (3, 'PresentPro', 'Qwer!234', 'Oliver', 'James', 'Smith', 'gosho@goshomail.com', '0888452462', null, true, false,
        '2024-02-26 18:29:07.579437', false, null, 'GreenHill Str', 1, 'Burgas', 'Bulgaria');
INSERT INTO public.users (user_id, username, password, first_name, middle_name, last_name, email, phone, photo,
                            verified, blocked, stamp_created, is_deleted, confirmation_token, street, number, city,
                            country)
VALUES (255, 'VisualVirtuoso', 'Qwer!234', 'Benjamin', 'Thomas', 'Anderson', 'boyanbot@gmail.com', '0888452465',
        'https://res.cloudinary.com/dhixuwvrk/image/upload/v1710756431/qa85vkxhfiaeijy71xng.png', true, false,
        '2024-03-18 12:07:13.024390', false, '8040fe49-c597-49ff-9763-735b8f3d5985', 'BlackHill Str', 1, 'Plovdiv',
        'Bulgaria');
INSERT INTO public.users (user_id, username, password, first_name, middle_name, last_name, email, phone, photo,
                            verified, blocked, stamp_created, is_deleted, confirmation_token, street, number, city,
                            country)
VALUES (21, 'cgerlingf', 'Qwer!234', 'Gothart', 'Clyve', 'Gerling', 'cgerlingf@google.ca', '2509579952', null, true,
        false, '2024-03-20 00:51:21.116878', false, null, '74901 Village Green Hill', 442633, 'Caronoan West',
        'Philippines');
INSERT INTO public.users (user_id, username, password, first_name, middle_name, last_name, email, phone, photo,
                            verified, blocked, stamp_created, is_deleted, confirmation_token, street, number, city,
                            country)
VALUES (330, 'PresentologyPro', 'Qwer!234', 'Mia', 'Emily', 'Davis', 'creativemind88@gmail.com', '0888452468', null,
        true, false, '2024-03-19 14:38:45.889429', false, 'dac1aa1f-2af5-49a3-ac27-6e410134c50f', 'BlackHill Str', 0,
        'Varna', 'Bulgaria');
INSERT INTO public.users (user_id, username, password, first_name, middle_name, last_name, email, phone, photo,
                            verified, blocked, stamp_created, is_deleted, confirmation_token, street, number, city,
                            country)
VALUES (332, 'TalkTactician', 'Qwer!234', 'Daniel', 'Joseph', 'Wilson', 'artistic.soul73@gmail.com', '0888452469', null,
        true, false, '2024-03-19 14:40:49.070125', false, '61a64b55-bdf8-412e-89f0-0953e99232c9', 'RedHill Str', 0,
        'Stara Zagora', 'Bulgaria');
INSERT INTO public.users (user_id, username, password, first_name, middle_name, last_name, email, phone, photo,
                            verified, blocked, stamp_created, is_deleted, confirmation_token, street, number, city,
                            country)
VALUES (5, 'PresentationProdigy', 'Qwer!234', 'Isabella', 'Marie', 'Brown', 'wise.thinker29@gmail.com', '0888452464',
        null, true, false, '2024-02-26 18:29:07.579437', false, null, 'GreenHill Str', 1, 'Stara Zagora', 'Bulgaria');
INSERT INTO public.users (user_id, username, password, first_name, middle_name, last_name, email, phone, photo,
                            verified, blocked, stamp_created, is_deleted, confirmation_token, street, number, city,
                            country)
VALUES (349, 'PitchPerfector', 'Qwer!234', 'Charlotte', 'Anne', 'Thompson', 'visualstoryteller55@gmail.com',
        '0888452460', null, true, false, '2024-03-20 00:51:23.916291', false, '92261ad2-45dd-4482-a81d-87aec7fe8a54',
        'BlueHill Str', 0, 'Plovdiv', 'Bulgaria');
INSERT INTO public.users (user_id, username, password, first_name, middle_name, last_name, email, phone, photo,
                            verified, blocked, stamp_created, is_deleted, confirmation_token, street, number, city,
                            country)
VALUES (305, 'SpeechCraftsman', 'Qwer!234', 'Ava', 'Elizabeth', 'Martinez', 'innovation.guru21@gmail.com', '0888452466',
        null, true, false, '2024-03-19 10:43:04.106195', false, 'b1966f72-93e7-43d1-bcd6-08bfb2b5e42a', 'RedHill Str',
        2, 'Pleven', 'Bulgaria');
INSERT INTO public.users (user_id, username, password, first_name, middle_name, last_name, email, phone, photo,
                            verified, blocked, stamp_created, is_deleted, confirmation_token, street, number, city,
                            country)
VALUES (4, 'DisplayDiva', 'Qwer!234', 'Ethan', 'Alexander', 'Williams', 'ivan@ivanmail.com', '0888452463', null, true,
        false, '2024-02-26 18:29:07.579437', false, null, 'GreenHill Str', 1, 'Sofia', 'Bulgaria');
INSERT INTO public.users (user_id, username, password, first_name, middle_name, last_name, email, phone, photo,
                            verified, blocked, stamp_created, is_deleted, confirmation_token, street, number, city,
                            country)
VALUES (320, 'ExpoExpert', 'Qwer!234', 'Lucas', 'Michael', 'Taylor', 'techwizard42@gmail.com', '0888452467',
        'https://res.cloudinary.com/dhixuwvrk/image/upload/v1710894240/rsfjc8h4jibkzgbhdd6z.png', true, false,
        '2024-03-19 14:18:23.080049', false, '706ebbb8-c5ea-4d1b-a6fe-be4b9d79d251', 'BlackHill Str', 0, 'Varna',
        'Bulgaria');
INSERT INTO public.users (user_id, username, password, first_name, middle_name, last_name, email, phone, photo,
                            verified, blocked, stamp_created, is_deleted, confirmation_token, street, number, city,
                            country)
VALUES (2, 'SlideMaster', 'Qwer!234', 'Sophia', 'Grace', 'Johnson', 'tosho@toshomail.com', '0888452461', null, true,
        false, '2024-02-26 18:29:07.579437', false, null, 'GreenHill Str', 1, 'Plovdiv', 'Bulgaria');
INSERT INTO public.users (user_id, username, password, first_name, middle_name, last_name, email, phone, photo,
                            verified, blocked, stamp_created, is_deleted, confirmation_token, street, number, city,
                            country)
VALUES (18, 'bpuddingc', 'Qwer!234', 'Gerhardt', 'Barney', 'Pudding', 'bpuddingc@mysql.com', '1477763809', null, true,
        false, '2024-03-20 00:51:20.682966', false, null, '4 Leroy Park', 788912, 'Twyford', 'United Kingdom');
INSERT INTO public.users (user_id, username, password, first_name, middle_name, last_name, email, phone, photo,
                            verified, blocked, stamp_created, is_deleted, confirmation_token, street, number, city,
                            country)
VALUES (16, 'lprestwicha', 'Qwer!234', 'Rosana', 'Letty', 'Prestwich', 'lprestwicha@telegraph.co.uk', '1079883715',
        null, true, false, '2024-03-20 00:51:20.388923', false, null, '64697 Tomscot Point', 236706, 'Nawābganj',
        'Bangladesh');
INSERT INTO public.users (user_id, username, password, first_name, middle_name, last_name, email, phone, photo,
                            verified, blocked, stamp_created, is_deleted, confirmation_token, street, number, city,
                            country)
VALUES (13, 'ioates7', 'Qwer!234', 'Fleurette', 'Issy', 'Oates', 'ioates7@house.gov', '8196585696', null, true, false,
        '2024-03-20 00:51:19.953807', false, null, '35368 Dawn Street', 268946, 'Bat Khela', 'Pakistan');
INSERT INTO public.users (user_id, username, password, first_name, middle_name, last_name, email, phone, photo,
                            verified, blocked, stamp_created, is_deleted, confirmation_token, street, number, city,
                            country)
VALUES (15, 'fbrodie9', 'Qwer!234', 'Hedvige', 'Francklyn', 'Brodie', 'fbrodie9@chron.com', '2923418784', null, true,
        false, '2024-03-20 00:51:20.237876', false, null, '49273 Prairieview Way', 313993, 'Cukangpanjang',
        'Indonesia');
INSERT INTO public.users (user_id, username, password, first_name, middle_name, last_name, email, phone, photo,
                            verified, blocked, stamp_created, is_deleted, confirmation_token, street, number, city,
                            country)
VALUES (20, 'lastelle', 'Qwer!234', 'Patrizia', 'Lynda', 'Astell', 'lastelle@wp.com', '3538732989', null, true, false,
        '2024-03-20 00:51:20.969872', false, null, '698 2nd Street', 974832, 'Succha', 'Peru');
INSERT INTO public.users (user_id, username, password, first_name, middle_name, last_name, email, phone, photo,
                            verified, blocked, stamp_created, is_deleted, confirmation_token, street, number, city,
                            country)
VALUES (24, 'rashmenti', 'Qwer!234', 'Dollie', 'Reggy', 'Ashment', 'rashmenti@paypal.com', '3505071395', null, true,
        false, '2024-03-20 00:51:21.545795', false, null, '9684 Warrior Hill', 185935, 'Kampungbaru', 'Indonesia');
INSERT INTO public.users (user_id, username, password, first_name, middle_name, last_name, email, phone, photo,
                            verified, blocked, stamp_created, is_deleted, confirmation_token, street, number, city,
                            country)
VALUES (22, 'dpoyntzg', 'Qwer!234', 'Catharine', 'Diane-marie', 'Poyntz', 'dpoyntzg@yahoo.co.jp', '5802694649', null,
        true, false, '2024-03-20 00:51:21.262744', false, null, '19242 Eliot Hill', 113096, 'Caigangan', 'Philippines');
INSERT INTO public.users (user_id, username, password, first_name, middle_name, last_name, email, phone, photo,
                            verified, blocked, stamp_created, is_deleted, confirmation_token, street, number, city,
                            country)
VALUES (17, 'emabbotb', 'Qwer!234', 'Carena', 'Ewell', 'Mabbot', 'emabbotb@unc.edu', '4558869027', null, true, false,
        '2024-03-20 00:51:20.547893', false, null, '990 Washington Alley', 606267, 'Jerada', 'Morocco');
INSERT INTO public.users (user_id, username, password, first_name, middle_name, last_name, email, phone, photo,
                            verified, blocked, stamp_created, is_deleted, confirmation_token, street, number, city,
                            country)
VALUES (14, 'qwadsworth8', 'Qwer!234', 'Phillie', 'Querida', 'Wadsworth', 'qwadsworth8@4shared.com', '1572021034', null,
        true, false, '2024-03-20 00:51:20.097832', false, null, '983 Scoville Hill', 977659, 'Komga', 'South Africa');
INSERT INTO public.users (user_id, username, password, first_name, middle_name, last_name, email, phone, photo,
                            verified, blocked, stamp_created, is_deleted, confirmation_token, street, number, city,
                            country)
VALUES (12, 'jbarnicott6', 'Qwer!234', 'Rhonda', 'Jackie', 'Barnicott', 'jbarnicott6@webeden.co.uk', '6424974365', null,
        true, false, '2024-03-20 00:51:19.806834', false, null, '5 Hanson Lane', 366470, 'Dongjingcheng', 'China');
INSERT INTO public.users (user_id, username, password, first_name, middle_name, last_name, email, phone, photo,
                            verified, blocked, stamp_created, is_deleted, confirmation_token, street, number, city,
                            country)
VALUES (6, 'ablois0', 'Qwer!234', 'Denny', 'Amaleta', 'Blois', 'ablois0@dailymail.co.uk', '7694032915', null, true,
        false, '2024-03-20 00:51:18.910137', false, null, '38225 Briar Crest Circle', 176064, 'Petauke', 'Zambia');
INSERT INTO public.users (user_id, username, password, first_name, middle_name, last_name, email, phone, photo,
                            verified, blocked, stamp_created, is_deleted, confirmation_token, street, number, city,
                            country)
VALUES (7, 'imatijasevic1', 'Qwer!234', 'Cally', 'Isadore', 'Matijasevic', 'imatijasevic1@loc.gov', '8225258254', null,
        true, false, '2024-03-20 00:51:19.062742', false, null, '87252 Lake View Trail', 854396, 'Youxi', 'China');
INSERT INTO public.users (user_id, username, password, first_name, middle_name, last_name, email, phone, photo,
                            verified, blocked, stamp_created, is_deleted, confirmation_token, street, number, city,
                            country)
VALUES (1, 'pesho', '1234', 'pesho', 'peshov', 'peshov', 'pesho@peshomail.com', '0888412465',
        'https://res.cloudinary.com/dhixuwvrk/image/upload/v1710887797/hynz6rdizxubiduo1pxd.png', true, false,
        '2024-02-26 18:29:07.579437', false, '3f19599f-369f-463b-b3d5-20134d2156f1', 'RedHill Str', 1, 'Sofia',
        'Bulgaria');
INSERT INTO public.users (user_id, username, password, first_name, middle_name, last_name, email, phone, photo,
                            verified, blocked, stamp_created, is_deleted, confirmation_token, street, number, city,
                            country)
VALUES (25, 'ncettellj', 'Qwer!234', 'Carmine', 'Nestor', 'Cettell', 'ncettellj@auda.org.au', '4582563524', null, true,
        false, '2024-03-20 00:51:21.686966', false, null, '6 Golf View Terrace', 302686, 'Agrela', 'Portugal');
INSERT INTO public.users (user_id, username, password, first_name, middle_name, last_name, email, phone, photo,
                            verified, blocked, stamp_created, is_deleted, confirmation_token, street, number, city,
                            country)
VALUES (10, 'aokeaveny4', 'Qwer!234', 'Sid', 'Ardys', 'O''Keaveny', 'aokeaveny4@digg.com', '3963615648', null, true,
        false, '2024-03-20 00:51:19.516040', false, null, '37627 Dwight Road', 711429, 'Alençon', 'France');
INSERT INTO public.users (user_id, username, password, first_name, middle_name, last_name, email, phone, photo,
                            verified, blocked, stamp_created, is_deleted, confirmation_token, street, number, city,
                            country)
VALUES (11, 'daddis5', 'Qwer!234', 'Catlee', 'Drusy', 'Addis', 'daddis5@yahoo.co.jp', '6232065017', null, true, false,
        '2024-03-20 00:51:19.668711', false, null, '5440 Transport Way', 864466, 'Wonda', 'Indonesia');
INSERT INTO public.users (user_id, username, password, first_name, middle_name, last_name, email, phone, photo,
                            verified, blocked, stamp_created, is_deleted, confirmation_token, street, number, city,
                            country)
VALUES (23, 'okippingh', 'Qwer!234', 'Deni', 'Oliviero', 'Kipping', 'okippingh@chron.com', '5306645086', null, true,
        false, '2024-03-20 00:51:21.399949', false, null, '76658 Northfield Drive', 480854, 'Pilar', 'Philippines');
INSERT INTO public.users (user_id, username, password, first_name, middle_name, last_name, email, phone, photo,
                            verified, blocked, stamp_created, is_deleted, confirmation_token, street, number, city,
                            country)
VALUES (19, 'mdyballd', 'Qwer!234', 'Dreddy', 'Maximilianus', 'Dyball', 'mdyballd@odnoklassniki.ru', '6581727685', null,
        true, false, '2024-03-20 00:51:20.822824', false, null, '7 Forster Pass', 105025, 'Batobato', 'Philippines');
INSERT INTO public.users (user_id, username, password, first_name, middle_name, last_name, email, phone, photo,
                            verified, blocked, stamp_created, is_deleted, confirmation_token, street, number, city,
                            country)
VALUES (9, 'ajoanic3', 'Qwer!234', 'Rutledge', 'Agatha', 'Joanic', 'ajoanic3@sfgate.com', '1056556059', null, true,
        false, '2024-03-20 00:51:19.371704', false, null, '5 Oakridge Way', 374043, 'Mosal’sk', 'Russia');
INSERT INTO public.users (user_id, username, password, first_name, middle_name, last_name, email, phone, photo,
                            verified, blocked, stamp_created, is_deleted, confirmation_token, street, number, city,
                            country)
VALUES (8, 'dbinns2', 'Qwer!234', 'Jennine', 'Del', 'Binns', 'dbinns2@tuttocitta.it', '8933513972', null, true, false,
        '2024-03-20 00:51:19.223012', false, null, '3 Steensland Way', 26720, 'Tabonok', 'Philippines');
INSERT INTO public.users (user_id, username, password, first_name, middle_name, last_name, email, phone, photo,
                            verified, blocked, stamp_created, is_deleted, confirmation_token, street, number, city,
                            country)
VALUES (393, 'max97', 'k9mk!AGPZ3S8C4W', 'Petar', 'Pep4ov', 'Andreev', 'andreev.petar.biz@gmail.com', '0898685003',
        'https://res.cloudinary.com/dhixuwvrk/image/upload/v1710941463/yhcwqmvv8jj4avstilwe.jpg', false, false,
        '2024-03-20 13:31:04.787018', false, 'c9ed9a23-ab32-429e-b818-9a4f3151e7fd', 'Petko Yu Todorov', 1, 'Sofia',
        'Bulgaria');
INSERT INTO public.users (user_id, username, password, first_name, middle_name, last_name, email, phone, photo,
                            verified, blocked, stamp_created, is_deleted, confirmation_token, street, number, city,
                            country)
VALUES (395, 'kandita za rabota ', 'A!@#a123', 'Ivan', 'Dge', 'Stefanov', 'stefeanpvivan1998@gmail.com', '0888681440',
        'https://res.cloudinary.com/dhixuwvrk/image/upload/v1710947016/u8bq9oanwnzaiufdftjp.png', true, false,
        '2024-03-20 15:03:38.177486', false, null, 'Zad bloka 99', 9, 'Sofia', 'България');
INSERT INTO public.users (user_id, username, password, first_name, middle_name, last_name, email, phone, photo,
                            verified, blocked, stamp_created, is_deleted, confirmation_token, street, number, city,
                            country)
VALUES (397, 'kanditaZaRabota', 'A!@#a123', 'vankataDge', 'Dge', 'Stefi', 'stefanovivan1998@abv.bg', '0888696969', null,
        false, false, '2024-03-20 15:06:48.149853', false, '83e6772e-b77f-4554-a226-bef8dd4709c7', 'Zad bloka 99', 9,
        'PlevenDge', 'България');
INSERT INTO public.users (user_id, username, password, first_name, middle_name, last_name, email, phone, photo,
                            verified, blocked, stamp_created, is_deleted, confirmation_token, street, number, city,
                            country)
VALUES (401, 'BezrabotniqNomad1', 'A!@#a123', 'vankataDge', 'Dge', 'Stefi', '1pi4avu66cia@abv.bg', '0888696960',
        'https://res.cloudinary.com/dhixuwvrk/image/upload/v1710947410/rrsjms2oelonnqiukw7q.png', false, false,
        '2024-03-20 15:10:11.797302', false, '085e6cd3-f32c-4727-a605-eab979c585a9', 'Zad bloka 99', 9, 'PlevenDge',
        'България');
INSERT INTO public.users (user_id, username, password, first_name, middle_name, last_name, email, phone, photo,
                            verified, blocked, stamp_created, is_deleted, confirmation_token, street, number, city,
                            country)
VALUES (368, 'newUser', 'Dc.7tiLBYzA4.y5', 'UserEdited', 'NewsFlash', 'Name', 'rawice2187@fryshare.commm', '1234567890',
        'https://res.cloudinary.com/dhixuwvrk/image/upload/v1710904065/oe3j7wp7pvmtksqawx2c.png', true, true,
        '2024-03-20 01:56:14.098475', false, null, '1', 2, 'Sofia', 'Bulgaria');
INSERT INTO public.users (user_id, username, password, first_name, middle_name, last_name, email, phone, photo,
                            verified, blocked, stamp_created, is_deleted, confirmation_token, street, number, city,
                            country)
VALUES (380, 'asdf', 'Asdf!234', 'Asdf', 'Asdf', 'Asdf', 'asdf@asdf.com', '0878555878', null, false, false,
        '2024-03-20 11:34:21.407178', false, 'b8dc2d71-a7bc-40cf-8464-4401008ae912', 'Asdf', 2, 'S', 'Asdf');
INSERT INTO public.users (user_id, username, password, first_name, middle_name, last_name, email, phone, photo,
                            verified, blocked, stamp_created, is_deleted, confirmation_token, street, number, city,
                            country)
VALUES (385, 'doncho', '1234', 'Doncho', 'Donchov', 'Donchov', 'doncho@donchomail.com', '0834563456',
        'https://res.cloudinary.com/dhixuwvrk/image/upload/v1710936747/hkagcrrxzzsd5cxfte2w.jpg', false, false,
        '2024-03-20 14:08:48.327608', false, '82105b83-36fe-41be-a9ea-3f1b590dcc89', 'DonchoStr', 25, 'Sofia',
        'Bulgaria');
