-- WARNING: This schema is for context only and is not meant to be run.
-- Table order and constraints may not be valid for execution.

CREATE TABLE category (
  category_id BIGINT AUTO_INCREMENT NOT NULL,
  name VARCHAR(255) NOT NULL,
  description VARCHAR(255),
  CONSTRAINT category_pkey PRIMARY KEY (category_id)
);

CREATE TABLE product (
  product_id BIGINT AUTO_INCREMENT NOT NULL,
  name VARCHAR(255) NOT NULL,
  description VARCHAR(255),
  price DECIMAL(10,2) NOT NULL,
  url_image VARCHAR(255),
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  category_id BIGINT,
  CONSTRAINT product_pkey PRIMARY KEY (product_id),
  CONSTRAINT products_category_id_fkey FOREIGN KEY (category_id) REFERENCES category(category_id)
);

CREATE TABLE type (
  type_id BIGINT AUTO_INCREMENT NOT NULL,
  name VARCHAR(255) NOT NULL UNIQUE,
  CONSTRAINT type_pkey PRIMARY KEY (type_id)
);

CREATE TABLE user (
  user_id BIGINT AUTO_INCREMENT NOT NULL,
  name VARCHAR(255) NOT NULL,
  lastname VARCHAR(255) NOT NULL,
  mail VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  type_id BIGINT NOT NULL,
  CONSTRAINT user_pkey PRIMARY KEY (user_id),
  CONSTRAINT user_type_id_fkey FOREIGN KEY (type_id) REFERENCES type(type_id)
);

CREATE TABLE ticket (
  ticket_id BIGINT AUTO_INCREMENT NOT NULL,
  user_id BIGINT NOT NULL,
  purchase_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  total DECIMAL(10,2) NOT NULL,
  CONSTRAINT ticket_pkey PRIMARY KEY (ticket_id),
  CONSTRAINT ticket_user_id_fkey FOREIGN KEY (user_id) REFERENCES user(user_id)
);

CREATE TABLE ticket_detail (
  ticket_detail_id BIGINT AUTO_INCREMENT NOT NULL,
  ticket_id BIGINT NOT NULL,
  product_id BIGINT NOT NULL,
  amount DECIMAL(10,2) NOT NULL,
  subtotal DECIMAL(10,2) NOT NULL,
  CONSTRAINT ticket_detail_pkey PRIMARY KEY (ticket_detail_id),
  CONSTRAINT ticket_detail_ticket_id_fkey FOREIGN KEY (ticket_id) REFERENCES ticket(ticket_id),
  CONSTRAINT ticket_detail_product_id_fkey FOREIGN KEY (product_id) REFERENCES product(product_id)
);