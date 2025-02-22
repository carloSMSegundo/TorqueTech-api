-- V2__create_stock_tables.sql

-- Tabela Local
CREATE TABLE local (
    id UUID PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    garage_id UUID NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_local_garage FOREIGN KEY (garage_id) REFERENCES garage(id) ON DELETE SET NULL
);

-- Tabela StockItem
CREATE TABLE stock_item (
    id UUID PRIMARY KEY NOT NULL,
    acquisition_price BIGINT,
    quantity INTEGER,
    item_id UUID NOT NULL,
    local_id UUID NOT NULL,
    garage_id UUID NOT NULL,
    acquisition_at DATE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_stock_item_item FOREIGN KEY (item_id) REFERENCES item(id),
    CONSTRAINT fk_stock_item_local FOREIGN KEY (local_id) REFERENCES local(id),
    CONSTRAINT fk_stock_item_garage FOREIGN KEY (garage_id) REFERENCES garage(id)
);

-- Tabela StockTransaction
CREATE TABLE stock_transaction (
    id UUID PRIMARY KEY NOT NULL,
    price BIGINT,
    quantity INTEGER,
    transaction_type VARCHAR(50),
    stock_item_id UUID NOT NULL,
    garage_id UUID NOT NULL,
    owner_id UUID NOT NULL,
    transaction_date TIMESTAMPTZ,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_stock_transaction_stock_item FOREIGN KEY (stock_item_id) REFERENCES stock_item(id),
    CONSTRAINT fk_stock_transaction_garage FOREIGN KEY (garage_id) REFERENCES garage(id),
    CONSTRAINT fk_stock_transaction_owner FOREIGN KEY (owner_id) REFERENCES users(id)
);
