CREATE SCHEMA IF NOT EXISTS credit_check;

CREATE SEQUENCE IF NOT EXISTS portfolio_id_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE portfolio
(
    id             BIGINT           NOT NULL,
    name           VARCHAR(255),
    max_fund_value DOUBLE PRECISION NOT NULL,
    CONSTRAINT pk_portfolio PRIMARY KEY (id)
);

CREATE SEQUENCE IF NOT EXISTS sector_id_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE sector
(
    id             BIGINT NOT NULL,
    type           VARCHAR(255),
    exposure_limit DOUBLE PRECISION NOT NULL,
    portfolio_id   BIGINT,
    CONSTRAINT pk_sector PRIMARY KEY (id)
);

ALTER TABLE sector
    ADD CONSTRAINT FK_SECTOR_ON_PORTFOLIO FOREIGN KEY (portfolio_id) REFERENCES portfolio (id);

CREATE SEQUENCE IF NOT EXISTS trade_id_sequence START WITH 10000 INCREMENT BY 1;

CREATE TABLE trade
(
    id            BIGINT           NOT NULL,
    symbol        VARCHAR(255),
    price         DOUBLE PRECISION NOT NULL,
    quantity      INTEGER          NOT NULL,
    sector_id     BIGINT,
    security_type VARCHAR(255),
    trade_type    VARCHAR(255),
    portfolio_id  BIGINT,
    trader_id     BIGINT,
    CONSTRAINT pk_trade PRIMARY KEY (id)
);

ALTER TABLE trade
    ADD CONSTRAINT FK_TRADE_ON_PORTFOLIO FOREIGN KEY (portfolio_id) REFERENCES portfolio (id);

ALTER TABLE trade
    ADD CONSTRAINT FK_TRADE_ON_SECTOR FOREIGN KEY (sector_id) REFERENCES sector (id);

CREATE SEQUENCE IF NOT EXISTS trader_id_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE trader
(
    id   BIGINT NOT NULL,
    name VARCHAR(255),
    CONSTRAINT pk_trader PRIMARY KEY (id)
);

ALTER TABLE trade
    ADD CONSTRAINT FK_TRADE_ON_TRADER FOREIGN KEY (trader_id) REFERENCES trader (id);

CREATE TABLE trader_portfolios
(
    trader_id     BIGINT NOT NULL,
    portfolios_id BIGINT NOT NULL
);

ALTER TABLE trader_portfolios
    ADD CONSTRAINT fk_trapor_on_portfolio FOREIGN KEY (portfolios_id) REFERENCES portfolio (id);

ALTER TABLE trader_portfolios
    ADD CONSTRAINT fk_trapor_on_trader FOREIGN KEY (trader_id) REFERENCES trader (id);