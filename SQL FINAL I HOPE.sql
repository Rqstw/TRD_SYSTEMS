DROP TABLE IF EXISTS trades CASCADE;
DROP TABLE IF EXISTS holdings CASCADE;
DROP TABLE IF EXISTS assets CASCADE;
DROP TABLE IF EXISTS categories CASCADE;
DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  name VARCHAR(60) NOT NULL,
  balance NUMERIC(12,2) NOT NULL DEFAULT 0,
  role VARCHAR(10) NOT NULL DEFAULT 'USER',
  banned BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE categories (
  id SERIAL PRIMARY KEY,
  name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE assets (
  id SERIAL PRIMARY KEY,
  symbol VARCHAR(10) UNIQUE NOT NULL,
  price NUMERIC(12,2) NOT NULL,
  category_id INT REFERENCES categories(id)
);

CREATE TABLE holdings (
  id SERIAL PRIMARY KEY,
  user_id INT NOT NULL REFERENCES users(id),
  asset_id INT NOT NULL REFERENCES assets(id),
  qty INT NOT NULL DEFAULT 0,
  UNIQUE(user_id, asset_id)
);

CREATE TABLE trades (
  id SERIAL PRIMARY KEY,
  user_id INT NOT NULL REFERENCES users(id),
  asset_id INT NOT NULL REFERENCES assets(id),
  side VARCHAR(4) NOT NULL,
  qty INT NOT NULL,
  price NUMERIC(12,2) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

INSERT INTO users(name, balance, role, banned) VALUES ('admin', 100000, 'ADMIN', false);
INSERT INTO users(name, balance, role, banned) VALUES ('user1',  10000,  'USER',  false);

INSERT INTO categories(name) VALUES ('tech');
INSERT INTO assets(symbol, price, category_id) VALUES ('AAPL', 150, 1);
