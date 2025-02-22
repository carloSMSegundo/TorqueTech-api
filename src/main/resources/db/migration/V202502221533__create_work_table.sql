CREATE TABLE IF NOT EXISTS Work (
    id UUID PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL,
    start_at TIMESTAMP NOT NULL,
    concluded_at TIMESTAMP NOT NULL,
    expected_at TIMESTAMP NOT NULL,
    total_cost BIGINT NULL,
    price BIGINT NULL,
    description TEXT,

    mechanic_id UUID NULL,
    owner_id UUID NOT NULL,
    vehicle_id UUID NOT NULL,
    customer_id UUID NOT NULL,
    garage_id UUID NOT NULL,
    CONSTRAINT fk_work_mechanic FOREIGN KEY (mechanic_id) REFERENCES mechanic(id),
    CONSTRAINT fk_work_owner FOREIGN KEY (owner_id) REFERENCES users(id),
    CONSTRAINT fk_work_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicle(id),
    CONSTRAINT fk_work_customer_id FOREIGN KEY (customer_id) REFERENCES customer(id),
    CONSTRAINT fk_work_garage FOREIGN KEY (garage_id) REFERENCES garage(id)
 );