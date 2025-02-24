DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = 'cadastro-clientes') THEN
        CREATE DATABASE "cadastro-clientes"
        WITH
        OWNER = postgres
        ENCODING = 'UTF8'
        LC_COLLATE = 'en_US.utf8'
        LC_CTYPE = 'en_US.utf8'
        LOCALE_PROVIDER = 'libc'
        TABLESPACE = pg_default
        CONNECTION LIMIT = -1
        IS_TEMPLATE = False;
    END IF;
END
$$;

-- DROP TABLE IF EXISTS public.clientes;

CREATE TABLE IF NOT EXISTS public.clientes
(
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    cpf VARCHAR(255) NOT NULL UNIQUE,
    cor VARCHAR(255) NOT NULL,
    observacao VARCHAR(255)
);

ALTER TABLE IF EXISTS public.clientes
    OWNER to postgres;
