CREATE TABLE IF NOT EXISTS vehicle (
    id UUID PRIMARY KEY NOT NULL,
    license_plate VARCHAR(20) NOT NULL UNIQUE,
    color VARCHAR(50),
    vehicle_type_id UUID,  -- Referência para a tabela vehicleTypes
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_vehicle_type FOREIGN KEY (vehicle_type_id) REFERENCES vehicleTypes(id) ON DELETE SET NULL
    );