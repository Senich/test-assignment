CREATE SEQUENCE IF NOT EXISTS sector_id_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE sector
(
    id             bigserial           NOT NULL,
    type           VARCHAR(255),
    exposure_limit DOUBLE PRECISION NOT NULL,
    CONSTRAINT pk_sector PRIMARY KEY (id)
);

CREATE SEQUENCE IF NOT EXISTS trade_id_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE trade
(
    id            bigserial           NOT NULL,
    symbol        VARCHAR(255),
    price         DOUBLE PRECISION NOT NULL,
    quantity      BIGINT           NOT NULL,
    sector_id     BIGINT,
    security_type INTEGER,
    trade_type    VARCHAR(255),
    CONSTRAINT pk_trade PRIMARY KEY (id)
);

ALTER TABLE trade
    ADD CONSTRAINT FK_TRADE_ON_SECTOR FOREIGN KEY (sector_id) REFERENCES sector (id);

CREATE SEQUENCE IF NOT EXISTS portfolio_id_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE portfolio
(
    id             bigserial           NOT NULL,
    name           VARCHAR(255),
    max_fund_value DOUBLE PRECISION NOT NULL,
    CONSTRAINT pk_portfolio PRIMARY KEY (id)
);

CREATE TABLE portfolio_security_sectors
(
    portfolio_id        BIGINT NOT NULL,
    security_sectors_id BIGINT NOT NULL
);

CREATE TABLE portfolio_trades
(
    portfolio_id BIGINT NOT NULL,
    trades_id    BIGINT NOT NULL
);

ALTER TABLE portfolio_trades
    ADD CONSTRAINT uc_portfolio_trades_trades UNIQUE (trades_id);

ALTER TABLE portfolio_security_sectors
    ADD CONSTRAINT fk_porsecsec_on_portfolio FOREIGN KEY (portfolio_id) REFERENCES portfolio (id);

ALTER TABLE portfolio_security_sectors
    ADD CONSTRAINT fk_porsecsec_on_sector FOREIGN KEY (security_sectors_id) REFERENCES sector (id);

ALTER TABLE portfolio_trades
    ADD CONSTRAINT fk_portra_on_portfolio FOREIGN KEY (portfolio_id) REFERENCES portfolio (id);

ALTER TABLE portfolio_trades
    ADD CONSTRAINT fk_portra_on_trade FOREIGN KEY (trades_id) REFERENCES trade (id);