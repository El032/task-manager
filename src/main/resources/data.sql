INSERT INTO users (name, email) VALUES
  ('Ivan', 'ivan@example.com'),
  ('Anna', 'anna@example.com')
ON CONFLICT (email) DO NOTHING;