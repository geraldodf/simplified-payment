CREATE TABLE transfers (
    id SERIAL PRIMARY KEY,
    user_from_fk INT,
    user_to_fk INT,
    FOREIGN KEY (user_from_fk) REFERENCES users (id),
    FOREIGN KEY (user_to_fk) REFERENCES users (id),
    transfer_value DECIMAL(6,2)
)
