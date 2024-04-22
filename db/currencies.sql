create table currencies
(
    currency_id serial
        primary key,
    name        varchar(20) not null
        constraint currencies_pk_2
            unique,
    symbol      varchar,
    ticker      varchar(3)
        constraint currencies_pk
            unique,
    rate_to_usd double precision
);

alter table currencies
    owner to doadmin;

INSERT INTO wallt_db.currencies (currency_id, name, symbol, ticker, rate_to_usd)
VALUES (2, 'US Dollar', '$', 'USD', 1);
INSERT INTO wallt_db.currencies (currency_id, name, symbol, ticker, rate_to_usd)
VALUES (4, 'Bitcoin', '₿', 'BTC', 62549);
INSERT INTO wallt_db.currencies (currency_id, name, symbol, ticker, rate_to_usd)
VALUES (3, 'Euro', '€', 'EUR', 1.08644);
INSERT INTO wallt_db.currencies (currency_id, name, symbol, ticker, rate_to_usd)
VALUES (1, 'Bulgarian Lev', 'лв', 'BGN', 0.555502);
INSERT INTO wallt_db.currencies (currency_id, name, symbol, ticker, rate_to_usd)
VALUES (5, 'British Pound', '£', 'GBP', 1.27184);
