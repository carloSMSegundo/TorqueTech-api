CREATE TABLE IF NOT EXISTS manager (
    id UUID PRIMARY KEY NOT NULL,
    garage_id UUID UNIQUE NOT NULL,
    user_id UUID UNIQUE NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_manager_garage FOREIGN KEY (garage_id) REFERENCES garage(id),
    CONSTRAINT fk_manager_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
