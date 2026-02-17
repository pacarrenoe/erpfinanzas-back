-- core.financial_period ya existe en tu V1. Asegura columnas/constraints y agrega income.

-- Asegura FK user_id en financial_period si tu V1 la creó sin user_id.
DO $$
BEGIN
  IF NOT EXISTS (
    SELECT 1
    FROM information_schema.columns
    WHERE table_schema='core' AND table_name='financial_period' AND column_name='user_id'
  ) THEN
    ALTER TABLE core.financial_period ADD COLUMN user_id UUID;
    ALTER TABLE core.financial_period
      ADD CONSTRAINT fk_period_user
      FOREIGN KEY (user_id) REFERENCES core.app_user(id);
    ALTER TABLE core.financial_period ALTER COLUMN user_id SET NOT NULL;
  END IF;
END $$;

-- Un solo periodo abierto por usuario (mejor que global)
DO $$
BEGIN
  IF NOT EXISTS (
    SELECT 1 FROM pg_indexes WHERE schemaname='core' AND indexname='ux_period_single_open_per_user'
  ) THEN
    CREATE UNIQUE INDEX ux_period_single_open_per_user
    ON core.financial_period (user_id)
    WHERE end_date IS NULL;
  END IF;
END $$;

-- Income
DO $$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'income_type' AND typnamespace = 'core'::regnamespace) THEN
    CREATE TYPE core.income_type AS ENUM ('SALARY','BONUS','AGUINALDO','SALE','REFUND','OTHER');
  END IF;
END $$;

CREATE TABLE IF NOT EXISTS core.income (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id UUID NOT NULL REFERENCES core.app_user(id),
  period_id UUID REFERENCES core.financial_period(id),
  income_date DATE NOT NULL,
  amount NUMERIC(15,2) NOT NULL CHECK (amount >= 0),
  type core.income_type NOT NULL,
  description TEXT,
  is_period_start BOOLEAN NOT NULL DEFAULT false,
  created_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE INDEX IF NOT EXISTS ix_income_user_date ON core.income(user_id, income_date);
CREATE INDEX IF NOT EXISTS ix_income_period ON core.income(period_id);
