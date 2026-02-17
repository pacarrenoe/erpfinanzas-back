CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE SCHEMA IF NOT EXISTS core;
CREATE SCHEMA IF NOT EXISTS analytics;

CREATE TABLE IF NOT EXISTS core.app_user (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  email TEXT NOT NULL UNIQUE,
  password_hash TEXT NOT NULL,
  display_name TEXT,
  created_at TIMESTAMP NOT NULL DEFAULT now(),
  last_login_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS core.financial_period (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  start_date DATE NOT NULL,
  end_date DATE,
  created_at TIMESTAMP NOT NULL DEFAULT now()
);

-- Solo 1 periodo abierto (end_date NULL)
CREATE UNIQUE INDEX IF NOT EXISTS ux_period_single_open
ON core.financial_period ((1))
WHERE end_date IS NULL;
