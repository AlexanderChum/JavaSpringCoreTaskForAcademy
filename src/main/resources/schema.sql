CREATE TABLE IF NOT EXISTS customer (
    customer_id UUID PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    contact_number VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS product (
    product_id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DOUBLE PRECISION NOT NULL,
    quantity_in_stock INT NOT NULL
);

CREATE TABLE IF NOT EXISTS orders (
    order_id UUID PRIMARY KEY,
    customer_id UUID NOT NULL,
    order_date TIMESTAMP NOT NULL,
    shipping_address VARCHAR(500) NOT NULL,
    total_price DOUBLE PRECISION NOT NULL,
    order_status VARCHAR(20) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS order_product (
    order_id UUID NOT NULL,
    product_id UUID NOT NULL,
    PRIMARY KEY (order_id, product_id),
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE CASCADE
);