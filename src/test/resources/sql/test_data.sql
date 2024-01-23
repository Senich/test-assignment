-- Inserting test data into the 'portfolio' table
INSERT INTO credit_check.portfolio (id, name, max_fund_value)
VALUES (1, 'Portfolio A', 70600.0),
       (2, 'Portfolio B', 80000.0),
       (3, 'Portfolio C', 120000.0)
ON CONFLICT (id) DO NOTHING;

-- Inserting test data into the 'sector' table
INSERT INTO credit_check.sector (id, type, exposure_limit, portfolio_id)
VALUES (1, 'INFORMATION_TECHNOLOGY', 70000.0, 1),
       (2, 'FINANCIALS', 30000.0, 1),
       (3, 'HEALTH_CARE', 45000.0, 1),
       (4, 'FINANCIALS', 30000.0, 2)
ON CONFLICT (id) DO NOTHING;

-- Inserting test data into the 'trade' table
INSERT INTO credit_check.trade (id, symbol, price, quantity, sector_id, security_type, portfolio_id)
VALUES (1, 'AAPL', 20.0, 750, 1, 'STOCK', 1),
       (2, 'GOOGL', 2000.0, 20, 1, 'STOCK', 1),
       (3, 'JPM', 120.0, 100, 2, 'STOCK', 1),
       (4, 'PFE', 45.0, 80, 3, 'STOCK', 1),
       (5, 'JPM', 120.0, 100, 2, 'STOCK', 2)
ON CONFLICT (id) DO NOTHING;

-- Inserting test data into the 'trader' table
INSERT INTO credit_check.trader (id, name)
VALUES (1, 'John Doe'),
       (2, 'Jane Smith')
ON CONFLICT (id) DO NOTHING;

-- Inserting test data into the 'trader_portfolios' table
INSERT INTO credit_check.trader_portfolios (trader_id, portfolios_id)
VALUES (1, 1),
       (1, 2),
       (2, 1);
