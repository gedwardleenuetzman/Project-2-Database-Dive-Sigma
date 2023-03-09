-- creating a table for ingredients
CREATE table ingredients(
    ID int PRIMARY KEY,
    name text,
    quantity int
);

-- static table that will not change for products
CREATE table products_cfa(
    ID int PRIMARY KEY,
    name text,
    price float
);

-- table for order which would be dynamic
CREATE table orders(
    ID int PRIMARY KEY,
    totalprice text
);

-- table for whoever is using this GUI / database
CREATE table users(
    ID int PRIMARY KEY,
    name text,
    is_manager bool
);

-- table for relations between ingredients and products
CREATE table product_ingredients(
    prod_ID int,
    item_ID int,
    quantity int
);

-- table to hold what items each order has
CREATE table order_products(
    order_ID int,
    prod_ID int,
    quantity int
);