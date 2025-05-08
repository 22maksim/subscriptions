CREATE TABLE subscriptions (
                               id BIGSERIAL PRIMARY KEY,
                               user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                               service_name VARCHAR(255) NOT NULL,
                               start_date DATE NOT NULL,
                               end_date DATE,
                               price DECIMAL(10, 2) NOT NULL,
                               currency VARCHAR(3) NOT NULL,
                               status VARCHAR(20) NOT NULL,
                               created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
