CREATE TABLE transactions (
    id SERIAL PRIMARY KEY UNIQUE,
    account_id BIGINT NOT NULL,
    amount NUMERIC(10,2) NOT NULL,
    operation_type VARCHAR(6) NOT NULL,
    transaction_date DATE NOT NULL
);
