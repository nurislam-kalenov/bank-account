CREATE TABLE "user_account"
(
    id           BIGSERIAL PRIMARY KEY NOT NULL,
    customer_id  BIGINT UNIQUE         NOT NULL,
    country_code VARCHAR(2)            NOT NULL
);

CREATE TABLE "account"
(
    id              BIGSERIAL PRIMARY KEY                  NOT NULL,
    user_account_id BIGSERIAL REFERENCES user_account (id) NOT NULL,
    currency        VARCHAR(3)                             NOT NULL,
    amount          DECIMAL(17, 3)                         NOT NULL,
    version         BIGINT                                 NOT NULL DEFAULT 1
);

