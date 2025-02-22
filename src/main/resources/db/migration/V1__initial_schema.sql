-- V1__initial_schema.sql

-- Tabela Address
CREATE TABLE addresses (
    id UUID PRIMARY KEY NOT NULL,
    street VARCHAR(255),
    number VARCHAR(50),
    cep VARCHAR(20),
    province VARCHAR(100),
    city VARCHAR(100),
    state VARCHAR(100),
    created_at TIMESTAMPTZ NOT NULL
);

-- Tabela Auth
CREATE TABLE auths (
    id UUID PRIMARY KEY NOT NULL,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL
);

-- Tabela Privilege
CREATE TABLE privileges (
    id UUID PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL,
    is_signature_revoked BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMPTZ NOT NULL
);

-- Tabela Role
CREATE TABLE roles (
    id UUID PRIMARY KEY NOT NULL,
    name VARCHAR(255) UNIQUE NOT NULL,
    created_at TIMESTAMPTZ NOT NULL
);

-- Tabela Profile
CREATE TABLE profiles (
    id UUID PRIMARY KEY NOT NULL,
    photo TEXT,
    document VARCHAR(255),
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(50),
    birth_date DATE,
    address_id UUID,
    created_at TIMESTAMPTZ NOT NULL,
    CONSTRAINT fk_address FOREIGN KEY (address_id) REFERENCES addresses(id) ON DELETE SET NULL
);

-- Tabela User
CREATE TABLE users (
    id UUID PRIMARY KEY NOT NULL,
    status VARCHAR(50),
    auth_id UUID,
    profile_id UUID,
    role_id UUID,
    created_at TIMESTAMPTZ NOT NULL,
    CONSTRAINT fk_auth FOREIGN KEY (auth_id) REFERENCES auths(id) ON DELETE CASCADE,
    CONSTRAINT fk_profile FOREIGN KEY (profile_id) REFERENCES profiles(id) ON DELETE SET NULL,
    CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE SET NULL
);

-- Tabela de relacionamento entre Role e Privilege
CREATE TABLE privileges_on_roles (
    role_id UUID NOT NULL,
    privilege_id UUID NOT NULL,
    PRIMARY KEY (role_id, privilege_id),
    CONSTRAINT fk_role_privilege FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE,
    CONSTRAINT fk_privilege_role FOREIGN KEY (privilege_id) REFERENCES privileges(id) ON DELETE CASCADE
);

-- Tabela de relacionamento entre User e Privilege
CREATE TABLE privileges_on_users (
    user_id UUID NOT NULL,
    privilege_id UUID NOT NULL,
    PRIMARY KEY (user_id, privilege_id),
    CONSTRAINT fk_user_privilege FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_privilege_user FOREIGN KEY (privilege_id) REFERENCES privileges(id) ON DELETE CASCADE
);

-- Inserir roles padrão na tabela roles
INSERT INTO roles (id, name, created_at)
VALUES
    ('9a0499e2-7ee7-4077-8bec-2433bc0c5a73', 'ROLE_USER', NOW()),
    ('1e883ae5-662b-460f-a18f-512088c186ae', 'ROLE_MANAGER', NOW()),
    ('441606b1-b1f5-4d6a-9c69-7749ae48fdbc', 'ROLE_ADMIN', NOW()),
    ('7fcb9fe2-7d3d-43f1-8f4e-9b63fd902b28', 'ROLE_SUPER_ADMIN', NOW());
