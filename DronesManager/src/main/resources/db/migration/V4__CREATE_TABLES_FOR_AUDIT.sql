CREATE TABLE IF NOT EXISTS drones_manager.drones_aud
(
    id              UUID    NOT NULL,
    rev             INTEGER NOT NULL,
    revtype         SMALLINT,
    battery_percent DOUBLE PRECISION,
    PRIMARY KEY (id, REV)
);

CREATE TABLE IF NOT EXISTS public.revinfo
(
    rev      INTEGER PRIMARY KEY,
    revtstmp BIGINT
);

create sequence if not exists drones_manager.hibernate_sequence;
create sequence if not exists public.hibernate_sequence;

ALTER TABLE drones_manager.drones_aud
    ADD CONSTRAINT FK_DRONES_AUD_ON_REVINFO FOREIGN KEY (rev) REFERENCES public.revinfo (rev);