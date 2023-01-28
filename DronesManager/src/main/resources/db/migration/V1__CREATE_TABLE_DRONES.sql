CREATE TABLE IF NOT EXISTS drones_manager.drones
(
    id              UUID PRIMARY KEY,
    serialNumber    VARCHAR(100) UNIQUE NOT NULL,
    model           VARCHAR(255)        NOT NULL,
    weightLimit     DOUBLE PRECISION    NOT NULL,
    battery_percent DOUBLE PRECISION    NOT NULL,
    state           VARCHAR(255)        NOT NULL
);