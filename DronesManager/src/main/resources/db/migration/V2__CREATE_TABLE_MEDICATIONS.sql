CREATE TABLE IF NOT EXISTS drones_manager.medications
(
    id        UUID             PRIMARY KEY ,
    name      VARCHAR(255)     NOT NULL,
    weight    DOUBLE PRECISION NOT NULL,
    code      VARCHAR(255)     NOT NULL,
    image     UUID,
    drones_id UUID
);

ALTER TABLE drones_manager.medications
    ADD CONSTRAINT FK_MEDICATIONS_ON_DRONES FOREIGN KEY (drones_id) REFERENCES drones_manager.drones (id);