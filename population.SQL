-- population SQL products

INSERT INTO products_cfa VALUES (0, 'chicken_sandwich', 4.85);

INSERT INTO products_cfa VALUES (1, 'deluxe_sandwich', 5.55);

INSERT INTO products_cfa VALUES (2, 'spicy_sandwich', 5.25);

INSERT INTO products_cfa VALUES (3, 'spicy_deluxe', 5.95);

INSERT INTO products_cfa VALUES (4, 'chicken_biscuit', 3.55);

INSERT INTO products_cfa VALUES (5, 'spicy_biscuit', 4.49);

INSERT INTO products_cfa VALUES (6, 'hash_browns', 1.55);

INSERT INTO products_cfa VALUES (7, 'five_nuggets', 3.05);

INSERT INTO products_cfa VALUES (8, 'eight_nuggets', 4.85);

INSERT INTO products_cfa VALUES (9, 'twelve_nuggets', 6.85);

INSERT INTO products_cfa VALUES (10, 'cobb_salad', 9.49);

INSERT INTO products_cfa VALUES (11, 'market_salad', 9.69);

INSERT INTO products_cfa VALUES (12, 'waffle_fries', 2.35);

INSERT INTO products_cfa VALUES (13, 'side_salad', 2.35);

INSERT INTO products_cfa VALUES (14, 'waffle_chips', 2.09);

INSERT INTO products_cfa VALUES (15, 'frosted_lemonade', 4.35);

INSERT INTO products_cfa VALUES (16, 'frosted_coffee', 4.35);

INSERT INTO products_cfa VALUES (17, 'chocolate_cookie', 1.65);

INSERT INTO products_cfa VALUES (18, 'lemonade', 2.55);

INSERT INTO products_cfa VALUES (19, 'ice_tea', 2.19);

INSERT INTO products_cfa VALUES (20, 'sweet_tea', 2.19);

INSERT INTO products_cfa VALUES (21, 'water_bottle', 2.25);

INSERT INTO products_cfa VALUES (22, 'cfa_sauce', 0);

INSERT INTO products_cfa VALUES (23, 'ranch_sauce', 0);

INSERT INTO products_cfa VALUES (24, 'honey_mustard', 0);

INSERT INTO products_cfa VALUES (25, 'bbq_sauce', 0);

-- populating SQL inventory

INSERT INTO ingredients VALUES (0, 'chicken_breast_fried', 100);

INSERT INTO ingredients VALUES (1, 'spicy_breast_fried', 100);

INSERT INTO ingredients VALUES (2, 'buns', 100);

INSERT INTO ingredients VALUES (3, 'biscuits', 100);

INSERT INTO ingredients VALUES (4, 'pickles', 100);

INSERT INTO ingredients VALUES (5, 'lettuce', 100);

INSERT INTO ingredients VALUES (6, 'tomatos', 100);

INSERT INTO ingredients VALUES (7, 'cheese', 100);

INSERT INTO ingredients VALUES (8, 'hash_browns', 100);

INSERT INTO ingredients VALUES (9, 'nuggets', 100);

INSERT INTO ingredients VALUES (10, 'cobb_salad', 100);

INSERT INTO ingredients VALUES (11, 'market_salad', 100);

INSERT INTO ingredients VALUES (12, 'waffle_fries', 100);

INSERT INTO ingredients VALUES (13, 'side_salad', 100);

INSERT INTO ingredients VALUES (14, 'waffle_chips', 100);

INSERT INTO ingredients VALUES (15, 'frosted_lemonade', 100);

INSERT INTO ingredients VALUES (16, 'frosted_coffee', 100);

INSERT INTO ingredients VALUES (17, 'chocolate_cookie', 100);

INSERT INTO ingredients VALUES (18, 'lemonade', 100);

INSERT INTO ingredients VALUES (19, 'ice_tea', 100);

INSERT INTO ingredients VALUES (20, 'sweet_tea', 100);

INSERT INTO ingredients VALUES (21, 'water_bottle', 100);

INSERT INTO ingredients VALUES (22, 'cfa_sauce', 100);

INSERT INTO ingredients VALUES (23, 'ranch_sauce', 100);

INSERT INTO ingredients VALUES (24, 'honey_mustard', 100);

INSERT INTO ingredients VALUES (25, 'bbq_sauce', 100);

-- Populating product_ingredient table, this is per product

--  product: chicken_sandwich

INSERT INTO product_ingredients VALUES (0, 0, 1); -- chicken

