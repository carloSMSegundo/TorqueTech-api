ALTER TABLE work_order
    ADD COLUMN stock_transaction_id UUID,
    ADD CONSTRAINT fk_stock_transaction FOREIGN KEY (stock_transaction_id) REFERENCES stock_transaction(id);
