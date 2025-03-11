CREATE TABLE notification (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    message TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    status VARCHAR(10) NOT NULL DEFAULT 'UNREAD',
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
