CREATE TABLE IF NOT EXISTS item (
    id UUID PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(100) NOT NULL,
    description TEXT,
    garage_id UUID NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_item_garage FOREIGN KEY (garage_id) REFERENCES garage(id)
);