INSERT INTO product_ingredients VALUES (0, 2, 1); -- buns

INSERT INTO product_ingredients VALUES (0, 4, 2); -- pickles

--  product: deluxe_sandwich

INSERT INTO product_ingredients VALUES (1, 0, 1); -- chicken

INSERT INTO product_ingredients VALUES (1, 2, 1); -- buns

INSERT INTO product_ingredients VALUES (1, 4, 2); -- pickles

INSERT INTO product_ingredients VALUES (1, 5, 2); -- tomato

INSERT INTO product_ingredients VALUES (1, 6, 1); -- lettuce

INSERT INTO product_ingredients VALUES (1, 7, 1); -- cheese

--  product: spicy_sandwich

INSERT INTO product_ingredients VALUES (2, 1, 1); -- spicy chicken

INSERT INTO product_ingredients VALUES (2, 2, 1); -- buns

INSERT INTO product_ingredients VALUES (2, 4, 2); -- pickles

--  product: spicy_deluxe

INSERT INTO product_ingredients VALUES (3, 1, 1); -- spicy chicken

INSERT INTO product_ingredients VALUES (3, 2, 1); -- buns

INSERT INTO product_ingredients VALUES (3, 4, 2); -- pickles

INSERT INTO product_ingredients VALUES (3, 5, 2); -- tomato

INSERT INTO product_ingredients VALUES (3, 6, 1); -- lettuce

INSERT INTO product_ingredients VALUES (3, 7, 1); -- cheese

--  product: chicken_biscuit

INSERT INTO product_ingredients VALUES (4, 0, 1); -- chicken

INSERT INTO product_ingredients VALUES (4, 3, 1); -- biscuit

--  product: spicy_biscuit

INSERT INTO product_ingredients VALUES (5, 1, 1); -- spicy chicken

INSERT INTO product_ingredients VALUES (5, 3, 1); -- biscuit

--  product: hash_browns

INSERT INTO product_ingredients VALUES (6, 8, 1); -- hashbrowns

--  product: five_nuggets

INSERT INTO product_ingredients VALUES (7, 9, 5); -- nuggets

--  product: eight_nuggets

INSERT INTO product_ingredients VALUES (8, 9, 8); -- nuggets

--  product: twelve_nuggets

INSERT INTO product_ingredients VALUES (9, 9, 12); -- nuggets

--  product: cobb_salad

INSERT INTO product_ingredients VALUES (10, 10, 1); -- cobb salad

--  product: market_salad

INSERT INTO product_ingredients VALUES (11, 11, 1); -- market salad

--  product: waffle_fries

INSERT INTO product_ingredients VALUES (12, 12, 1); -- waffle fries

--  product: side_salad

INSERT INTO product_ingredients VALUES (13, 13, 1); -- side salad

--  product: waffle_chips

INSERT INTO product_ingredients VALUES (14, 14, 1); -- waffle chips

--  product: frosted_lemonade

INSERT INTO product_ingredients VALUES (15, 15, 1); -- frosted_lemonade

--  product: frosted_coffee

INSERT INTO product_ingredients VALUES (16, 16, 1); -- frosted_coffee

--  product: chocolate_cookie

INSERT INTO product_ingredients VALUES (17, 17, 1); -- chocolate cookie

--  product: lemonade

INSERT INTO product_ingredients VALUES (18, 18, 1); -- lemonade

--  product: ice_tea

INSERT INTO product_ingredients VALUES (19, 19, 1); -- ice tea

--  product: sweet_tea

INSERT INTO product_ingredients VALUES (20, 20, 1); -- sweet tea

--  product: water_bottle

INSERT INTO product_ingredients VALUES (21, 21, 1); -- water bottle

--  product: cfa_sauce

INSERT INTO product_ingredients VALUES (22, 22, 1); -- cfa sauce

--  product: ranch_sauce

INSERT INTO product_ingredients VALUES (23, 23, 1); -- ranch sauce

--  product: honey_mustard

INSERT INTO product_ingredients VALUES (24, 24, 1); -- honey mustard

--  product: bbq_sauce

INSERT INTO product_ingredients VALUES (25, 25, 1); -- bbq sauce

-- Populating the user table

INSERT INTO users VALUES (0, 'Manager', TRUE);

INSERT INTO users VALUES (1, 'Employee', FALSE);

INSERT INTO users VALUES (2, 'Employee', FALSE);

INSERT INTO users VALUES (3, 'Employee', FALSE);