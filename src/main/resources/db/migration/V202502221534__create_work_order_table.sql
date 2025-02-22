CREATE TABLE IF NOT EXISTS work_order (
   id UUID PRIMARY KEY NOT NULL,
   work_id UUID NOT NULL,
   start_at TIMESTAMP NOT NULL,
   concluded_at TIMESTAMP NOT NULL,
   expected_at TIMESTAMP NOT NULL,
   status VARCHAR(50) NOT NULL,
   title VARCHAR(255) NOT NULL,
   description TEXT,
   note TEXT,
   cost BIGINT,

   CONSTRAINT fk_work FOREIGN KEY (work_id) REFERENCES work(id)
);