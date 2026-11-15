CREATE TABLE products (
                          id          UUID           NOT NULL,
                          name        VARCHAR(150)   NOT NULL,
                          description VARCHAR(2000),
                          price       NUMERIC(12, 2) NOT NULL,
                          sku         VARCHAR(50)    NOT NULL,
                          active      BOOLEAN        NOT NULL,
                          created_at  TIMESTAMPTZ    NOT NULL,
                          updated_at  TIMESTAMPTZ    NOT NULL,

                          CONSTRAINT pk_products PRIMARY KEY (id),
                          CONSTRAINT uk_products_sku UNIQUE (sku),
                          CONSTRAINT ck_products_price_positive CHECK (price > 0)
);


CREATE INDEX idx_products_active_created_at ON products (active, created_at DESC);
