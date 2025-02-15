CREATE TABLE IF NOT EXISTS garage (
    id UUID PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL,
    cnpj VARCHAR(20) NOT NULL UNIQUE,
    owner_id UUID UNIQUE,
    address_id UUID UNIQUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_garage_owner FOREIGN KEY (owner_id) REFERENCES users(id) ON DELETE SET NULL,
    CONSTRAINT fk_garage_address FOREIGN KEY (address_id) REFERENCES addresses(id) ON DELETE SET NULL
);
