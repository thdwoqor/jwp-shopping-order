DROP TABLE IF EXISTS order_coupon;
DROP TABLE IF EXISTS order_item;
DROP TABLE IF EXISTS cart_order;
DROP TABLE IF EXISTS member_coupon;
DROP TABLE IF EXISTS cart_item;
DROP TABLE IF EXISTS coupon;
DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS product;

CREATE TABLE if not exists product
(
    id        BIGINT PRIMARY KEY AUTO_INCREMENT,
    name      VARCHAR(255) NOT NULL,
    price     INT          NOT NULL,
    image_url VARCHAR(255) NOT NULL
);

CREATE TABLE if not exists member
(
    id       BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    email    VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE if not exists cart_item
(
    id         BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    member_id  BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity   INT    NOT NULL,
    FOREIGN KEY (member_id) REFERENCES member (id),
    FOREIGN KEY (product_id) REFERENCES product (id)
);

CREATE TABLE if not exists coupon
(
    id            BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    discount_type VARCHAR(255) NOT NULL,
    amount        INT          NOT NULL
);

CREATE TABLE if not exists member_coupon
(
    id        BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL,
    coupon_id BIGINT NOT NULL,
    used      boolean default false,
    FOREIGN KEY (member_id) REFERENCES member (id),
    FOREIGN KEY (coupon_id) REFERENCES coupon (id)
);

CREATE TABLE if not exists cart_order
(
    id           BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    member_id    BIGINT NOT NULL,
    delivery_fee BIGINT NOT NULL,
    FOREIGN KEY (member_id) REFERENCES member (id)
);

CREATE TABLE if not exists order_item
(
    id         BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    order_id   BIGINT       NOT NULL,
    product_id BIGINT       NOT NULL,
    name       VARCHAR(255) NOT NULL,
    price      INT          NOT NULL,
    image_url  VARCHAR(255) NOT NULL,
    quantity   INT          NOT NULL,
    FOREIGN KEY (order_id) REFERENCES cart_order (id)
);


CREATE TABLE if not exists order_coupon
(
    id               BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    order_item_id    BIGINT NOT NULL,
    member_coupon_id BIGINT NOT NULL,
    FOREIGN KEY (order_item_id) REFERENCES order_item (id),
    FOREIGN KEY (member_coupon_id) REFERENCES member_coupon (id)
);

