DROP TABLE IF EXISTS contract;
CREATE TABLE contract
(
    id                  uuid                                NOT NULL,
    date_start          VARCHAR(255),
    date_end            VARCHAR(255),
    date_send           TIMESTAMP,
    date_create         TIMESTAMP DEFAULT current_timestamp,
    contract_number     VARCHAR(255),
    contract_name       VARCHAR(255),
    client_api          VARCHAR(255),
    contractual_parties jsonb,
    PRIMARY KEY (id)
);
CREATE INDEX contract_number_index ON contract (contract_number);
