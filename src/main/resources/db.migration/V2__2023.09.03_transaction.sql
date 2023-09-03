CREATE TABLE "transaction"
(
    id               BIGSERIAL PRIMARY KEY                  NOT NULL,
    account_id       BIGSERIAL REFERENCES account (id)      NOT NULL,
    currency         VARCHAR(3)                             NOT NULL,
    amount           DECIMAL(17, 3)                         NOT NULL,
    description      VARCHAR(200)                           NOT NULL,
    transaction_type VARCHAR(3)                             NOT NULL,
    date_created     TIMESTAMP WITH TIME ZONE DEFAULT now() NOT NULL
);
