CREATE TABLE IF NOT EXISTS customer (
    id UUID PRIMARY KEY NOT NULL,
    status VARCHAR(50) NOT NULL,
    name VARCHAR(255) NOT NULL,
    profile_id UUID,
    garage_id UUID NOT NULL,
    owner_id UUID NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_customer_profile FOREIGN KEY (profile_id) REFERENCES profiles(id) ON DELETE SET NULL,
    CONSTRAINT fk_customer_garage FOREIGN KEY (garage_id) REFERENCES garage(id),
    CONSTRAINT fk_customer_owner FOREIGN KEY (owner_id) REFERENCES users(id) ON DELETE SET NULL
);
